package com.example.covidscanner.utils.services;

import static org.apache.commons.math3.util.Precision.round;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MeasureRespirationService extends Service implements SensorEventListener {
    public MeasureRespirationService() {

    }

    private SensorManager sensorManager;
    private Sensor sensor;
    private Double respRate = 0.0;
    ArrayList<Float> arr = new ArrayList<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(getApplicationContext().SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            arr.add(event.values[1]);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sensorManager.unregisterListener(MeasureRespirationService.this);
                    respRate = respRate(arr);
                    respRate = round(respRate, 2);
                    Log.e("Resprate<>", String.valueOf(respRate));
                    sendMessageToActivity(respRate);
                }
            }, TimeUnit.SECONDS.toMillis(45));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void sendMessageToActivity(double val) {
        Intent intent = new Intent("RespiratoryRate");
        intent.putExtra("value", val);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public Double respRate(ArrayList<Float> arr) {
        Double respCount = 0.0;
        for (int i = 0; i < arr.size(); i++) {
            if (isaPeak(arr, arr.size(), arr.get(i), i - 1, i + 1)) {
                respCount++;
            }
        }
        return (respCount / 2) * 60 * 0.5 / 45;
    }

    private Boolean isaPeak(ArrayList<Float> arr, int n, float no, int i, int j) {
        if (j < n && arr.get(j) > no) {
            return false;
        }
        if (i >= 0 && arr.get(i) > no) {
            return false;
        }
        return true;
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
