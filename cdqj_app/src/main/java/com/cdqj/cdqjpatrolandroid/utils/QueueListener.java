/*
 * Copyright (c) 2017 LingoChamp Inc.
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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.cdqj.cdqjpatrolandroid.download.ProgressUtil;
import com.cdqj.cdqjpatrolandroid.download.queue.TagUtil;
import com.cdqj.cdqjpatrolandroid.download.task.TaskUtil;
import com.cdqj.cdqjpatrolandroid.base.App;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.SpeedCalculator;
import com.liulishuo.okdownload.StatusUtil;
import com.liulishuo.okdownload.core.Util;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.cdqj.cdqjpatrolandroid.R;
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed;
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend;

import java.util.List;
import java.util.Map;

public class QueueListener extends DownloadListener4WithSpeed {

    private static final String TAG = "QueueListener";

    private SparseArray<BaseViewHolder> holderMap = new SparseArray<>();

    public void bind(DownloadTask task, BaseViewHolder holder) {
        LogUtils.aTag(TAG, "bind " + task.getId() + " with " + holder);
        // replace.
        final int size = holderMap.size();
        for (int i = 0; i < size; i++) {
            if (holderMap.valueAt(i) == holder) {
                holderMap.removeAt(i);
                break;
            }
        }
        holderMap.put(task.getId(), holder);
    }

    public void resetInfo(DownloadTask task, BaseViewHolder holder) {
        // task name
        final String taskName = TagUtil.getTaskName(task);
        TextView update = holder.getView(R.id.offline_data_update);
        TextView code = holder.getView(R.id.offline_data_version_code);
        TextView time = holder.getView(R.id.offline_data_time);
        ProgressBar progress = holder.getView(R.id.offline_data_progress);
        TextView number = holder.getView(R.id.offline_data_progress_number);
        TextView lastTiem = holder.getView(R.id.offline_data_last_time);

        // process references
        final String status = TagUtil.getStatus(task);
        if (status != null) {
            //   started
            update.setText(TaskUtil.getStatusText(status));
            if (status.equals(EndCause.COMPLETED.toString())) {
                update.setText("完成");
                time.setText("下载完成");
                task.setTag(null);
                progress.setProgress(progress.getMax());
                progress.setProgressDrawable(ContextCompat.getDrawable(App.getInstance(),
                        R.drawable.stroke_bg_radius_progress_green));
            } else {
                progress.setProgressDrawable(ContextCompat.getDrawable(App.getInstance(),
                        R.drawable.stroke_bg_radius_progress_them));
                final long total = TagUtil.getTotal(task);
                if (total == 0) {
                    progress.setProgress(0);
                } else {
                    ProgressUtil.calcProgressToViewAndMark(progress,
                            TagUtil.getOffset(task), total, false);
                }
            }
        } else {
            // non-started
            final StatusUtil.Status statusOnStore = StatusUtil.getStatus(task);
            TagUtil.saveStatus(task, statusOnStore.toString());
            if (statusOnStore == StatusUtil.Status.COMPLETED) {
                update.setText(EndCause.COMPLETED.toString());
                time.setText("下载完成");
                progress.setProgress(progress.getMax());
                progress.setProgressDrawable(ContextCompat.getDrawable(App.getInstance(),
                        R.drawable.stroke_bg_radius_progress_green));
            } else {
                progress.setProgressDrawable(ContextCompat.getDrawable(App.getInstance(),
                        R.drawable.stroke_bg_radius_progress_them));
                switch (statusOnStore) {
                    case IDLE:
                        update.setText(TaskUtil.getStatusText(statusOnStore));
                        break;
                    case PENDING:
                        update.setText(TaskUtil.getStatusText(statusOnStore));
                        break;
                    case RUNNING:
                        update.setText(System.currentTimeMillis() / 500 % 2 == 0 ? "更新中." :
                                (System.currentTimeMillis() / 500 % 3 == 0 ? "更新中.." : "更新中..."));
                        break;
                    default:
                        update.setText(TaskUtil.getStatusText(statusOnStore));
                }

                if (statusOnStore == StatusUtil.Status.UNKNOWN) {
                    progress.setProgress(0);
                } else {
                    final BreakpointInfo info = StatusUtil.getCurrentInfo(task);
                    if (info != null) {
                        TagUtil.saveTotal(task, info.getTotalLength());
                        TagUtil.saveOffset(task, info.getTotalOffset());
                        ProgressUtil.calcProgressToViewAndMark(progress,
                                info.getTotalOffset(), info.getTotalLength(), false);
                    } else {
                        progress.setProgress(0);
                    }
                }

            }
        }
    }

    public void clearBoundHolder() {
        holderMap.clear();
    }

    @Override
    public void taskStart(@NonNull DownloadTask task) {
        final String status = "taskStart";
        TagUtil.saveStatus(task, status);

        final BaseViewHolder holder = holderMap.get(task.getId());
        if (holder == null) {
            return;
        }
        TextView update = holder.getView(R.id.offline_data_update);
        TextView code = holder.getView(R.id.offline_data_version_code);
        TextView time = holder.getView(R.id.offline_data_time);
        ProgressBar progress = holder.getView(R.id.offline_data_progress);
        TextView number = holder.getView(R.id.offline_data_progress_number);
        TextView lastTiem = holder.getView(R.id.offline_data_last_time);
        update.setText("准备中...");
        number.setText("0%");
        progress.setProgressDrawable(ContextCompat.getDrawable(App.getInstance(), R.drawable.stroke_bg_radius_progress_them));
        TaskUtil.calcProgressToView(progress,0,100);
        time.setText("未知");
    }

    @Override
    public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {
        final String status = "connectStart";
        TagUtil.saveStatus(task, status);

        final BaseViewHolder holder = holderMap.get(task.getId());
        if (holder == null) {
            return;
        }
        TextView update = holder.getView(R.id.offline_data_update);
        TextView code = holder.getView(R.id.offline_data_version_code);
        TextView time = holder.getView(R.id.offline_data_time);
        ProgressBar progress = holder.getView(R.id.offline_data_progress);
        TextView number = holder.getView(R.id.offline_data_progress_number);
        TextView lastTiem = holder.getView(R.id.offline_data_last_time);

        String text = "Connect Start " + blockIndex;
        update.setText(text);
    }

    @Override
    public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {
        final String status = "connectEnd";
        TagUtil.saveStatus(task, status);

        final BaseViewHolder holder = holderMap.get(task.getId());
        if (holder == null) {
            return;
        }
        TextView update = holder.getView(R.id.offline_data_update);
        TextView code = holder.getView(R.id.offline_data_version_code);
        TextView time = holder.getView(R.id.offline_data_time);
        ProgressBar progress = holder.getView(R.id.offline_data_progress);
        TextView number = holder.getView(R.id.offline_data_progress_number);
        TextView lastTiem = holder.getView(R.id.offline_data_last_time);

        final String text = "Connect End " + blockIndex;
        update.setText(text);
    }

    @Override
    public void infoReady(@NonNull DownloadTask task, @NonNull BreakpointInfo info, boolean fromBreakpoint, @NonNull Listener4SpeedAssistExtend.Listener4SpeedModel model) {
        final String status = "infoReady";
        TagUtil.saveStatus(task, status);

        final BaseViewHolder holder = holderMap.get(task.getId());
        if (holder == null) {
            return;
        }
        TextView update = holder.getView(R.id.offline_data_update);
        TextView code = holder.getView(R.id.offline_data_version_code);
        TextView time = holder.getView(R.id.offline_data_time);
        ProgressBar progress = holder.getView(R.id.offline_data_progress);
        TextView number = holder.getView(R.id.offline_data_progress_number);
        TextView lastTiem = holder.getView(R.id.offline_data_last_time);
        update.setText("准备中...");

        long totalLength = info.getTotalLength();
        String readableTotalLength = Util.humanReadableBytes(totalLength, true);
        TaskUtil.calcProgressToView(progress, info.getTotalOffset(), totalLength);
        update.setTag(R.id.offline_data_update, totalLength);
        code.setTag(R.id.offline_data_version_code, readableTotalLength);
    }

    @Override
    public void progressBlock(@NonNull DownloadTask task, int blockIndex, long currentBlockOffset, @NonNull SpeedCalculator blockSpeed) {
        final String status = "progressBlock";
        TagUtil.saveStatus(task, status);

        final BaseViewHolder holder = holderMap.get(task.getId());
        if (holder == null) {
            return;
        }
        TextView update = holder.getView(R.id.offline_data_update);
        TextView code = holder.getView(R.id.offline_data_version_code);
        TextView time = holder.getView(R.id.offline_data_time);
        ProgressBar progress = holder.getView(R.id.offline_data_progress);
        TextView number = holder.getView(R.id.offline_data_progress_number);
        TextView lastTiem = holder.getView(R.id.offline_data_last_time);
    }

    @Override
    public void progress(@NonNull DownloadTask task, long currentOffset, @NonNull SpeedCalculator taskSpeed) {
        final String status = "progress";
        TagUtil.saveStatus(task, status);

        final BaseViewHolder holder = holderMap.get(task.getId());
        if (holder == null) {
            return;
        }

        TextView update = holder.getView(R.id.offline_data_update);
        TextView code = holder.getView(R.id.offline_data_version_code);
        TextView time = holder.getView(R.id.offline_data_time);
        ProgressBar progress = holder.getView(R.id.offline_data_progress);
        TextView number = holder.getView(R.id.offline_data_progress_number);
        TextView lastTiem = holder.getView(R.id.offline_data_last_time);

        Long totalLength = (Long) update.getTag(R.id.offline_data_update);
        String readableTotalLength = (String) code.getTag(R.id.offline_data_version_code);

        final String readableOffset = Util.humanReadableBytes(currentOffset, true);
        final String progressStatus = readableOffset + "/" + readableTotalLength;
        final String speed = taskSpeed.speed();
        final String progressStatusWithSpeed = progressStatus + "(" + speed + ")";
        // totalLength可能会为空
        final String percent = (int) (currentOffset * 100 / totalLength) + "%";
        number.setText(percent);
        update.setText(System.currentTimeMillis()/500%2==0?"更新中.":
                (System.currentTimeMillis()/500%3==0?"更新中..":"更新中..."));
        time.setText(progressStatusWithSpeed);
        TaskUtil.calcProgressToView(progress, currentOffset, totalLength);
    }

    @Override
    public void blockEnd(@NonNull DownloadTask task, int blockIndex, BlockInfo info, @NonNull SpeedCalculator blockSpeed) {
        final String status = "blockEnd";
        TagUtil.saveStatus(task, status);

        final BaseViewHolder holder = holderMap.get(task.getId());
        if (holder == null) {
            return;
        }
        TextView update = holder.getView(R.id.offline_data_update);
        TextView code = holder.getView(R.id.offline_data_version_code);
        TextView time = holder.getView(R.id.offline_data_time);
        ProgressBar progress = holder.getView(R.id.offline_data_progress);
        TextView number = holder.getView(R.id.offline_data_progress_number);
        TextView lastTiem = holder.getView(R.id.offline_data_last_time);
    }

    @Override
    public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull SpeedCalculator taskSpeed) {
        final String status = "taskEnd";
        TagUtil.saveStatus(task, status);

        final BaseViewHolder holder = holderMap.get(task.getId());
        if (holder == null) {
            return;
        }
        TextView update = holder.getView(R.id.offline_data_update);
        TextView code = holder.getView(R.id.offline_data_version_code);
        TextView time = holder.getView(R.id.offline_data_time);
        ProgressBar progress = holder.getView(R.id.offline_data_progress);
        TextView number = holder.getView(R.id.offline_data_progress_number);
        TextView lastTiem = holder.getView(R.id.offline_data_last_time);

        final String statusWithSpeed = cause.toString() + " " + taskSpeed.averageSpeed();
        update.setText(TaskUtil.getStatusText(cause));
        time.setText(statusWithSpeed);

        // mark
        task.setTag(null);
        if (cause == EndCause.COMPLETED) {
            progress.setProgressDrawable(ContextCompat.getDrawable(App.getInstance(),
                    R.drawable.stroke_bg_radius_progress_green));
            number.setText("100%");
        }
    }
}