package com.xenione.libs.calibrator.orientation.filters;


import com.xenione.libs.calibrator.orientation.Compensator;

/**
 * This class is a FIR filter
 */
public class FIRFilter extends Filter {

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

    private MatrixHelper matrixHelper = new MatrixHelper(x);

    private Compensator compensator = new Compensator();


    @Override
    public float filter(float u) {
        double uC = compensator.compensate(positivize(u));
        matrixHelper.startWith((float) uC);
        float f = matrixHelper.multiplication(coef);
        return positivize((float) compensator.removeCompensation(f));
    }

    public float[] getCoef() {
        return coef;
    }
}
