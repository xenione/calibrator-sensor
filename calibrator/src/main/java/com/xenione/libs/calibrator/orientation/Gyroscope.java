package com.xenione.libs.calibrator.orientation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.xenione.libs.calibrator.orientation.filters.Filter;
import com.xenione.libs.calibrator.orientation.filters.NoFilter;


/**
 * Created by Eugeni on 10/05/2016.
 */
public class Gyroscope implements SensorEventListener {

    private float[] raw = new float[3];

    private SensorManager mSensorManager;

    private float[] mR = new float[9];
    private float[] mI = new float[9];
    private float[] magnet = new float[3];
    private float[] accel = new float[3];

    private Filter[] filter = new Filter[]{new NoFilter(), new NoFilter(), new NoFilter()};

    public Gyroscope(Context context) {
        mSensorManager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        initListeners();
    }

    private void initListeners() {
        Sensor gsensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor msensor = mSensorManager
                .getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(this, gsensor,
                SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, msensor,
                SensorManager.SENSOR_DELAY_GAME);
    }

    public void setFilter(Filter filterX, Filter filterY, Filter filterZ) {
        this.filter[0] = filterX;
        this.filter[1] = filterY;
        this.filter[2] = filterZ;
    }

    public void setFilterX(Filter filterX) {
        this.filter[0] = filterX;
    }

    public void setFilterY(Filter filterY) {
        this.filter[1] = filterY;
    }

    public void setFilterZ(Filter filterZ) {
        this.filter[2] = filterZ;
    }

    public void release() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nothing to do here
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        switch (type) {
            case Sensor.TYPE_ACCELEROMETER:
                System.arraycopy(event.values, 0, accel, 0, 3);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                System.arraycopy(event.values, 0, magnet, 0, 3);
                break;
            default:
                return;
        }

        if (SensorManager.getRotationMatrix(mR, mI, accel, magnet)) {
            SensorManager.getOrientation(mR, raw);
        }
    }

    private void filter(float[] orientation) {
        int dimen = Math.min(orientation.length, 3);
        for (int i = 0; i < dimen; i++) {
            orientation[i] = filter[i].filter(positivize(raw[i]));
        }
    }

    public float[] getRawOrientation() {
        return raw;
    }

    public void getFilteredOrientation(float[] orientation) {
        filter(orientation);
        Log.i("orientation", "radians filtered: " + orientation[0] + " raw:" + raw[0]);
    }

    private float positivize(float value) {
        return (value > 0) ? value : (value + 2 * (float) Math.PI);
    }
}
