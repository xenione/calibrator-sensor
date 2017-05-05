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

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.xenione.libs.calibrator.coordinator_system.Polar;
import com.xenione.libs.calibrator.orientation.Compensator;

public class CalibratorView extends View {

    public interface CalibrationListener {
        void onCalibrationComplete();
    }

    private static final int SPINE_LENGTH = 40;
    private static final int MARGIN = 10;

    private static final int AMOUNT = 100;
    private int lastLayerCountdown = AMOUNT;

    Matrix mProjectionMatrix;

    private Crown<Spine> mCrown;
    private Ball mBall;

    private int mSize;

    private CalibrationListener mListener;

    public CalibratorView(Context context) {
        super(context);
    }

    public CalibratorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalibratorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CalibratorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            resize();
        }
    }

    private void resize() {
        buildProjectionMatrix();
        mSize = Math.min(this.getWidth() / 2 - getPaddingLeft() - getPaddingRight(),
                this.getHeight() / 2 - getPaddingTop() - getPaddingBottom());
        mCrown = buildCrown();
        mBall = buildBall();
    }

    private void buildProjectionMatrix() {
        int width = this.getWidth();
        int height = this.getHeight();

        mProjectionMatrix = new Matrix();
        // translate origin to center
        mProjectionMatrix.setTranslate(width / 2, height / 2);
        // change y axis sense
        mProjectionMatrix.preScale(1, -1);
    }

    private Crown<Spine> buildCrown() {
        int distance = mSize - SPINE_LENGTH - MARGIN - 2 * MARGIN;
        return new Crown.Builder<Spine>()
                .from(0)
                .to(360)
                .distance(distance)
                .amount(AMOUNT)
                .drawableFactory(new DrawableFactory<Spine>() {
                    @Override
                    public Spine create(int index, Polar position) {
                        int length = SPINE_LENGTH;
                        if (index % 5 == 0) {
                            length = (int) (1.5 * SPINE_LENGTH);
                        }
                        return new Spine(position, length);
                    }
                }).build();
    }

    private Ball buildBall() {
        int radius = 25;
        int gap = 20;
        int distance = mSize - 2 * radius - SPINE_LENGTH - MARGIN - gap;
        return new Ball.Builder()
                .radius(radius)
                .distance(distance)
                .color(Color.RED)
                .build();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.concat(mProjectionMatrix);
        mBall.draw(canvas);
        mCrown.draw(canvas);
    }

    public void setOrientation(int alpha) {
        startInterpolation(alpha);
    }


    ValueAnimator animator = null;
    Compensator compensator = new Compensator();

    private void startInterpolation(int alpha) {
        if (animator == null) {
            animator = new ValueAnimator();
            animator.setIntValues(0, 0);
            animator.setDuration(50);
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    redraw((int) animation.getAnimatedValue());
                }
            });
        }
        if (animator.isStarted()) {
            animator.cancel();
        }
        int lastValue = (int) animator.getAnimatedValue();

        if (lastValue == alpha) {
            return;
        }
        animator.setIntValues(lastValue, alpha);
        Log.i("CalibratorView", "from : " + animator.getAnimatedValue() + " to : " + alpha);
        animator.start();
    }

    private void redraw(int alpha) {
        Log.i("CalibratorView", "animated alpha : " + alpha);

        mBall.setAlpha(alpha);
        setAlphaOnCrown(alpha);
        invalidate();
    }

    public void setOnCalibrationListener(CalibrationListener listener) {
        mListener = listener;
    }

    private void setAlphaOnCrown(int alpha) {
        Spine spine = mCrown.drawableAt(alpha);
        if (spine == null || spine.isLastLayer()) {
            return;
        }
        spine.addPaintLayer();
        if (spine.isLastLayer()
                && --lastLayerCountdown == 0) {
            mListener.onCalibrationComplete();
        }
    }
}
