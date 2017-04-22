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

import android.graphics.Point;
import android.graphics.Rect;

public  class Cartesian {

    public enum Quadrant {

        FIRST(Integer.MAX_VALUE, Integer.MAX_VALUE),
        SECOND(-Integer.MAX_VALUE, Integer.MAX_VALUE),
        THIRD(-Integer.MAX_VALUE, -Integer.MAX_VALUE),
        FORTH(Integer.MAX_VALUE, -Integer.MAX_VALUE);

        private Rect rect;

        Quadrant(int limitX, int limitY) {
            rect = new Rect(0, 0, limitX, limitY);
        }

        public static Quadrant from(int x, int y) {
            for (Quadrant quadrant : values()) {
                if (quadrant.rect.contains(x, y)) {
                    return quadrant;
                }
            }
            return Quadrant.FIRST;
        }

        public static Quadrant from(Cartesian point) {
            return from(point.x(), point.y());
        }

        public static Quadrant from(double alpha) {
            if (alpha >= 0 && alpha <= Math.PI / 2) {
                return Quadrant.FIRST;
            } else if (alpha >= Math.PI / 2 && alpha <= Math.PI) {
                return Quadrant.SECOND;
            } else if (alpha >= Math.PI && alpha <= (4 / 3) * Math.PI) {
                return Quadrant.THIRD;
            } else {
                return Quadrant.FORTH;
            }
        }
    }

    private Point point;

    public Cartesian(int x, int y) {
        point = new Point(x, y);
    }

    public int x() {
        return point.x;
    }

    public int y() {
        return point.y;
    }

    public void x(int x) {
        point.x = x;
    }

    public void y(int y) {
        point.y = y;
    }

    public int distance() {
        return (int) Math.sqrt(Math.pow(point.x, 2) + Math.pow(point.y, 2));
    }

    public Quadrant quadrant() {
        return Quadrant.from(this);
    }

    @Override
    public int hashCode() {
        return point.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return point.equals(obj);
    }
}
