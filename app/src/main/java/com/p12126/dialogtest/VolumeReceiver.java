package com.p12126.dialogtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRouter;
import android.util.Log;

/**
 * Created by P12126 on 2016-03-29.
 */
public class VolumeReceiver extends BroadcastReceiver{
    VolumeCallback mCallback;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
            Log.d("Music Stream", "has changed");
            int newVolume = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
            int oldVolume = intent.getIntExtra("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", 0);
            if (mCallback != null) {
                mCallback.onVolumeChanged(newVolume);
            }
        }
    }

    public void setOnVolumeChanged(VolumeCallback callback) {
        mCallback = callback;
    }
}
