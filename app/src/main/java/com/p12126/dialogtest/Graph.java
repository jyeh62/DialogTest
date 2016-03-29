package com.p12126.dialogtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Region;
import android.util.Log;

/**
 * Created by P12126 on 2016-03-22.
 */
public class Graph implements Circle {
    private static final String TAG = "Graph";
    private final Bitmap mMask;
    private final Bitmap sketchBook;
    private Context mContext;
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
    private float mMax;
    private Paint mBackgoundPaint;
    private Paint mPaint;
    private int mGraphColor = 0xff249af7;

    public Graph(Context context) {
        mContext = context;
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);

        mBackgoundPaint = new Paint();
        mBackgoundPaint.setAntiAlias(true);
        mBackgoundPaint.setColor(Color.TRANSPARENT);
        mBackgoundPaint.setStyle(Paint.Style.FILL);
        mMask = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.volume_bg_mute);
        sketchBook = Bitmap.createBitmap(mMask.getWidth(), mMask.getHeight(), Bitmap.Config.ARGB_8888);

    }
    @Override
    public void drawCircle(Canvas canvas) {
        Canvas sketchBookCanvas = new Canvas(sketchBook);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mLeft = mCenterX - mRadius;
        float top = mCenterY - mRadius;
        mRight = mCenterX + mRadius;
        float bottom = mCenterY + mRadius;

        mPaint.setColor(mGraphColor);

        //sketchBookCanvas.drawCircle(mCenterX, mCenterY, mRadius, mBackgoundPaint);
        sketchBookCanvas.drawRect(mLeft, bottom - mValue, mRight, bottom, mPaint);
        sketchBookCanvas.drawBitmap(mMask, mLeft, top, paint);


        canvas.drawBitmap(sketchBook,  0, 0, new Paint());
        /*
        mPaint.setAntiAlias(true);
        mPaint.setColor(mGraphColor);
        //paint.setStrokemWidth(3);
        mPaint.setAlpha(255);
        mPaint.setStyle(Paint.Style.FILL);

        mLeft = mCenterX - mRadius;
        float top = mCenterY - mRadius;
        mRight = mCenterX + mRadius;
        float bottom = mCenterY + mRadius;
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mBackgoundPaint);
        canvas.save();

        //mPath.reset();
        //canvas.clipPath(mPath); // makes the clip empty
        //mPath.addCircle(mCenterX, mCenterY, mRadius, Path.Direction.CCW);
        //canvas.clipPath(mPath, Region.Op.REPLACE);

        if (mEndAnimating) {
            canvas.save();
            canvas.rotate(-mSurfaceDegree, mCenterX, mCenterY);
            mPaint.setColor(mGraphColor);
            mPaint.setAlpha(127);
            canvas.drawRect(mLeft, mValue, mRight, bottom, mPaint);
            canvas.restore();
        }
        canvas.save();
        canvas.rotate(mSurfaceDegree, mCenterX, mCenterY);

        mPaint.setAlpha(255);
        canvas.drawRect(mLeft, bottom - mValue, mRight, bottom, mPaint);
        canvas.restore();

        canvas.restore();
        */
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
            if (mMax == 0) {
                mMax = mHeight;
            }
        }
    }
    public void setCurrentValue(int value) {
        float convertedValue = (value / mMax) * (mHeight);
        Log.v(TAG, "setCurrentValue = " + convertedValue);
        setValue(convertedValue);
    }

    public void setMax(int max) {
        mMax = max;
    }
}
