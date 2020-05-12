package com.cdqj.cdqjpatrolandroid.version;

import android.support.annotation.NonNull;

import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.UnifiedListenerManager;

/**
 * Created by lyf on 2018/11/16 10:54
 *
 * @author lyf
 * desc：
 */
public class GlobalTaskManager {

    private UnifiedListenerManager manager;

    private GlobalTaskManager() {
        manager = new UnifiedListenerManager();
    }

    private static class ClassHolder {
        private static final GlobalTaskManager INSTANCE = new GlobalTaskManager();
    }

    public static GlobalTaskManager getImpl() {
        return ClassHolder.INSTANCE;
    }

    public void addAutoRemoveListenersWhenTaskEnd(int id) {
        manager.addAutoRemoveListenersWhenTaskEnd(id);
    }

    public void attachListener(@NonNull DownloadTask task, @NonNull DownloadListener listener) {
        manager.attachListener(task, listener);
    }

    public void enqueueTask(@NonNull DownloadTask task,
                     @NonNull DownloadListener listener) {
        manager.enqueueTaskWithUnifiedListener(task, listener);
    }
}
