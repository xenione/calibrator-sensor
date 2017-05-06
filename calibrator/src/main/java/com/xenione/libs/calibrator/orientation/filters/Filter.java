package com.xenione.libs.calibrator.orientation.filters;

/**
 * Created by Eugeni on 11/05/2016.
 */
public abstract class Filter {

    public abstract float filter(float u);

    public float positivize(float value) {
        return (value > 0) ? value : (value + 2 * (float) Math.PI);
    }
}
