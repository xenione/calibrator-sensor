package com.xenione.apps.calibrator_sensor;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xenione.libs.calibrator.CalibratorView;
import com.xenione.libs.calibrator.orientation.OrientationService;
import com.xenione.libs.calibrator.orientation.filters.FIRFilter;

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
            public void onCalibrationComplete(int percentage) {
                if (percentage > 70) {
                    navigateToOrientation();
                }
            }
        });
        mOrientationService = new OrientationService(this);
        mOrientationService.registerUpdateListener(mOrientationListener);
        mOrientationService.start(100);
        mOrientationService.setFilterX(new FIRFilter());
    }

    private void navigateToOrientation(){
        CalibrationActivity.this.startActivity(OrientationActivity
                .newIntent(CalibrationActivity.this));
        finish();
    }

    private OrientationService.OrientationListener mOrientationListener = new OrientationService.OrientationListener() {
        @Override
        public void onOrientationChanged(float[] orientation) {
            calibratorView.setOrientation(orientation[0]);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrientationService.unregisterUpdateListener(mOrientationListener);
        mOrientationService.stop();
    }
}
