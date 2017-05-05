package com.xenione.apps.calibrator_sensor;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xenione.libs.calibrator.CalibratorView;
import com.xenione.libs.calibrator.orientation.OrientationService;

public class CalibrationActivity extends AppCompatActivity {

    private OrientationService mOrientationService;
    CalibratorView calibratorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);
        calibratorView = (CalibratorView) findViewById(R.id.calibrator);
        calibratorView.setOnCalibrationListener(new CalibratorView.CalibrationListener() {
            @Override
            public void onCalibrationComplete() {
                CalibrationActivity.this.startActivity(OrientationActivity
                        .newIntent(CalibrationActivity.this));
                finish();
            }
        });
        mOrientationService = new OrientationService(this);
        mOrientationService.registerUpdateListener(mOrientationListener);
        mOrientationService.start(100);
    }

    private OrientationService.OrientationListener mOrientationListener = new OrientationService.OrientationListener() {
        @Override
        public void onOrientationChanged(float[] orientation) {
            int deg = (int) ((360 / (2 * Math.PI)) * orientation[0]);
            calibratorView.setOrientation(deg);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrientationService.unregisterUpdateListener(mOrientationListener);
        mOrientationService.stop();
    }
}
