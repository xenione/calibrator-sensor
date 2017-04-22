package com.xenione.apps.calibrator_sensor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.Toast;

import com.xenione.libs.calibrator.CalibratorView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CalibratorView calibratorView = (CalibratorView) findViewById(R.id.calibrator);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        seekBar.setOnSeekBarChangeListener(null);
    }
}
