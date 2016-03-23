package com.p12126.dialogtest;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by P12126 on 2016-01-21.
 */
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class CustomDialog extends Dialog{

    private static final String TAG = "CustomDialog";
    private int mType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setCanceledOnTouchOutside(true);
        final DisplayMetrics dm = getContext().getResources().getDisplayMetrics();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = dm.widthPixels;
        lp.height = dm.heightPixels;
        getWindow().setAttributes(lp);

        setContentView(R.layout.custom_dialog);
        CircleView mCircle = (CircleView)findViewById(R.id.circle_view);
        mCircle.setType(mType);
        final TextView mTextView = (TextView)findViewById(R.id.volume_value);

        mCircle.setOnAngleChangedListener(new OnAngleChangedListener() {
            @Override
            public void onAngleChanged(float angle) {
                mTextView.setText(""+angle);
            }
        });
    }

    public CustomDialog(Context context) {
        // Dialog 배경을 투명 처리 해준다.
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
    }

    public CustomDialog(Context context , int type) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
        mType = type;
    }

    public CustomDialog(Context context , String title ,
                        View.OnClickListener singleListener) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
    }

    public CustomDialog(Context context , String title , String content ,
                        View.OnClickListener leftListener , View.OnClickListener rightListener) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean ret = super.dispatchTouchEvent(ev);
        Log.d(TAG, "dispatchTouchEvent event = " + ev.getActionMasked() + ", ret = " + ret);
        return false;
    }
}
