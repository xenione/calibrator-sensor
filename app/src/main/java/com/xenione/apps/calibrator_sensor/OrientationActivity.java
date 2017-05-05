package com.xenione.apps.calibrator_sensor;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.xenione.libs.calibrator.orientation.OrientationService;

import java.util.Locale;

public class OrientationActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, OrientationActivity.class);
    }

    private OrientationService mOrientationService;
    private TextView orientationX;
    private TextView orientationY;
    private TextView orientationZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation);
        orientationX = (TextView) findViewById(R.id.orientation_x);
        orientationY = (TextView) findViewById(R.id.orientation_y);
        orientationZ = (TextView) findViewById(R.id.orientation_z);


        mOrientationService = new OrientationService(this);
        mOrientationService.registerUpdateListener(mOrientationListener);
        mOrientationService.start(50);
    }

    private OrientationService.OrientationListener mOrientationListener = new OrientationService.OrientationListener() {
        @Override
        public void onOrientationChanged(float[] orientation) {
            orientationX.setText(String.format(Locale.getDefault(), "x: %.2f", orientation[0]));
            orientationY.setText(String.format(Locale.getDefault(), "Y: %.2f", orientation[1]));
            orientationZ.setText(String.format(Locale.getDefault(), "z: %.2f", orientation[2]));
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrientationService.unregisterUpdateListener(mOrientationListener);
        mOrientationService.stop();
    }
}
