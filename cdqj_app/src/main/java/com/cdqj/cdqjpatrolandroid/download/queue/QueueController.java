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

package com.cdqj.cdqjpatrolandroid.download.queue;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.cdqj.cdqjpatrolandroid.utils.BasicConstant;
import com.liulishuo.okdownload.DownloadContext;
import com.liulishuo.okdownload.DownloadContextListener;
import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 队列下载控制
 */
public class QueueController {

    private static final String TAG = "QueueController";
    /**
     * 队列任务
     */
    private List<DownloadTask> taskList = new ArrayList<>();
    /**
     * 下载执行对象
     */
    private DownloadContext context;
    /**
     * 回调监听
     */
    private DownloadListener downloadListener;

    private DownloadContext.Builder builder;

    private File queueDir;

    /**
     * 任务初始化
     * @param urlList 任务集合
     * @param listener 监听
     */
    public void initTasks(List<QueueDownLoadBean> urlList, DownloadContextListener listener, DownloadListener downloadListener) {
        final DownloadContext.QueueSet set = new DownloadContext.QueueSet();
        final File parentFile = new File(SDCardUtils.getSDCardPathByEnvironment(), BasicConstant.APP_DIR);
        this.queueDir = parentFile;
        this.downloadListener = downloadListener;

        set.setParentPathFile(parentFile);
        set.setMinIntervalMillisCallbackProcess(200);

        builder = set.commit();

        DownloadTask boundTask;
        for (QueueDownLoadBean bean: urlList) {
            boundTask =  builder.bind(bean.getUrl());
            TagUtil.saveTaskName(boundTask, bean.getKey());
        }

        builder.setListener(listener);

        this.context = builder.build();
        this.taskList = Arrays.asList(this.context.getTasks());
    }

    /**
     * 添加新的任务
     * @param bean 任务实体类
     */
    public void addTask(QueueDownLoadBean bean) {
        DownloadTask boundTask =  builder.bind(bean.getUrl());
        TagUtil.saveTaskName(boundTask, bean.getKey());
        this.context = context.toBuilder()
                // .setListener(listener)
                .bindSetTask(boundTask)
                .build();
        this.taskList = Arrays.asList(this.context.getTasks());
    }

    /**
     * 删除所有下载的任务
     */
    public void deleteFiles() {
        if (queueDir != null) {
            String[] children = queueDir.list();
            if (children != null) {
                for (String child : children) {
                    if (!new File(queueDir, child).delete()) {
                        LogUtils.aTag(TAG, "delete " + child + " failed!");
                    }
                }
            }

            if (!queueDir.delete()) {
                LogUtils.aTag(TAG, "delete " + queueDir + " failed!");
            }
        }

        for (DownloadTask task : taskList) {
            TagUtil.clearProceedTask(task);
        }
    }


    /**
     * 设置任务下载优先级(新的的任务)
     */
    public void setPriority(DownloadTask task, int priority) {
        final DownloadTask newTask = task.toBuilder().setPriority(priority).build();
        this.context = context.toBuilder()
                .bindSetTask(newTask)
                .build();
        newTask.setTags(task);
        TagUtil.savePriority(newTask, priority);
        this.taskList = Arrays.asList(this.context.getTasks());
    }

    /**
     * 开始所有任务下载
     */
    public void start(boolean isSerial) {
        this.context.start(downloadListener, isSerial);
    }

    /**
     * 停止所有下载的任务
     */
    public void stop() {
        if (this.context.isStarted()) {
            this.context.stop();
        }
    }

    /**
     * 当前执行的任务数量
     */
    int size() {
        return taskList.size();
    }

    public DownloadContext getContext() {
        return context;
    }
}