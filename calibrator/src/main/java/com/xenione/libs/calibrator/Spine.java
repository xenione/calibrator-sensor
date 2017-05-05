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

package com.xenione.libs.calibrator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;

import com.xenione.libs.calibrator.coordinator_system.Cartesian;
import com.xenione.libs.calibrator.coordinator_system.Polar;

import static com.xenione.libs.calibrator.coordinator_system.CoordinatorSystem.toCartesian;

public class Spine implements Drawable {

    private Cartesian start;
    private Cartesian end;

    private State state = State.ONE_LAYER_OF_PAINT;

    public Spine(Polar polar, int length) {
        start = toCartesian(polar);
        Polar endPolar = new Polar(polar);
        endPolar.offset(length);
        end = toCartesian(endPolar);
    }

    public void addPaintLayer() {
        state = state.next();
    }

    public boolean isLastLayer() {
        return state.isLastLayer();
    }

    @Override
    public void draw(Canvas canvas) {
        state.draw(canvas, start, end);
    }

    private enum State {
        ONE_LAYER_OF_PAINT(Color.argb(127, 255, 255, 255)),
        SECOND_LAYER_OF_PAINT(Color.argb(255, 255, 255, 255)),
        THIRD_LAYER_OF_PAINT(Color.RED);

        private Paint paint;

        State(@ColorInt int color) {
            this.paint = new Paint();
            this.paint.setColor(color);
            this.paint.setAntiAlias(true);
            this.paint.setStrokeWidth(5);
            this.paint.setStyle(Paint.Style.FILL_AND_STROKE);
        }

        public State next() {
            switch (this) {
                case ONE_LAYER_OF_PAINT:
                    return SECOND_LAYER_OF_PAINT;
                case SECOND_LAYER_OF_PAINT:
                case THIRD_LAYER_OF_PAINT:
                    return THIRD_LAYER_OF_PAINT;
            }
            return null;
        }

        public boolean isLastLayer() {
            return this == THIRD_LAYER_OF_PAINT;
        }

        public void draw(Canvas canvas, Cartesian start, Cartesian end) {
            canvas.drawLine(start.x(), start.y(), end.x(), end.y(), paint);
        }
    }
}
