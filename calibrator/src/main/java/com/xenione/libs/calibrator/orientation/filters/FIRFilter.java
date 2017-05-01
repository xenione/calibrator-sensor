package com.xenione.libs.calibrator.orientation.filters;


import android.util.Log;

/**
 * This class is a FIR filter for 50 ms sample time
 */
public class FIRFilter implements Filter {

    private float[] coef = new float[]{
            0.0012770f, 0.0046636f, 0.0115022f, 0.0229597f, 0.0394189f, 0.0599347f,
            0.0820306f, 0.10205813f, 0.1161061f, 0.1200973f, 0.1161061f, 0.10205813f,
            0.0820306f, 0.0599347f, 0.0394189f, 0.0229597f, 0.0115022f, 0.0046636f,
            0.0012770f};

    private float[] x = new float[]{
            0f, 0f, 0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f, 0f, 0f,
            0f,};

    private double compensation = 0;
    private double lastU = -1;

    @Override
    public float filter(float u) {
        double uC = compensation(positivize(u));
        MatrixUtil.insert((float) uC, x);
        float f = MatrixUtil.multiplication(x, coef);
        lastU = uC;
        Log.i("orientation", "radians filtered: " + uC + " raw:" + u);
        return positivize((float) (f % (2 * Math.PI)));
    }

    private float positivize(float value) {
        return (value > 0) ? value : (value + 2 * (float) Math.PI);
    }

    private double compensation(double u) {
        double uC = u + compensation;
        if (lastU == -1) {
            lastU = uC;
        }
        if (Math.abs(lastU - uC) > 2 * 0.8 * Math.PI) {
            if (lastU > uC) {
                compensation += (2 * Math.PI);
            } else {
                compensation -= (2 * Math.PI);
            }
        }
        return u + compensation;
    }

    public float[] getCoef() {
        return coef;
    }
}
