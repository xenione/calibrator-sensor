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

public class Animator {

    public interface UpdateListener {
        void onUpdate(int fraction);
    }

    private UpdateListener updateListener;

    private ValueAnimator animator;

    private AnimatorUpdateListener animatorUpdateListener = new AnimatorUpdateListener();

    public Animator(long duration) {
        initAnimator(duration);
    }

    private void initAnimator(long duration) {
        animator = ValueAnimator.ofInt(0, 0);
        animator.setDuration(duration);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animatorUpdateListener);
    }

    public void setUpdateListener(UpdateListener listener) {
        updateListener = listener;
    }

    public void startFrom(int end) {
        if (animator.isStarted()) {
            animator.cancel();
        }
        int start = (int) animator.getAnimatedValue();
        if (start == end) {
            return;
        }
        animator.setIntValues(start, end);
        animator.start();
    }

    private class AnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            updateListener.onUpdate((int) animation.getAnimatedValue());
        }
    }
}
