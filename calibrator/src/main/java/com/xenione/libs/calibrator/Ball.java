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
limitations under the License. ex
*/

package com.xenione.libs.calibrator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;

import com.xenione.libs.calibrator.coordinator_system.Cartesian;
import com.xenione.libs.calibrator.coordinator_system.Polar;

import static com.xenione.libs.calibrator.coordinator_system.CoordinatorSystem.toCartesian;

public class Ball implements Drawable {

    private Paint mPaint;

    private int radius;

    private Polar position;

    private Cartesian cartesian;

    public Ball(Builder builder) {
        position = new Polar(builder.distance, 0.0d);
        cartesian = toCartesian(position);
        this.radius = builder.radius;
        initPaint(builder.color);
    }

    private void initPaint(@ColorInt int color) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(color);
        mPaint.setAntiAlias(true);
    }

    public void setAlpha(double alpha) {
        position.alpha = (2 * Math.PI / 360) * alpha;
    }

    @Override
    public void draw(Canvas canvas) {
        toCartesian(position, cartesian);
        canvas.drawCircle(cartesian.x(), cartesian.y(), radius, mPaint);
    }

    public static class Builder {

        int distance;
        int radius;
        int color;

        public Builder distance(int distance) {
            this.distance = distance;
            return this;
        }

        public Builder radius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        public Ball build(){
            return new Ball(this);
        }
    }
}
