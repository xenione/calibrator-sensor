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

public class CoordinatorSystem {

    public static Polar toPolar(Cartesian point) {

        int distance = point.distance();
        double alpha = Math.asin(point.y() / distance);

        Cartesian.Quadrant q = point.quadrant();

        double alpha2Pi = 0;

        switch (q) {
            case FIRST: {
                alpha2Pi = alpha;
                break;
            }
            case SECOND: {
                alpha2Pi = Math.PI - alpha;
                break;
            }
            case THIRD: {
                alpha2Pi = Math.PI - (2 / 3) * alpha;
                break;
            }
            case FORTH: {
                alpha2Pi = 2 * Math.PI - (4 / 3) * alpha;
                break;
            }
        }
        return new Polar(distance, alpha2Pi);
    }

    public static void toCartesian(Polar polar,Cartesian cartesian) {

        int x;
        int y;

        Cartesian.Quadrant q = Cartesian.Quadrant.from(polar.alpha);

        switch (q) {
            case FIRST: {
                double alpha = polar.alpha;
                y = (int) (polar.distance * Math.sin(alpha));
                x = (int) (polar.distance * Math.cos(alpha));
            }
            break;
            case SECOND: {
                double alpha = Math.PI - polar.alpha;
                y = (int) (polar.distance * Math.sin(alpha));
                x = -(int) (polar.distance * Math.cos(alpha));
            }
            break;
            case THIRD: {
                double alpha = polar.alpha - Math.PI;
                y = -(int) (polar.distance * Math.sin(alpha));
                x = -(int) (polar.distance * Math.cos(alpha));
            }
            break;
            default:
            case FORTH: {
                double alpha = 2 * Math.PI - polar.alpha;
                y = -(int) (polar.distance * Math.sin(alpha));
                x = (int) (polar.distance * Math.cos(alpha));
            }
        }

        cartesian.x(x);
        cartesian.y(y);
    }

    public static Cartesian toCartesian(Polar polar) {
        Cartesian cartesian = new Cartesian(0, 0);
        toCartesian(polar, cartesian);
        return cartesian;
    }
}
