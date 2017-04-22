/*
Copyright 22/04/2017 Eugeni Josep Senent i Gabriel

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

package com.xenione.libs.calibrator.coordinator_system;

public class Polar {

    public double alpha;

    public int distance;

    public Polar(Polar polar) {
        this.distance = polar.distance;
        this.alpha = polar.alpha;
    }

    public Polar(int distance, double deg) {
        this.distance = distance;
        this.alpha = (2 * Math.PI / 360) * deg;
    }

    public void distance(int distance) {
        this.distance += distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Polar polar = (Polar) o;

        if (Double.compare(polar.alpha, alpha) != 0) return false;
        return distance == polar.distance;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(alpha);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + distance;
        return result;
    }
}

