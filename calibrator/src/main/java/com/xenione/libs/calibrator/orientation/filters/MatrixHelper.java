package com.xenione.libs.calibrator.orientation.filters;


/**
 * Created by Eugeni on 11/05/2016.
 */
public class MatrixHelper {

    private final float[] array;

    public MatrixHelper(float[] array) {
        this.array = array;
    }

    public float multiplication(float[] y) {
        float result = 0;
        for (int i = 0; i < array.length; i++) {
            result += array[i] * y[i];
        }
        return result;
    }

    public float[] multiplication(double x) {
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (float) (array[i] * x);
        }

        return result;
    }

    public void startWith(float newX) {
        System.arraycopy(array, 0, array, 1, array.length - 1);
        array[0] = newX;
    }

    public void add(float added) {
        for (int i = 0; i < array.length; i++) {
            array[i] += array[i] + added;
        }
    }
}
