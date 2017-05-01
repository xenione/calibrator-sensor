package com.xenione.apps.calibrator_sensor;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import com.xenione.libs.calibrator.CalibratorView;
import com.xenione.libs.calibrator.orientation.OrientationService;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    private OrientationService mOrientationService;
    CalibratorView calibratorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calibratorView = (CalibratorView) findViewById(R.id.calibrator);
        calibratorView.setOnCalibrationListener(new CalibratorView.CalibrationListener() {
            @Override
            public void onCalibrationComplete() {
                Toast.makeText(MainActivity.this, "Finish calibration", Toast.LENGTH_LONG).show();
            }
        });
        seekBar = ((SeekBar) findViewById(R.id.seek));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    calibratorView.setAlpha(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mOrientationService = new OrientationService(this);
        mOrientationService.registerUpdateListener(mOrientationListener);
    }

    private OrientationService.OrientationListener mOrientationListener = new OrientationService.OrientationListener() {
        @Override
        public void onOrientationChanged(float[] orientation) {
            int deg = (int) ((0.3333) * ((360 / (2 * Math.PI)) * orientation[0]) +
                    (0.3333) * ((360 / (2 * Math.PI)) * orientation[1]) +
                    (0.3333) * ((360 / (2 * Math.PI)) * orientation[2]));
            Log.i("MainActivity", "deg: " + deg);
            calibratorView.setAlpha(deg);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        seekBar.setOnSeekBarChangeListener(null);
        mOrientationService.unregisterUpdateListener(mOrientationListener);
        mOrientationService.release();
    }
}
