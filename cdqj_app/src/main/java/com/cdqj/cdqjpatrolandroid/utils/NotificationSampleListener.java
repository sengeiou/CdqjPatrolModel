/*
 * Copyright (c) 2018 LingoChamp Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cdqj.cdqjpatrolandroid.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;
import com.cdqj.cdqjpatrolandroid.http.PatrolApiService;
import com.cdqj.cdqjpatrolandroid.http.RetrofitManager;
import com.cdqj.cdqjpatrolandroid.version.CancelReceiver;
import com.cdqj.cdqjpatrolandroid.version.VersionHelper;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.SpeedCalculator;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed;
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 版本升级通知
 */
public class NotificationSampleListener extends DownloadListener4WithSpeed {

    private int totalLength;

    private static NotificationSampleListener mInstance;

    private NotificationCompat.Builder builder;
    private NotificationManager manager;
    private Runnable taskEndRunnable;
    private Context context;

    public static NotificationSampleListener getInstance(Context context) {
        if (mInstance == null) {
            synchronized (VersionHelper.class) {
                mInstance = new NotificationSampleListener(context);
            }
        }
        return mInstance;
    }

    private NotificationCompat.Action action;

    private NotificationSampleListener(Context context) {
        this.context = context.getApplicationContext();
    }

    public void attachTaskEndRunnable(Runnable taskEndRunnable) {
        this.taskEndRunnable = taskEndRunnable;
    }

    public void releaseTaskEndRunnable() {
        taskEndRunnable = null;
    }

    public NotificationCompat.Action getAction() {
        return this.action;
    }

    public void setAction(NotificationCompat.Action action) {
        this.action = action;
    }

    public void initNotification() {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final NotificationChannel channel = new NotificationChannel(
                    Constant.APP_DIR,
                    AppUtils.getAppName(),
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        builder = new NotificationCompat.Builder(context, Constant.APP_DIR);


        builder.setDefaults(Notification.DEFAULT_ALL)
                .setOngoing(true)
                // 只执行一次震动等
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentTitle(AppUtils.getAppName())
                .setContentText(AppUtils.getAppName() + "更新中...")
                .setTicker(AppUtils.getAppName() + "版本更新")
                .setSmallIcon(R.mipmap.ic_launcher);


        if (action != null) {
            builder.addAction(action);
        }
    }

    @Override
    public void taskStart(@NonNull DownloadTask task) {
        builder.setTicker("taskStart");
        builder.setOngoing(true);
        builder.setAutoCancel(false);
        builder.setContentText("The task is started");
        builder.setProgress(0, 0, true);
        manager.notify(task.getId(), builder.build());
    }

    @Override
    public void connectStart(@NonNull DownloadTask task, int blockIndex,
                             @NonNull Map<String, List<String>> requestHeaderFields) {
        builder.setTicker("connectStart");
        builder.setContentText(
                "The connect of " + blockIndex + " block for this task is connecting");
        builder.setProgress(0, 0, true);
        manager.notify(task.getId(), builder.build());
    }

    @Override
    public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode,
                           @NonNull Map<String, List<String>> responseHeaderFields) {
        builder.setTicker("connectStart");
        builder.setContentText(
                "The connect of " + blockIndex + " block for this task is connected");
        builder.setProgress(0, 0, true);
        manager.notify(task.getId(), builder.build());
    }

    @Override
    public void infoReady(@NonNull DownloadTask task, @NonNull BreakpointInfo info,
                          boolean fromBreakpoint,
                          @NonNull Listener4SpeedAssistExtend.Listener4SpeedModel model) {
        if (fromBreakpoint) {
            builder.setTicker("断点下载");
        } else {
            builder.setTicker("断点下载准备");
        }
        builder.setContentText(
                "任务断点下载[" + fromBreakpoint + "]");
        builder.setProgress((int) info.getTotalLength(), (int) info.getTotalOffset(), true);
        manager.notify(task.getId(), builder.build());

        totalLength = (int) info.getTotalLength();
    }

    @Override
    public void progressBlock(@NonNull DownloadTask task, int blockIndex,
                              long currentBlockOffset,
                              @NonNull SpeedCalculator blockSpeed) {
    }

    @Override
    public void progress(@NonNull DownloadTask task, long currentOffset,
                         @NonNull SpeedCalculator taskSpeed) {
        builder.setContentText("速度: " + taskSpeed.speed());
        builder.setProgress(totalLength, (int) currentOffset, false);
        manager.notify(task.getId(), builder.build());
    }

    @Override
    public void blockEnd(@NonNull DownloadTask task, int blockIndex, BlockInfo info,
                         @NonNull SpeedCalculator blockSpeed) {
    }

    @Override
    public void taskEnd(@NonNull final DownloadTask task, @NonNull EndCause cause,
                        @android.support.annotation.Nullable Exception realCause,
                        @NonNull SpeedCalculator taskSpeed) {
        builder.setOngoing(false);
        builder.setAutoCancel(true);

        builder.setTicker("任务终止 " + cause);
        builder.setContentText(
                "任务终止：" + cause + " 终止速度: " + taskSpeed.averageSpeed());
        if (cause == EndCause.COMPLETED) {
            builder.setTicker("任务下载完成 " + cause);
            builder.setContentText("新版本下载完成");
            builder.setProgress(1, 1, false);
            builder.addAction(new NotificationCompat.Action(R.mipmap.ic_launcher, "点击安装", PendingIntent.getBroadcast(context,
                    0, new Intent(CancelReceiver.ACTION_INSTALL_APP), PendingIntent.FLAG_UPDATE_CURRENT)));

            Map<String, String> map = new HashMap<>(5);
            map.put("imei", DeviceUtils.getAndroidID());
            map.put("appOs", DeviceUtils.getSDKVersionName());
            map.put("afterVersion", PreferencesUtil.getString(Constant.APP_VERSION));
            map.put("beforeVersion", AppUtils.getAppVersionName());
            map.put("networkType", NetworkUtils.getNetworkType() + "");
            updateDownloadNum(map);
            LogUtils.e("version_update", map.toString());
        }

        // because of on some android phone too frequency notify for same id would be
        // ignored.
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (taskEndRunnable != null) {
                taskEndRunnable.run();
            }

            manager.notify(task.getId(), builder.build());
        }, 100);
    }

    private void updateDownloadNum(Map<String, String> version) {
        RetrofitManager.getInstance().create(PatrolApiService.class)
                .updateDownloadNum(version)
                .compose(TransformUtils.defaultSchedulers())
                .subscribe(new BaseSubscriber<BasePostResponse<Object>>() {
                    @Override
                    public void onResult(BasePostResponse<Object> objectBasePostResponse) {
                        ToastBuilder.showShort(objectBasePostResponse.getMsg());
                        // 下载成功后安装APP 且重置请求地址（请求地址改变的因素）
                        //PreferencesUtil.putString(Constant.REQUEST_ADDRESS, "");
                        //PreferencesUtil.putString(Constant.FILE_SERVICE_PATH, "");
                        PreferencesUtil.putBoolean(Constant.INSTALL_APP, true);
                        AppUtils.installApp(Constant.APP_PATH + File.separator + AppUtils.getAppName() + PreferencesUtil.getString(Constant.APP_VERSION) + ".apk");
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        AppUtils.installApp(Constant.APP_PATH + File.separator + AppUtils.getAppName() + PreferencesUtil.getString(Constant.APP_VERSION) + ".apk");
                    }
                });
    }
}
