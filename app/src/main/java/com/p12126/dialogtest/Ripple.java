package com.p12126.dialogtest;

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
    private static final String TAG = "Ripple";
    private float mValue = 0f;
    private float mCenterY;
    private float mCenterX;
    private float mLeft;
    private float mRight;
    private float mRadius;
    private Path mPath;
    private boolean mEndAnimating;
    private float mSurfaceDegree;
    private static final int MAX_CIRCLE = 5;

    private int mWidth;
    private int mHeight;
    private int mStep = 30;
    private int[] mColors = { 0xFF00FFFF, 0xfFFF00FF, 0x3F023FFF, 0x0AF0032FF, 0x0500F0FF};

    public Ripple() {
        mPath = new Path();
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

        canvas.save();
        mPath.reset();
        canvas.clipPath(mPath); // makes the clip empty
        // 클립영역으로 원이 출력이 됩니다.
        // 원 부분만 출력이 되고 나머지는 배경이 나오게 됩니다.
        mPath.addCircle(mCenterX, mCenterY, mValue, Path.Direction.CCW);
        canvas.clipPath(mPath, Region.Op.REPLACE);
        for(int i = 0 ; i<MAX_CIRCLE; i++) {
            paint.setColor(mColors[i]);
            canvas.drawCircle(mCenterX, mCenterY, mRadius - (mStep * i), paint);
        }
    }

    @Override
    public void setValue(float value) {
        mValue = value;
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
            mStep = (int) (mRadius / MAX_CIRCLE);
            Log.d(TAG, "onLayout "
                    + ", center x = " + mCenterX
                    + ", y = " + mCenterY
                    + ", mRadius = " + mRadius
                    + ", mStep = " + mStep);
        }
    }
}
