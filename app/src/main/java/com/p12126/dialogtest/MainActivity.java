package com.p12126.dialogtest;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaRouter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import static android.graphics.Color.TRANSPARENT;
import android.provider.Settings.System;

public class MainActivity extends Activity implements VolumeCallback {

    private static final String TAG = "Main";
    private CustomDialog mCustomDialog;
    private CustomDialog mCUseCanvasDialog;
    private VolumeObserver mVolumeObserver;
    private VolumeReceiver mVolumeReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mVolumeObserver = new VolumeObserver(this, new Handler());
        mVolumeReceiver = new VolumeReceiver();
        mVolumeReceiver.setOnVolumeChanged(this);

        IntentFilter filter = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
        registerReceiver(mVolumeReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mVolumeReceiver);
        super.onDestroy();
    }

    public void onClickView(View v){
        switch (v.getId()) {
            case R.id.b2_canvas:
                mCustomDialog = new CustomDialog(this, 0);
                mCustomDialog.show();
                break;
            case R.id.c_canvas:
                mCustomDialog = new CustomDialog(this,1);
                mCustomDialog.show();
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
    protected void onResume() {
        super.onResume();
        //getApplicationContext().getContentResolver()
        //        .registerContentObserver(System.CONTENT_URI, true, mVolumeObserver);
    }

    @Override
    protected void onPause() {
        //getApplicationContext().getContentResolver()
        //        .unregisterContentObserver(mVolumeObserver);
        super.onPause();
    }

    @Override
    public void onVolumeChanged(int volume) {
        Log.i(TAG, "onVolumeChanged volume = " + volume);
        if (mCustomDialog != null) {
            mCustomDialog.setValue(volume);
        }
    }
}
