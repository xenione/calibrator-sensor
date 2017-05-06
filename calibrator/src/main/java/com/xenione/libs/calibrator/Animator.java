package com.xenione.libs.calibrator;
/*
Copyright 06/05/2017 Eugeni Josep Senent i Gabriel

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

import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

import com.xenione.libs.calibrator.orientation.Compensator;

public class Animator {

    public interface UpdateListener {
        void onUpdate(float fraction);
    }

    private UpdateListener updateListener;

    private ValueAnimator animator;

    private AnimatorUpdateListener animatorUpdateListener = new AnimatorUpdateListener();

    private Compensator compensator = new Compensator();

    public Animator(long duration) {
        initAnimator(duration);
    }

    private void initAnimator(long duration) {
        animator = ValueAnimator.ofFloat(0f, 0f);
        animator.setDuration(duration);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animatorUpdateListener);
    }

    public void setUpdateListener(UpdateListener listener) {
        updateListener = listener;
    }



    public void startFrom(float end) {
        if (animator.isStarted()) {
            animator.cancel();
        }
        float startC = (float) animator.getAnimatedValue();
        float endC = (float) compensator.compensate(end);
        if (startC == endC) {
            return;
        }
        compensator.setLastUC(startC);
        animator.setFloatValues(startC, endC);
        animator.start();
    }

    public float positivize(float value) {
        return (value > 0) ? value : (value + 2 * (float) Math.PI);
    }

    private class AnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float alpha = (float) compensator.removeCompensation((float) animation.getAnimatedValue());
            updateListener.onUpdate(positivize(alpha));
        }
    }
}
