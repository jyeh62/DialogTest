package com.p12126.dialogtest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import static android.graphics.Color.TRANSPARENT;

public class MainActivity extends Activity {

    private static final String TAG = "Main";
    private CustomDialog mCustomDialog;
    private CustomDialog mCUseCanvasDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setStatusBarColor(TRANSPARENT);
        //getWindow().setNavigationBarColor(TRANSPARENT);
        setContentView(R.layout.activity_main);
    }

    public void onClickView(View v){
        switch (v.getId()) {
            case R.id.b2_canvas:
                mCustomDialog = new CustomDialog(this, 0);
                mCustomDialog.show();
                break;
            case R.id.c_canvas:
                mCUseCanvasDialog = new CustomDialog(this,1);
                mCUseCanvasDialog.show();
                break;
        }
    }

    private View.OnClickListener leftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "왼쪽버튼 Click!!",
                    Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener rightClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "오른쪽버튼 Click!!",
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent ev = " + ev.getActionMasked());
        return super.dispatchTouchEvent(ev);
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.d(TAG, "dispatchKeyEvent event = " + event);
        return super.dispatchKeyEvent(event);
    }
}
