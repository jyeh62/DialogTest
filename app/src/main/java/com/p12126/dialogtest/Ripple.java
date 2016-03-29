package com.p12126.dialogtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.Log;

/**
 * Created by P12126 on 2016-03-29.
 */
public class Ripple implements Circle {
    public static final String TAG = "Ripple";
    public static final float OUTER_RIPPLE_ALPHA;
    private static final float CORRECT_NUMBER = 3f;
    private InnerRipple [] mInnerRipple;
    private static final int MAX_COUNT_CIRCLE = 4;
    private static final int OUTEST_CIRCLE = MAX_COUNT_CIRCLE-1;
    private int[] mColors = { 0xff249af7, 0xff1684db, 0xff0865ae, 0xff135180};
    static {
        OUTER_RIPPLE_ALPHA = 255 * 0.8f;
    }
    private float mValue = 0f;
    private float mCenterY;
    private float mCenterX;
    private float mLeft;
    private float mRight;
    private float mRadius;
    private Path mPath;
    private boolean mEndAnimating;
    private float mSurfaceDegree;


    private int mWidth;
    private int mHeight;
    private int mStep = 30;

    private float mMax;
    private float mCoreCircleRadius;
    private float mMaxCircleRadius;
    private final float UNIT_MAX;

    private int mMaxLevelZero = 0;
    private int mCurreuntValue;
    private int mRippleOrder;

    {

        mInnerRipple = new InnerRipple[MAX_COUNT_CIRCLE];
        for (int i = 0; i<MAX_COUNT_CIRCLE; i++) {
            mInnerRipple[i] = new InnerRipple(i, mColors[i]);
        }
    }

    public Ripple(Context context) {
        mPath = new Path();
        mCoreCircleRadius = context.getResources().getDimensionPixelSize(R.dimen.common_circle_raidus);
        mMaxCircleRadius = context.getResources().getDimensionPixelSize(R.dimen.ripple_circle_raidus);
        UNIT_MAX = mMaxCircleRadius - mCoreCircleRadius;
    }
    @Override
    public void drawCircle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        //paint.setStrokeWidth(3);

        paint.setStyle(Paint.Style.FILL);

        mLeft = mCenterX - mRadius;
        float top = mCenterY - mRadius;
        mRight = mCenterX + mRadius;
        float bottom = mCenterY + mRadius;


        /*
        mPath.reset();
        canvas.clipPath(mPath); // makes the clip empty
        mPath.addCircle(mCenterX, mCenterY, mCoreCircleRadius+mValue, Path.Direction.CCW);
        canvas.clipPath(mPath, Region.Op.REPLACE);
        */
        float radius = 0f;
        for(int i = OUTEST_CIRCLE ; i >= 0; i--) {
            radius = mRadius - (mStep * (OUTEST_CIRCLE - i));
            //Log.v(TAG, "draw value = " + mValue + ", radius = " + radius);
            if (mRippleOrder == i) {
                if (i == 0) {
                    radius = mCoreCircleRadius;
                } else {
                    radius = mValue;
                }
            }
            Log.v(TAG, "draw value = " + mValue
                    + ", radius = " + radius
                    + ", boundary = " + (mRadius - (mStep * (OUTEST_CIRCLE - i)))
                    + ", mCoreCircleRadius = " + mCoreCircleRadius);
            mInnerRipple[i].drawRipple(canvas, mRippleOrder, radius,  paint);
            //canvas.drawCircle(mCenterX, mCenterY, mRadius - (mStep * i), paint);
        }

    }

    @Override
    public void setValue(float value) {
        mValue = value;
        float min = 0;
        float max = mCoreCircleRadius;
        for(int i = 0 ; i<MAX_COUNT_CIRCLE; i++) {
            Log.v(TAG, "setValue min = " + min + ", max = " +  max);
            if (min <= value && max > value) {
                mRippleOrder = i;
                break;
            }
            min = mCoreCircleRadius + mStep * i;
            max = min + mStep;
        }
        Log.v(TAG, "setValue value = " + value + ", mRippleOrder = " + mRippleOrder);
    }

    @Override
    public float getValue() {
        return mValue;
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            mCenterX = (right - left) / 2;
            mCenterY = (bottom - top) / 2;
            mWidth = (right - left);
            mHeight = (bottom - top);
            if (mWidth > mHeight) {
                mRadius = mHeight / 2;
            } else {
                mRadius = mWidth / 2;
            }

            mStep = (int) ((mRadius - mCoreCircleRadius) / OUTEST_CIRCLE);
            Log.d(TAG, "onLayout "
                    + ", left = " + left
                    + ", right = " + right
                    + ", center x = " + mCenterX
                    + ", y = " + mCenterY
                    + ", mRadius = " + mRadius
                    + ", mStep = " + mStep);
            if (mMax == 0) {
                mMax = mRadius;
            }
        }
    }

    public void setCurrentValue(int value) {
        mCurreuntValue = value;
        float convertedValue = 0f;
        if (value > mMaxLevelZero) {
            convertedValue = (mCoreCircleRadius - CORRECT_NUMBER) + (((value - mMaxLevelZero)  * (mMaxCircleRadius - mCoreCircleRadius)) / (mMax - mMaxLevelZero));
        } else {
            convertedValue = (value * mCoreCircleRadius) / mMaxLevelZero;
        }
        Log.v(TAG, "setCurrentValue = " + convertedValue);
        setValue(convertedValue);
    }

    public void setMax(int max) {        
        mMax = max;
        mMaxLevelZero = (int)(mMax  / MAX_COUNT_CIRCLE);
    }

    class InnerRipple {

        private int mOrder;
        private int mColor;
        public InnerRipple(int order, int color) {
            mOrder = order;
            mColor = color;
        }
        public void drawRipple(Canvas canvas, int order, float radius, Paint paint) {

             if (mOrder > order) {
                return;
            }
            canvas.save();
            paint.setColor(mColor);
            if (mOrder == order && mOrder != 0) {
                paint.setAlpha((int) OUTER_RIPPLE_ALPHA);
            }
            canvas.drawCircle(mCenterX, mCenterY, radius, paint);
            canvas.restore();
        }
    }
}
