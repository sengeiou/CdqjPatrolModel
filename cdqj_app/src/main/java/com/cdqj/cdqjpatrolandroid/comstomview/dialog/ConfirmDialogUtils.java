package com.cdqj.cdqjpatrolandroid.comstomview.dialog;

import android.content.Context;

import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmSelectDialog;

/**
 * 版权：成都千嘉科技公司所有
 *
 * @author lyf
 * 创建日期： 2020/3/4
 * 创建时间： 17:12
 * 描述：弹窗工具类
 */
public class ConfirmDialogUtils {

    /**
     * 基本提示框
     * @param context 上下文
     * @param msg 提示文本
     */
    public static void showDialogDef(Context context, String msg) {
        new ConfirmSelectDialog(context).setNo(false).setContentStr(msg).show();
    }
}
