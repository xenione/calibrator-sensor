package com.xenione.libs.calibrator.orientation;
/*
Copyright 05/05/2017 Eugeni Josep Senent i Gabriel

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

public class Compensator {

    private double compensation = 0;
    private double lastUC = -1;
    private boolean isLastUCSet = false;


    public double removeCompensation(float f) {
        return (f % (2 * Math.PI));
    }

    public double compensate(double u) {
        double uC = u + compensation;
        if (!isLastUCSet) {
            setLastUC(uC);
        }
        if (Math.abs(lastUC - uC) > 2 * 0.8 * Math.PI) {
            compensation += lastUC > uC ? (2 * Math.PI) : -(2 * Math.PI);
        }
        lastUC = u + compensation;
        return lastUC;
    }

    public void setLastUC(double value) {
        lastUC = value;
        isLastUCSet = true;
    }
}
