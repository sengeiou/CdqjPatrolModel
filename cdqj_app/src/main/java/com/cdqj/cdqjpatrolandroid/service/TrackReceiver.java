package com.cdqj.cdqjpatrolandroid.service;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager.WakeLock;

import com.baidu.trace.model.StatusCodes;
import com.blankj.utilcode.util.LogUtils;

public class TrackReceiver extends BroadcastReceiver {

    private WakeLock wakeLock = null;

    public TrackReceiver(WakeLock wakeLock) {
        super();
        this.wakeLock = wakeLock;
    }

    @SuppressLint({"Wakelock", "DefaultLocale"})
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            if (null != wakeLock && !(wakeLock.isHeld())) {
                wakeLock.acquire();
            }
        } else if (Intent.ACTION_SCREEN_ON.equals(action) || Intent.ACTION_USER_PRESENT.equals(action)) {
            if (null != wakeLock && wakeLock.isHeld()) {
                wakeLock.release();
            }
        } else if (StatusCodes.GPS_STATUS_ACTION.equals(action)) {
            int statusCode = intent.getIntExtra("statusCode", 0);
            String statusMessage = intent.getStringExtra("statusMessage");
            LogUtils.d(String.format("GPS status, statusCode:%d, statusMessage:%s", statusCode,
                    statusMessage));

        }
    }

}
