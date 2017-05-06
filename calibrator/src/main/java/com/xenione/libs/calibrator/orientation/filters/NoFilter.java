package com.xenione.libs.calibrator.orientation.filters;

/**
 * Created by Eugeni on 11/05/2016.
 */
public class NoFilter extends Filter {
    @Override
    public float filter(float u) {
        return positivize(u);
    }
}
