package com.cdqj.cdqjpatrolandroid.version;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.LoginResultBean;
import com.cdqj.cdqjpatrolandroid.utils.NotificationSampleListener;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.StatusUtil;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

/**
 * Created by lyf on 2018/11/16 10:33
 *
 * @author lyf
 * desc：版本更新管理
 */
public class VersionHelper {

    /**
     * 声明升级弹窗对象
     */
    private TextView tvVersionCode;
    private TextView tvVersionText;
    private TextView tvSubmitUpdate;
    private ImageView ivEsc;

    private DialogPlus mDialogPlus;

    private Context mContext;
    private LoginResultBean userInfo;

    private DownloadTask task;
    private NotificationSampleListener listener;
    private PendingIntent cancelPendingIntent;

    @SuppressLint("InflateParams")
    public VersionHelper(Context context, LoginResultBean userInfo, CancelReceiver cancelReceiver) {
        mContext = context;
        this.userInfo = userInfo;
        // 初始化升级弹窗
        View mView = LayoutInflater.from(mContext).inflate(R.layout.cdqj_patrol_my_version_update_window, null);
        tvVersionCode = mView.findViewById(R.id.window_version_code);
        tvVersionText = mView.findViewById(R.id.window_content);
        tvSubmitUpdate = mView.findViewById(R.id.window_update_btn);
        ivEsc = mView.findViewById(R.id.window_esc);

        // 版本更新
        mDialogPlus = DialogPlus.newDialog(mContext)
                .setContentHolder(new ViewHolder(mView))
                .setContentWidth(ScreenUtils.getScreenWidth() / 6 * 5)
                .setContentHeight(ScreenUtils.getScreenHeight() / 5 * 3)
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setContentBackgroundResource(R.drawable.stroke_bg_radius_transparent)
                .create();

        initTask();
        initListener();

        GlobalTaskManager.getImpl().attachListener(task, listener);
        GlobalTaskManager.getImpl().addAutoRemoveListenersWhenTaskEnd(task.getId());

        cancelReceiver.initData(task, listener, cancelPendingIntent, tvSubmitUpdate);
    }

    @SuppressLint("SetTextI18n")
    public void show() {
        setWindow();
        // 版本信息初始化
        tvVersionCode.setText("V" + userInfo.getAppinfos().getVersion());
        tvVersionText.setText(StringUtils.isTrimEmpty(userInfo.getAppinfos().getDiscript()) ? "暂无更新内容" :
                userInfo.getAppinfos().getDiscript());
        mDialogPlus.show();
    }

    /**
     * 初始化弹窗
     */
    private void setWindow() {
        setWindowOnClick();

        if (StatusUtil.isSameTaskPendingOrRunning(task)) {
            tvSubmitUpdate.setText("暂停");
            tvSubmitUpdate.setTag(new Object());
        }
    }

    private void setWindowOnClick() {
        tvSubmitUpdate.setOnClickListener(v -> {
            if (ObjectUtils.isNotEmpty(mDialogPlus) && mDialogPlus.isShowing()) {
                mDialogPlus.dismiss();
            }
            if (v.getTag() == null) {
                // need to start
                listener.setAction(new NotificationCompat.Action(R.mipmap.ic_launcher, "暂停", cancelPendingIntent));
                listener.initNotification();
                GlobalTaskManager.getImpl().enqueueTask(task, listener);
                ((TextView) v).setText("暂停");
                v.setTag(new Object());
            } else {
                // need to cancel
                listener.setAction(new NotificationCompat.Action(R.mipmap.ic_launcher, "继续", cancelPendingIntent));
                listener.initNotification();
                task.cancel();
            }
        });
        ivEsc.setOnClickListener(v -> {
            if (ObjectUtils.isNotEmpty(mDialogPlus) && mDialogPlus.isShowing()) {
                mDialogPlus.dismiss();
            }
        });
    }

    private void initListener() {
        listener = NotificationSampleListener.getInstance(mContext);
        listener.attachTaskEndRunnable(() -> {
            tvSubmitUpdate.setText("立即更新");
            tvSubmitUpdate.setTag(null);
        });

        final Intent intent = new Intent(CancelReceiver.ACTION);
        cancelPendingIntent = PendingIntent.getBroadcast(mContext, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        listener.setAction(new NotificationCompat.Action(R.mipmap.ic_launcher, "暂停", cancelPendingIntent));
        listener.initNotification();
    }

    private void initTask() {
        String filePath = "";
        if (ObjectUtils.isNotEmpty(userInfo)
                && ObjectUtils.isNotEmpty(userInfo.getAppinfos())) {
            filePath = PreferencesUtil.getString(Constant.FILE_SERVICE_PATH) + userInfo.getAppinfos().getFilepath();
        }
        if (StringUtils.isTrimEmpty(filePath)) {
            ToastBuilder.showShortWarning("下载地址出错");
            return;
        }
        task = new DownloadTask
                .Builder(filePath, FileUtils.getFileByPath(Constant.APP_PATH))
                .setFilename(AppUtils.getAppName() + PreferencesUtil.getString(Constant.APP_VERSION) + ".apk")
                // if there is the same task has been completed download, just delete it and
                // re-download automatically.
                .setPassIfAlreadyCompleted(false)
                .setMinIntervalMillisCallbackProcess(80)
                // because for the notification we don't need make sure invoke on the ui thread, so
                // just let callback no need callback to the ui thread.
                .setAutoCallbackToUIThread(false)
                .build();
    }
}
