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
import android.util.SparseArray;

import com.xenione.libs.calibrator.coordinator_system.Polar;

public class Crown implements Drawable {

    private SparseArray<Drawable> registerSpines = new SparseArray<>();

    private Crown() {

    }

    void addDrawable(int alpha, Drawable drawable) {
        registerSpines.put(alpha, drawable);
    }

    public Drawable drawableAt(int alpha) {
        return registerSpines.get(alpha);
    }

    @Override
    public void draw(Canvas canvas) {
        for (int i = 0; i < registerSpines.size(); i++) {
            registerSpines.valueAt(i).draw(canvas);
        }
    }

    public static class Builder {

        int from = 0;
        int to = 360;
        int distance;
        DrawableFactory factory;
        int amount;

        public Builder from(int deg) {
            this.from = deg;
            return this;
        }

        public Builder to(int deg) {
            this.to = deg;
            return this;
        }

        public Builder distance(int distance) {
            this.distance = distance;
            return this;
        }

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder drawableFactory(DrawableFactory factory) {
            this.factory = factory;
            return this;
        }

        public Crown build() {
            Crown crown = new Crown();
            double interval = (to - from) / amount;
            for (int i = 0; i < amount; i++) {
                double alpha = interval * i;
                Drawable drawable = factory.create(i, new Polar(distance, alpha));
                crown.addDrawable((int) alpha, drawable);
            }
            return crown;
        }
    }
}
