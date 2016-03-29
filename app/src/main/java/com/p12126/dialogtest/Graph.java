package com.p12126.dialogtest;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;

/**
 * Created by P12126 on 2016-03-22.
 */
public class Graph implements Circle {
    private static final String TAG = "Graph";
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

    public Graph() {
        mPath = new Path();
    }
    @Override
    public void drawCircle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        //paint.setStrokemWidth(3);

        paint.setStyle(Paint.Style.FILL);

        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;

        mLeft = mCenterX - mRadius;
        float top = mCenterY - mRadius;
        mRight = mCenterX + mRadius;
        float bottom = mCenterY + mRadius;
        canvas.drawCircle(mCenterX, mCenterY, mRadius, paint);
        canvas.save();
        mPath.reset();
        canvas.clipPath(mPath); // makes the clip empty
        // 클립영역으로 원이 출력이 됩니다.
        // 원 부분만 출력이 되고 나머지는 배경이 나오게 됩니다.
        mPath.addCircle(mCenterX, mCenterY, mRadius, Path.Direction.CCW);
        canvas.clipPath(mPath, Region.Op.REPLACE);
        if (mEndAnimating) {
            canvas.save();
            canvas.rotate(-mSurfaceDegree, mCenterX, mCenterY);
            paint.setColor(Color.GRAY);
            canvas.drawRect(mLeft, mValue, mRight, bottom, paint);
            canvas.restore();
        }
        canvas.save();
        canvas.rotate(mSurfaceDegree, mCenterX, mCenterY);
        paint.setColor(Color.RED);
        canvas.drawRect(mLeft, mValue, mRight, bottom, paint);
        canvas.restore();

        canvas.restore();
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
        }
    }
}
