package com.xenione.libs.calibrator.orientation.filters;


/**
 * Created by Eugeni on 11/05/2016.
 */
public class MatrixUtil {

    public static float multiplication(float[] x, float[] y) {
        float result = 0;
        for (int i = 0; i < x.length; i++) {
            result += x[i] * y[i];
        }
        return result;
    }

    public static float[] multiplication(double x, float[] y) {
        float[] result = new float[y.length];
        for (int i = 0; i < y.length; i++) {
            result[i] = (float) (y[i] * x);
        }

        return result;
    }

    public static void insert(float newX, float[] x) {
        System.arraycopy(x, 0, x, 1, x.length - 1);
        x[0] = newX;
    }

    public static void add(float[] array, float added) {
        for (int i = 0; i < array.length; i++) {
            array[i] += array[i] + added;
        }
    }
}
