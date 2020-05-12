package com.cdqj.cdqjpatrolandroid.comstomview.loadingdialog;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by lyf on 2017/6/19.
 * 等待加载框
 */
public class LoadingHelper {

    /**
     * 显示进度等待框
     *
     * @param context context
     */
    public static CustomProgressDialog startProgressDialog(Context context,
                                                           CustomProgressDialog progressDialog) {
        return startProgressDialog(context, progressDialog, null);
    }
    /**
     * 显示进度等待框
     *
     * @param context context
     * @param message message 提示
     */
    public static CustomProgressDialog startProgressDialog(Context context, CustomProgressDialog progressDialog, String message) {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(context);
        }
        if (!TextUtils.isEmpty(message)) {
            progressDialog.setMessage(message);
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        // 设置点击屏幕Dialog不消失
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    /**
     * 隐藏进度等待框
     */
    public static CustomProgressDialog stopProgressDialog(CustomProgressDialog progressDialog) {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        return progressDialog;
    }

}
