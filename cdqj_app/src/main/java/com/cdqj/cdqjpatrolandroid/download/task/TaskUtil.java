package com.cdqj.cdqjpatrolandroid.download.task;

import android.widget.ProgressBar;

import com.blankj.utilcode.util.FileUtils;
import com.cdqj.cdqjpatrolandroid.utils.BasicConstant;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.StatusUtil;
import com.liulishuo.okdownload.core.cause.EndCause;

import java.io.File;

/**
 * Created by lyf on 2018/8/24 12:10
 *
 * @author lyf
 * desc：
 */
public class TaskUtil {

    public static DownloadTask build(String url, String name) {
        File file = FileUtils.getFileByPath(BasicConstant.APP_PATH);
        return new DownloadTask.Builder(url, file)
                .setFilename(name)
                // the minimal interval millisecond for callback progress
                .setMinIntervalMillisCallbackProcess(16)
                // ignore the same task has already completed in the past.
                .setPassIfAlreadyCompleted(false)
                .build();
    }


    public static void calcProgressToView(ProgressBar progressBar, long offset, long total) {
        final float percent = (float) offset / total;
        progressBar.setProgress((int) (percent * progressBar.getMax()));
    }


    public static  String getStatusText (EndCause cause) {
        return cause==EndCause.COMPLETED?"完成"
                :(cause==EndCause.ERROR?"失败"
                :cause==EndCause.CANCELED?"暂停"
                :(cause==EndCause.FILE_BUSY?"失败"
                :cause==EndCause.SAME_TASK_BUSY?"失败"
                :(cause==EndCause.PRE_ALLOCATE_FAILED?"失败":"失败")));
    }

    public static  String getStatusText (StatusUtil.Status cause) {
        return cause==StatusUtil.Status.COMPLETED?"完成"
                :(cause==StatusUtil.Status.PENDING?"等待中"
                :cause==StatusUtil.Status.RUNNING?"更新中"
                :(cause==StatusUtil.Status.IDLE?"失败"
                :cause==StatusUtil.Status.UNKNOWN?"失败":"失败"));
    }

    public static  String getStatusText (String status) {
        return status.equals(EndCause.COMPLETED.toString())?"完成"
                :(status.equals(EndCause.ERROR.toString())?"失败"
                :status.equals(EndCause.CANCELED.toString())?"暂停"
                :(status.equals(EndCause.FILE_BUSY.toString())?"失败"
                :status.equals(EndCause.SAME_TASK_BUSY.toString())?"失败"
                :status.equals(EndCause.PRE_ALLOCATE_FAILED.toString())?"失败":"失败"));
    }

}
