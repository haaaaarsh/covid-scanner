package com.example.covidscanner.ui.calculateheart;

import static org.apache.commons.math3.util.Precision.round;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.covidscanner.R;
import com.example.covidscanner.data.db.AppDatabase;
import com.example.covidscanner.data.db.dao.SymptomsDao;
import com.example.covidscanner.data.model.Symptoms;
import com.example.covidscanner.databinding.ActivityCalculateRateBinding;
import com.example.covidscanner.ui.base.BaseActivity;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class CalculateHeartActivity extends BaseActivity<CalculateHeartViewModel> implements CalculateHeartNavigator {

    ActivityCalculateRateBinding binding;

    AppDatabase db;

    String filepath;
    private int frameRate = 20;
    private ArrayList<Double> intensityAvg = new ArrayList<Double>();
    private int ip = 0;
    private double threshold = 0.5;
    private double finalAvg = 0.0;
    private int windowSize = 5;
    private float finalHeartRate = 0f;
    private Double valueDef = 65.;
    private ArrayList<Double> diffWindow = new ArrayList<Double>();
    private int duration = 45;

    @NonNull
    @Override
    protected CalculateHeartViewModel createViewModel() {
        CalculateHeartViewModelFactory factory = new CalculateHeartViewModelFactory();
        return ViewModelProviders.of(this, factory).get(CalculateHeartViewModel.class);
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i("OpenCV", "OpenCV loaded successfully");
//                    imageMat=new Mat();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBar();
        setDataBindings();
        viewModel.setNavigator(this);
        db = AppDatabase.getInstance();
        initCalculate();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void onError(String message) {
        showSnackbar(message, Color.RED, Color.WHITE);
    }

    private void initCalculate() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!OpenCVLoader.initDebug()) {
                    Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
                    OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, CalculateHeartActivity.this, mLoaderCallback);
                } else {
                    Log.d("OpenCV", "OpenCV library found inside package. Using it!");
                    mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
                }

                if (getIntent() != null)
                    filepath = getIntent().getStringExtra("filepath");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(Calendar.getInstance().getTime().toString());
                        getVideoFrames(filepath, frameRate * 45);
                        if (intensityAvg.size() == (frameRate * 45)) {
                            createDiffArray(intensityAvg, windowSize);
                            valueDef = heartRate(diffWindow);
                            try {
                                Handler mainhandler1 = new Handler(Looper.getMainLooper());
                                Runnable myrunnable1 = new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.circularProgressBar.setProgress(100);
                                        Double temp = (valueDef * 60) / (duration);
                                        finalHeartRate = (float) round(temp, 2);
                                        Log.e("Final Heart Rate<>", String.valueOf(finalHeartRate));
                                        updateDb(finalHeartRate);
                                        binding.txtHeartRate.setText(String.format("%.2f BPM", finalHeartRate));
                                    }
                                };
                                mainhandler1.post(myrunnable1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }

                }).start();
            }
        }, TimeUnit.SECONDS.toMillis(2));

    }

    private void updateDb(float hr) {
        class UpdateDbTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                SymptomsDao symptomsDao = db.symptomsDao();
                Symptoms s = symptomsDao.getSymptoms().get(symptomsDao.getSymptoms().size() - 1);
                s.heartRate = hr;
                symptomsDao.updateSymptom(s);
                return null;
            }
        }
        UpdateDbTask updateDbTask = new UpdateDbTask();
        updateDbTask.execute();
    }

    private void createDiffArray(ArrayList<Double> input, int lag) {
        double sum_ = 0.0;
        int count = 0;
        for (int i = 0; i < input.size() - 1; i++) {
            if (count == lag) {
                diffWindow.add(sum_);
                finalAvg += sum_;
//                System.out.println(sum_);
                count = 1;
                sum_ = 0;
                sum_ += Math.abs(input.get(i) - input.get(i + 1));
            } else {
                sum_ += Math.abs(input.get(i) - input.get(i + 1));
                count++;
            }

        }
        finalAvg /= diffWindow.size();
        System.out.println("Final Avg: " + Double.toString(finalAvg));
    }

    private void getVideoFrames(String path, int size) {
        Bitmap frame;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();

        retriever.setDataSource(path);
        int i = 0;
        System.out.print(Calendar.getInstance().getTime().toString());
        while (i < size) {
            Mat mat = new Mat();
            frame = retriever.getFrameAtTime(i * (1000000 / frameRate), MediaMetadataRetriever.OPTION_CLOSEST).copy(Bitmap.Config.ARGB_8888, true);
            Utils.bitmapToMat(frame, mat);
            org.opencv.core.Size s = new Size(3, 3);
            Imgproc.GaussianBlur(mat, mat, s, 2);
            double sum_ = 0.0;
            for (int j = (int) (mat.height() * 0.20); j < (mat.height() * (1 - 0.20)); j++) {
                for (int k = (int) (mat.width() * 0.20); k < (mat.width() * (1 - 0.20)); k++) {
                    sum_ += mat.get(j, k)[0];
                }
            }
            sum_ /= (mat.height() * 0.6 * (mat.width() * 0.6));
            intensityAvg.add(sum_);
            ip = i;
            Handler mainhandler = new Handler(Looper.getMainLooper());
            Runnable myrunnable = new Runnable() {
                @Override
                public void run() {
                    float prog = (int) (ip * 100) / (frameRate * 45);
                    setProgressAnimation(prog);
                }
            };
            mainhandler.post(myrunnable);
            i++;
        }

    }

    private void setProgressAnimation(float prog) {
        String waitText = "Calculating";
        int len = waitText.length();
        int tvLen = binding.txtHeartRate.getText().toString().trim().length();
        if (tvLen == 0)
            binding.txtHeartRate.setText(waitText);
        else if (tvLen == len)
            binding.txtHeartRate.setText(waitText + ".");
        else if (tvLen == len + 1)
            binding.txtHeartRate.setText(waitText + "..");

        else if (tvLen == len + 2)
            binding.txtHeartRate.setText(waitText + "...");

        else if (tvLen == len + 3)
            binding.txtHeartRate.setText(waitText + "....");
        else if (tvLen == len + 4)
            binding.txtHeartRate.setText(waitText + ".....");
        else if (tvLen == len + 5)


            binding.txtHeartRate.setText(waitText);
        binding.circularProgressBar.setProgress(prog);
    }

    private Boolean isaPeak(ArrayList<Double> arr, Double no, int i) {
        if (Math.abs(no - arr.get(i)) < finalAvg * threshold) {
            return false;
        }
        return true;
    }

    public Double heartRate(ArrayList<Double> arr) {
        Double hCount = 0.0;
        for (int i = 1; i < arr.size(); i++) {
            if (isaPeak(arr, arr.get(i), i - 1)) {
                hCount++;
            }
        }
        return hCount / 2;
    }

    private void setToolBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_calculate_rate);
    }

    private void setDataBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calculate_rate);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }

}