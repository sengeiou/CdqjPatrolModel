package com.cdqj.cdqjpatrolandroid.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

import com.cdqj.cdqjpatrolandroid.R;

/**
 * Created by lyf on 2018/6/1 16:11
 *
 * @author lyf
 * desc：
 */
public class LocationNotificationUtils extends ContextWrapper{

    private NotificationManager mManager;

    public LocationNotificationUtils(Context base) {
        super(base);
        createChannels();
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels() {
        // create android channel
        NotificationChannel androidChannel = new NotificationChannel(Constant.ANDROID_CHANNEL_ID,
                Constant.ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        // 是否在桌面icon右上角展示小红点
        androidChannel.enableLights(true);
        // 设置发布到这个频道的通知是否应该振动。
        androidChannel.enableVibration(true);
        // 小红点颜色
        androidChannel.setLightColor(Color.GREEN);
        // 是否在久按桌面图标时显示此渠道的通知
        androidChannel.setShowBadge(true);
        // 设置发布到该频道的通知是否出现在锁定屏幕上
        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(androidChannel);

    }

    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getAndroidChannelNotification(String title, String body) {
        return new Notification.Builder(getApplicationContext(), Constant.ANDROID_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);
    }
}
