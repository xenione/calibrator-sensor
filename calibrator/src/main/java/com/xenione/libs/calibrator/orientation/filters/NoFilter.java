package com.xenione.libs.calibrator.orientation.filters;

/**
 * Created by Eugeni on 11/05/2016.
 */
public class NoFilter implements Filter {
    @Override
    public float filter(float u) {
        return u;
    }
}
