package com.xenione.libs.calibrator.orientation;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.xenione.libs.calibrator.orientation.filters.FIRFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by Eugeni on 10/05/2016.
 */

/**
 * Service to track orientation backed by accelerometer and magnetic sensors
 * provide listeners due to registerUpdateListener to be aware of orientation changes.
 * This service provide readings at every 50 milliseconds.
 */
public class OrientationService implements Runnable {

    public interface OrientationListener {
        void onOrientationChanged(float[] orientation);
    }

    private static final String TAG = "GyroscopeService";
    private static final int NOTIFY_ORIENTATION = 1;

    private ScheduledExecutorService mService = Executors.newSingleThreadScheduledExecutor();

    private List<OrientationListener> mOrientationCallbacks = new ArrayList<>();
    private OrientationHandler mHandler;
    private volatile Gyroscope gyro;
    private volatile float[] orientation = new float[3];

    /**
     * Service to track orientation backed by accelerometer and magnetic sensors
     * provide listeners due to registerUpdateListener to be aware of orientation changes
     *
     * @param context
     * @param looper  where to process orientation changes callbacks
     */
    public OrientationService(Context context, Looper looper) {
        mHandler = new OrientationHandler(looper == null ? Looper.getMainLooper() : looper);
        init(context);
    }

    /**
     * Service to track orientation backed by accelerometer and magnetic sensors
     * provide listeners due to registerUpdateListener to be aware of orientation changes
     *
     * @param context
     */
    public OrientationService(Context context) {
        this(context, null);
    }

    private void init(Context context) {
        gyro = new Gyroscope(context);
        gyro.setFilter(new FIRFilter(), new FIRFilter(), new FIRFilter()); /*this filters are for 50 ms sample time*/
        mService.scheduleAtFixedRate(this, 300, 50, TimeUnit.MILLISECONDS);
    }

    public void registerUpdateListener(OrientationListener listener) {
        mOrientationCallbacks.add(listener);
    }

    public void unregisterUpdateListener(OrientationListener listener) {
        mOrientationCallbacks.remove(listener);
    }

    public void release() {
        if (mService != null) {
            gyro.release();
            mService.shutdown();
            mHandler.removeMessages(NOTIFY_ORIENTATION);
            mService = null;
        }
    }

    @Override
    public void run() {
        gyro.getFilteredOrientation(orientation);
        mHandler.obtainMessage(NOTIFY_ORIENTATION).sendToTarget();
    }

    private class OrientationHandler extends Handler {

        OrientationHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == NOTIFY_ORIENTATION) {
                for (OrientationListener listener : mOrientationCallbacks) {
                    listener.onOrientationChanged(orientation);
                }
            }
        }
    }
}
