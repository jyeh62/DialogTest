package com.p12126.dialogtest;

import android.graphics.Canvas;

/**
 * Created by P12126 on 2016-03-22.
 */
public interface Circle {
    void drawCircle(Canvas canvas);
    void setValue(float value);
    float getValue();
    void onLayout(boolean changed, int left, int top, int right, int bottom);

    void setMax(int max);
    void setCurrentValue(int value);
}
