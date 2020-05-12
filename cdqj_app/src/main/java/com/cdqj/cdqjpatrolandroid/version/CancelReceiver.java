package com.cdqj.cdqjpatrolandroid.version;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.utils.NotificationSampleListener;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.liulishuo.okdownload.DownloadTask;

import java.io.File;

/**
 * Created by lyf on 2018/11/16 11:04
 *
 * @author lyf
 * desc：
 */
public class CancelReceiver extends BroadcastReceiver {

    public static final String ACTION = "version_update";

    public static final String ACTION_INSTALL_APP = "action_install_app";

    private DownloadTask task;

    private NotificationSampleListener listener;
    private PendingIntent cancelPendingIntent;
    private TextView view;

    public void initData(@NonNull DownloadTask task, NotificationSampleListener listener,
                         PendingIntent cancelPendingIntent, TextView view) {
        this.task = task;
        this.view = view;
        this.cancelPendingIntent = cancelPendingIntent;
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION.equals(intent.getAction())) {
            if (view.getTag() == null) {
                // need to start
                GlobalTaskManager.getImpl().enqueueTask(task, listener);
                view.setText("暂停");
                view.setTag(new Object());
                listener.setAction(new NotificationCompat.Action(R.mipmap.ic_launcher, "暂停", cancelPendingIntent));
                listener.initNotification();
            } else {
                // need to cancel
                listener.setAction(new NotificationCompat.Action(R.mipmap.ic_launcher, "继续", cancelPendingIntent));
                listener.initNotification();
                task.cancel();
            }
        } else if (ACTION_INSTALL_APP.equals(intent.getAction())){
            AppUtils.installApp(Constant.APP_PATH + File.separator + AppUtils.getAppName() + PreferencesUtil.getString(Constant.APP_VERSION) + ".apk");
        }
    }
}
