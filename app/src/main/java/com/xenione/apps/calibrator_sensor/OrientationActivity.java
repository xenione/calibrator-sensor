package com.xenione.apps.calibrator_sensor;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.xenione.libs.calibrator.orientation.OrientationService;
import com.xenione.libs.calibrator.orientation.filters.FIRFilter;
import com.xenione.libs.calibrator.orientation.filters.NoFilter;
import com.xenione.libs.calibrator.utils.MathUtils;

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

        SwitchCompat switchX = ((SwitchCompat) findViewById(R.id.switch_x));
        switchX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mOrientationService.setFilterX(isChecked ? new FIRFilter() : new NoFilter());
            }
        });
        switchX.setChecked(true);

        SwitchCompat switchY = ((SwitchCompat) findViewById(R.id.switch_y));
        switchY.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mOrientationService.setFilterY(isChecked ? new FIRFilter() : new NoFilter());
            }
        });
        switchY.setChecked(true);

        SwitchCompat switchZ = ((SwitchCompat) findViewById(R.id.switch_z));
        switchZ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mOrientationService.setFilterZ(isChecked ? new FIRFilter() : new NoFilter());
            }
        });
        switchZ.setChecked(true);


    }

    private OrientationService.OrientationListener mOrientationListener = new OrientationService.OrientationListener() {
        @Override
        public void onOrientationChanged(float[] orientation) {
            orientationX.setText(String.format(Locale.getDefault(), "Azimuth(degrees): %d", MathUtils.convertToDeg(orientation[0])));
            orientationY.setText(String.format(Locale.getDefault(), "Pitch(degrees): %d", MathUtils.convertToDeg(orientation[1])));
            orientationZ.setText(String.format(Locale.getDefault(), "Roll(degrees): %d", MathUtils.convertToDeg(orientation[2])));
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrientationService.unregisterUpdateListener(mOrientationListener);
        mOrientationService.stop();
    }
}
