package com.p12126.dialogtest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by P12126 on 2016-01-21.
 */
public class CircleView extends RelativeLayout {

    private static final String TAG = "CircleView";
    private static final float MAX_SURFAE_DEGREE = 10f;
    private static final int MAX_CIRCLE = 5;
    private final Path mPath;
    float width;

    float height;
    float center_x, center_y;
    final RectF oval = new RectF();

    float sweep = 0;
    float left, right;
    int percent = 0;
    float mX;
    float mY;
    OnAngleChangedListener mListener;
    int circleWidth = 0;
    private float mRadius = 0;
    private float mSurfaceDegree = 0f;
    private boolean mEndAnimating = false;
    private int mStep = 30;
    private int[] mColors = { 0xFF00FFFF, 0xfFFF00FF, 0x3F023FFF, 0x0AF0032FF, 0x0500F0FF};
    private int mType;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //mRadius = mRadius - 9; //?
        drawCircirB(canvas);
        //drawCircirC(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xPosition = event.getX();
        float yPosition = event.getY();
        float distY = yPosition - center_y;
        float distX = xPosition - center_x;

        int action = event.getActionMasked();
        Log.d(TAG, "onTouhEvent " +
                " action = " + action
                + " x = " + xPosition
                + ", y = " + yPosition);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mSurfaceDegree = MAX_SURFAE_DEGREE;
                break;
            case MotionEvent.ACTION_MOVE:
                float dist = distance(center_x, center_y, xPosition, yPosition);
                Log.d(TAG, "onTouhEvent "
                        + ", dist = " + dist);
                circleWidth = (int)dist;
                mEndAnimating = true;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //startSurfaceAnimation(MAX_SURFAE_DEGREE, -MAX_SURFAE_DEGREE);
                break;
        }

        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            center_x = (right - left) / 2;
            center_y = (bottom - top) / 2;

            width = (float) (right - left);
            height = (float) (bottom - top);
            if (width > height) {
                mRadius = height / 2;
            } else {
                mRadius = width / 2;
            }
            mStep = (int) (mRadius / MAX_CIRCLE);
            Log.d(TAG, "onLayout "
                    + ", center x = " + center_x
                    + ", y = " + center_y
                    + ", mRadius = " + mRadius
                    + ", mStep = " + mStep);
        }
    }

    float distance(float x, float y, float a, float b)
    {
        return (float)Math.sqrt((x - a) * (x - a) + (y - b) * (y - b));
    }

    public void setOnAngleChangedListener( OnAngleChangedListener l) {
        mListener = l;
    }

    private void startCircleAnimation(final int start, final int stop) {
        ValueAnimator animator = ValueAnimator.ofInt(start, stop);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener () {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleWidth = (int)animation.getAnimatedValue();
                invalidate();
                //Log.v(TAG, "onAnimationUpdate mCurrentValue = " + mCurrentValue);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                ;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.v(TAG, "onAnimationCancel");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "onAnimationEnd");
            }
        });
        animator.setDuration(1000);
        animator.setRepeatMode(ValueAnimator.INFINITE);
        animator.start();
    }

    private void startSurfaceAnimation(final float start, final float stop) {
        ValueAnimator animator = ValueAnimator.ofFloat(start, stop);
        final ValueAnimator endAnimator = ValueAnimator.ofFloat(stop, 0);
        endAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSurfaceDegree = (float) animation.getAnimatedValue();
                invalidate();
                //Log.v(TAG, "onAnimationUpdate mCurrentValue = " + mCurrentValue);
            }
        });

        endAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                Log.v(TAG, "end onAnimationCancel");
                mEndAnimating = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "end onAnimationEnd");
                mEndAnimating = false;
            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSurfaceDegree = (float) animation.getAnimatedValue();
                invalidate();
                //Log.v(TAG, "onAnimationUpdate mCurrentValue = " + mCurrentValue);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                ;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.v(TAG, "surface onAnimationCancel");
                mSurfaceDegree = 0f;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "surface onAnimationEnd");
                endAnimator.start();
            }
        });
        animator.setDuration(500);
        animator.setRepeatMode(ValueAnimator.INFINITE);

        animator.start();
    }

    private void drawCircirB(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        //paint.setStrokeWidth(3);

        paint.setStyle(Paint.Style.FILL);

        center_x = width / 2;
        center_y = height / 2;

        left = center_x - mRadius;
        float top = center_y - mRadius;
        right = center_x + mRadius;
        float bottom = center_y + mRadius;

        canvas.save();
        mPath.reset();
        canvas.clipPath(mPath); // makes the clip empty
        // 클립영역으로 원이 출력이 됩니다.
        // 원 부분만 출력이 되고 나머지는 배경이 나오게 됩니다.
        mPath.addCircle(center_x, center_y, circleWidth, Path.Direction.CCW);
        canvas.clipPath(mPath, Region.Op.REPLACE);
        for(int i = 0 ; i<MAX_CIRCLE; i++) {
            paint.setColor(mColors[i]);
            canvas.drawCircle(center_x, center_y, mRadius - (mStep * i), paint);
        }
    }

    private void drawCircirC(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        //paint.setStrokeWidth(3);

        paint.setStyle(Paint.Style.FILL);

        center_x = width / 2;
        center_y = height / 2;

        left = center_x - mRadius;
        float top = center_y - mRadius;
        right = center_x + mRadius;
        float bottom = center_y + mRadius;
        canvas.drawCircle(center_x, center_y, mRadius, paint);
        canvas.save();
        mPath.reset();
        canvas.clipPath(mPath); // makes the clip empty
        // 클립영역으로 원이 출력이 됩니다.
        // 원 부분만 출력이 되고 나머지는 배경이 나오게 됩니다.
        mPath.addCircle(center_x, center_y, mRadius, Path.Direction.CCW);
        canvas.clipPath(mPath, Region.Op.REPLACE);
        if (mEndAnimating) {
            canvas.save();
            canvas.rotate(-mSurfaceDegree, center_x, center_y);
            paint.setColor(Color.GRAY);
            canvas.drawRect(left, circleWidth, right, bottom, paint);
            canvas.restore();
        }
        canvas.save();
        canvas.rotate(mSurfaceDegree, center_x, center_y);
        paint.setColor(Color.RED);
        canvas.drawRect(left, circleWidth, right, bottom, paint);
        canvas.restore();

        canvas.restore();
    }
    public void setType(int type) {
        mType = type;
    }

}
