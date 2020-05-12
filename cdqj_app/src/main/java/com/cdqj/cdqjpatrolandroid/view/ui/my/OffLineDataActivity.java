package com.cdqj.cdqjpatrolandroid.view.ui.my;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.comstomview.recyclerview.SpacesItemDecoration;
import com.cdqj.cdqjpatrolandroid.download.queue.QueueDownLoadBean;
import com.cdqj.cdqjpatrolandroid.download.task.TaskUtil;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.OfflineDataAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.SpeedCalculator;
import com.liulishuo.okdownload.core.Util;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed;
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 离线数据管理
 *
 * @author lyf
 * @date 2018年8月15日 10:20:42
 */
public class OffLineDataActivity extends BaseActivity {

    BaseRecyclerView offlineDataRv;
    SmartRefreshLayout offlineDataRefreshLayout;

    private List<QueueDownLoadBean> urlList;
    private List<DownloadTask> taskList;

    /**
     * 界面初始化
     */
    @SuppressLint("InflateParams")
    @Override
    public void initView() {
        offlineDataRefreshLayout.setEnablePureScrollMode(true);
        offlineDataRv.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(5)));

        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Object());
        }
        offlineDataRv.setLayoutManager(new LinearLayoutManager(this));
        OfflineDataAdapter adapter = new OfflineDataAdapter(R.layout.cdqj_patrol_offline_data_item, list, this);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.setEmptyView(
                getLayoutInflater().inflate(R.layout.cdqj_patrol_list_empty_view, null));
        adapter.bindToRecyclerView(offlineDataRv);
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            int i = view.getId();
            if (i == R.id.offline_data_update) {
                DownloadTask task = taskList.get(position);
                final boolean started = task.getTag() != null;
                if (started) {
                    // to cancel
                    task.cancel();
                } else {
                    // to start
                    startTask(task, adapter1,
                            (TextView) adapter1.getViewByPosition(position, R.id.offline_data_update),
                            (TextView) adapter1.getViewByPosition(position, R.id.offline_data_version_code),
                            (TextView) adapter1.getViewByPosition(position, R.id.offline_data_time),
                            (ProgressBar) adapter1.getViewByPosition(position, R.id.offline_data_progress),
                            (TextView) adapter1.getViewByPosition(position, R.id.offline_data_progress_number),
                            (TextView) adapter1.getViewByPosition(position, R.id.offline_data_last_time));
                    // mark
                    task.setTag(urlList.get(position).getKey());
                }
            }
        });

        downLoadInit();
        offlineDataRv.setItemViewCacheSize(urlList.size());
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_off_line_data;
    }

    @Override
    protected void findView() {
        offlineDataRv = findViewById(R.id.offline_data_rv);
        offlineDataRefreshLayout = findViewById(R.id.offline_data_refreshLayout);
    }

    @Override
    protected String getTitleText() {
        return "离线数据下载";
    }

    /**
     * 下载初始化
     */
    private void downLoadInit() {
        // TODO
        urlList = new ArrayList<>();
        taskList = new ArrayList<>();

        QueueDownLoadBean bean;
        String url = "http://dldir1.qq.com/weixin/android/weixin6516android1120.apk";
        bean = new QueueDownLoadBean(url, "1. WeChat");
        urlList.add(bean);
        taskList.add(TaskUtil.build(url, "weixin6516android1120.apk"));

        url = "https://cdn.llscdn.com/yy/files/tkzpx40x-lls-LLS-5.7-785-20171108-111118.apk";
        bean = new QueueDownLoadBean(url, "2. LiuLiShuo");
        urlList.add(bean);
        taskList.add(TaskUtil.build(url, "LiuLiShuo.apk"));

        url = "https://t.alipayobjects.com/L1/71/100/and/alipay_wap_main.apk";
        bean = new QueueDownLoadBean(url, "3. Alipay");
        urlList.add(bean);
        taskList.add(TaskUtil.build(url, "Alipay.apk"));

        url = "https://dldir1.qq.com/qqfile/QQforMac/QQ_V6.2.0.dmg";
        bean = new QueueDownLoadBean(url, "4. QQ for Mac");
        urlList.add(bean);
        taskList.add(TaskUtil.build(url, "QQ_V6.apk"));

        final String zhiHuApkHome = "https://zhstatic.zhihu.com/pkg/store/zhihu";
        url = zhiHuApkHome + "/futureve-mobile-zhihu-release-5.8.2(596).apk";
        bean = new QueueDownLoadBean(url, "5. ZhiHu");
        urlList.add(bean);
        taskList.add(TaskUtil.build(url, "ZhiHu.apk"));

        url = "http://d1.music.126.net/dmusic/CloudMusic_official_4.3.2.468990.apk";
        bean = new QueueDownLoadBean(url, "6. NetEaseMusic");
        urlList.add(bean);
        taskList.add(TaskUtil.build(url, "NetEaseMusic.apk"));

        url = "http://d1.music.126.net/dmusic/NeteaseMusic_1.5.9_622_officialsite.dmg";
        bean = new QueueDownLoadBean(url, "7. NetEaseMusic for Mac");
        urlList.add(bean);
        taskList.add(TaskUtil.build(url, "NeteaseMusic_1.apk"));

        url = "http://dldir1.qq.com/weixin/Windows/WeChatSetup.exe";
        bean = new QueueDownLoadBean(url, "8. WeChat for Windows");
        urlList.add(bean);
        taskList.add(TaskUtil.build(url, "WeChatSetup.apk"));

        url = "https://dldir1.qq.com/foxmail/work_weixin/wxwork_android_2.4.5.5571_100001.apk";
        bean = new QueueDownLoadBean(url, "9. WeChat Work");
        urlList.add(bean);
        taskList.add(TaskUtil.build(url, "wxwork_android_2.apk"));

        url = "https://dldir1.qq.com/foxmail/work_weixin/WXWork_2.4.5.213.dmg";
        bean = new QueueDownLoadBean(url, "10. WeChat Work for Mac");
        urlList.add(bean);
        taskList.add(TaskUtil.build(url, "WXWork_2.apk"));

    }

    @Override
    public void initListener() {
        findViewById(R.id.offline_data_check_btn).setOnClickListener(this::setOnClick);
    }

    /**
     * 设置监听
     */
    public void setOnClick(View view) {
        // 检查是否有更新
    }

    private void startTask(DownloadTask task,
                           final BaseQuickAdapter adapter,
                           final TextView statusTv,
                           final TextView versionCode,
                           final TextView time,
                           final ProgressBar progressBar,
                           final TextView progressBarNumber,
                           final TextView lastTime) {

        task.enqueue(new DownloadListener4WithSpeed() {
            private long totalLength;
            private String readableTotalLength;

            @Override
            public void taskStart(@NonNull DownloadTask task) {
                statusTv.setText("准备中...");
                progressBarNumber.setText("0%");
                progressBar.setProgressDrawable(ContextCompat.getDrawable(OffLineDataActivity.this, R.drawable.stroke_bg_radius_progress_them));
                statusTv.setBackgroundResource(R.drawable.stroke_bg_radius_order_item_theme);
                TaskUtil.calcProgressToView(progressBar, 0, 100);
                time.setText("未知");
            }

            @Override
            public void infoReady(@NonNull DownloadTask task, @NonNull BreakpointInfo info,
                                  boolean fromBreakpoint,
                                  @NonNull Listener4SpeedAssistExtend.Listener4SpeedModel model) {
                statusTv.setText("准备中...");

                totalLength = info.getTotalLength();
                readableTotalLength = Util.humanReadableBytes(totalLength, true);
                TaskUtil.calcProgressToView(progressBar, info.getTotalOffset(), totalLength);
            }

            @Override
            public void connectStart(@NonNull DownloadTask task, int blockIndex,
                                     @NonNull Map<String, List<String>> requestHeaders) {
                final String status = "Connect Start " + blockIndex;
                statusTv.setText(status);
            }

            @Override
            public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode,
                                   @NonNull Map<String, List<String>> responseHeaders) {
                final String status = "Connect End " + blockIndex;
                statusTv.setText(status);
            }

            @Override
            public void progressBlock(@NonNull DownloadTask task, int blockIndex,
                                      long currentBlockOffset,
                                      @NonNull SpeedCalculator blockSpeed) {
            }

            @Override
            public void progress(@NonNull DownloadTask task, long currentOffset,
                                 @NonNull SpeedCalculator taskSpeed) {
                final String readableOffset = Util.humanReadableBytes(currentOffset, true);
                final String progressStatus = readableOffset + "/" + readableTotalLength;
                final String speed = taskSpeed.speed();
                final String progressStatusWithSpeed = progressStatus + "(" + speed + ")";
                final String percent = (int) (currentOffset * 100 / totalLength) + "%";
                progressBarNumber.setText(percent);
                statusTv.setText(System.currentTimeMillis() / 500 % 2 == 0 ? "更新中." :
                        (System.currentTimeMillis() / 500 % 3 == 0 ? "更新中.." : "更新中..."));
                time.setText(progressStatusWithSpeed);
                TaskUtil.calcProgressToView(progressBar, currentOffset, totalLength);
            }

            @Override
            public void blockEnd(@NonNull DownloadTask task, int blockIndex, BlockInfo info,
                                 @NonNull SpeedCalculator blockSpeed) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause,
                                @Nullable Exception realCause,
                                @NonNull SpeedCalculator taskSpeed) {
                final String statusWithSpeed = cause.toString() + " " + taskSpeed.averageSpeed();
                statusTv.setText(TaskUtil.getStatusText(cause));
                time.setText(statusWithSpeed);

                // mark
                task.setTag(null);
                if (cause == EndCause.COMPLETED) {
                    progressBar.setProgressDrawable(ContextCompat.getDrawable(OffLineDataActivity.this,
                            R.drawable.stroke_bg_radius_progress_green));
                    statusTv.setBackgroundResource(R.drawable.stroke_bg_radius_order_item_green_to);
                    progressBarNumber.setText("100%");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        for (DownloadTask task : taskList) {
            if (ObjectUtils.isNotEmpty(task)) {
                task.cancel();
            }
        }
        super.onDestroy();
    }
}
