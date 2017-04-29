package com.xenione.libs.calibrator.orientation.filters;


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

    private float[] xaux = new float[]{
            0f, 0f, 0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f, 0f, 0f,
            0f, 0f, 0f, 0f, 0f, 0f,
            0f,};

    @Override
    public float filter(float u) {
        System.arraycopy(x, 0, xaux, 1, xaux.length - 1);
        xaux[0] = u;
        float y = MatrixUtil.multiplication(xaux, coef);
        System.arraycopy(xaux, 0, x, 0, x.length);
        return y;
    }

    public float[] getCoef() {
        return coef;
    }
}
