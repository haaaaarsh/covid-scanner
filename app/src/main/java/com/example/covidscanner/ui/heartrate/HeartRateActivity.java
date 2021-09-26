package com.example.covidscanner.ui.heartrate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.daasuu.camerarecorder.CameraRecordListener;
import com.daasuu.camerarecorder.CameraRecorder;
import com.daasuu.camerarecorder.CameraRecorderBuilder;
import com.daasuu.camerarecorder.LensFacing;
import com.example.covidscanner.R;
import com.example.covidscanner.databinding.ActivityHeartRateBinding;
import com.example.covidscanner.ui.base.BaseActivity;
import com.example.covidscanner.ui.calculateheart.CalculateHeartActivity;
import com.example.covidscanner.ui.respiratory.RespiratoryRateActivity;
import com.example.covidscanner.utils.AlertUtil;
import com.example.covidscanner.utils.SampleGLView;
import com.example.covidscanner.utils.services.MeasureRespirationService;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class HeartRateActivity extends BaseActivity<HeartRateViewModel> implements HeartRateNavigator {

    ActivityHeartRateBinding binding;

    private SampleGLView sampleGLView;
    protected CameraRecorder cameraRecorder;
    private String filepath;
    protected LensFacing lensFacing = LensFacing.BACK;
    protected int cameraWidth = 1280;
    protected int cameraHeight = 720;
    protected int videoWidth = 720;
    protected int videoHeight = 720;
    CountDownTimer cTimer;

    @NonNull
    @Override
    protected HeartRateViewModel createViewModel() {
        HeartRateViewModelFactory factory = new HeartRateViewModelFactory();
        return ViewModelProviders.of(this, factory).get(HeartRateViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setToolBar();
        setDataBindings();
        viewModel.setNavigator(this);
        openDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseCamera();
        cancelTimer();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void onError(String message) {
        showSnackbar(message, Color.RED, Color.WHITE);
    }

    @Override
    public void recordClick() {
        File file;
        String filename = "heart_rate.mp4";
        file = new File(this.getCacheDir(), filename);
        filepath = file.getAbsolutePath();
        cameraRecorder.start(filepath);
        binding.btnRecord.setVisibility(View.GONE);
        startTimer();
    }

    private void setToolBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_heart_rate);
    }

    private void setDataBindings() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_heart_rate);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }

    public void openDialog() {
        AlertUtil.showAlertDialogWithListener(this, getString(R.string.instructions_heart), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    private void releaseCamera() {
        if (sampleGLView != null) {
            sampleGLView.onPause();
        }

        if (cameraRecorder != null) {
            cameraRecorder.stop();
            cameraRecorder.release();
            cameraRecorder = null;
        }

        if (sampleGLView != null) {
            ((FrameLayout) findViewById(R.id.rootView)).removeView(sampleGLView);
            sampleGLView = null;
        }
    }


    private void setUpCameraView() {
        runOnUiThread(() -> {
            FrameLayout frameLayout = findViewById(R.id.rootView);
            frameLayout.removeAllViews();
            sampleGLView = null;
            sampleGLView = new SampleGLView(getApplicationContext());
            sampleGLView.setTouchListener((event, width, height) -> {
                if (cameraRecorder == null) return;
                cameraRecorder.changeManualFocusPoint(event.getX(), event.getY(), width, height);
            });
            frameLayout.addView(sampleGLView);
        });
    }


    private void setUpCamera() {
        setUpCameraView();

        cameraRecorder = new CameraRecorderBuilder(this, sampleGLView)
                .recordNoFilter(true)
                .cameraRecordListener(new CameraRecordListener() {
                    @Override
                    public void onGetFlashSupport(boolean flashSupport) {
                        runOnUiThread(() -> {

                        });
                    }

                    @Override
                    public void onRecordComplete() {
//                        exportMp4ToGallery(getApplicationContext(), filepath);
                    }

                    @Override
                    public void onRecordStart() {

                    }

                    @Override
                    public void onError(Exception exception) {
                        Log.e("CameraRecorder", exception.toString());
                    }

                    @Override
                    public void onCameraThreadFinish() {

                    }
                })
                .videoSize(videoWidth, videoHeight)
                .cameraSize(cameraWidth, cameraHeight)
                .lensFacing(lensFacing)
                .mute(true)
                .build();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cameraRecorder.switchFlashMode();
            }
        }, TimeUnit.SECONDS.toMillis(1));
    }

    void startTimer() {
        binding.timer.setVisibility(View.VISIBLE);
        cTimer = new CountDownTimer(TimeUnit.SECONDS.toMillis(45), TimeUnit.SECONDS.toMillis(1)) {
            public void onTick(long millisUntilFinished) {
                String seconds = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));
                binding.timer.setText(seconds.length() > 1 ? "00:" + seconds : "00:" + "0" + seconds);
            }

            public void onFinish() {
                cameraRecorder.stop();
                Intent intent = new Intent(HeartRateActivity.this, CalculateHeartActivity.class);
                intent.putExtra("filepath", filepath);
                startActivity(intent);
                finish();
            }
        };
        cTimer.start();
    }

    //cancel timer
    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }
}