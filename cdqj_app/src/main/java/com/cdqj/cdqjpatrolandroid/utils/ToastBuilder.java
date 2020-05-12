package com.cdqj.cdqjpatrolandroid.utils;

import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.base.App;

/**
 * Created by lyf on 2018/9/7 15:03
 *
 * @author lyf
 * desc：
 */
@SuppressLint({"InflateParams","ResourceType"})
public class ToastBuilder {

    /**
     * 警告提示
     * @param msg 消息
     */
    public static void showShortWarning(String msg) {
        showShortCustom(msg, 3);
    }

    /**
     * 警告提示
     * @param msg 消息
     */
    public static void showShortWarning(int msg) {
        showShortCustom(msg, 3);
    }

    /**
     * 警告提示
     * @param msg 消息
     */
    public static void showLongWarning(String msg) {
        showLongCustom(msg, 3);
    }

    /**
     * 警告提示
     * @param msg 消息
     */
    public static void showLongWarning(int msg) {
        showLongCustom(msg, 3);
    }

    /**
     * 错误提示
     * @param msg 消息
     */
    public static void showShortError(String msg) {
        showShortCustom(msg, 2);
    }

    /**
     * 错误提示
     * @param msg 消息
     */
    public static void showShortError(int msg) {
        showShortCustom(msg, 2);
    }

    /**
     * 错误提示
     * @param msg 消息
     */
    public static void showLongError(String msg) {
        showLongCustom(msg, 2);
    }

    /**
     * 错误提示
     * @param msg 消息
     */
    public static void showLongError(int msg) {
        showLongCustom(msg, 2);
    }

    /**
     * 默认提示（正确）
     * @param msg 消息
     */
    public static void showLong(String msg) {
        showLongCustom(msg, 1);
    }

    /**
     * 默认提示（正确）
     * @param msg 消息
     */
    public static void showLong(int msg) {
        showLongCustom(msg, 1);
    }

    /**
     * 默认提示（正确）
     * @param msg 消息
     */
    public static void showShort(String msg) {
        showShortCustom(msg, 1);
    }

    /**
     * 默认提示（正确）
     * @param msg 消息
     */
    public static void showShort(int msg) {
        showShortCustom(msg, 1);
    }

    /**
     * 提示关闭
     */
    public static void cancel() {
        ToastToUtils.cancel();
    }

    /**
     *  @param msg msg
     * @param flag 1正常 2错误 3警告
     */
    private static View setCustom(int msg, int flag) {
        View view = LayoutInflater.from(App.getInstance()).inflate(R.layout.cdqj_patrol_toast_builder_layout, null);
        TextView msgTv = view.findViewById(R.id.toast_msg);
        ImageView img = view.findViewById(R.id.toast_img);
        msgTv.setText(msg);
        msgTv.setTextColor(ContextCompat.getColor(App.getInstance(),flag==1?R.color.theme_one:
                flag==2?R.color.red: R.color.yellow));
        img.setImageResource(flag==1?R.mipmap.icon_toast_success:flag==2?R.mipmap.icon_toast_error:
                R.mipmap.icon_toast_warning);
        // ToastToUtils.setMsgTextSize(14);
        // ToastToUtils.setBgColor(ContextCompat.getColor(App.getInstance(),R.color.theme_one));
        // ToastToUtils.setGravity(Gravity.CENTER|Gravity.TOP,0,0);
        return view;
    }

    /**
     *
     * @param msg msg
     * @param flag 1正常 2错误 3警告
     */
    private static View setCustom(String msg, int flag) {
        View view = LayoutInflater.from(App.getInstance()).inflate(R.layout.cdqj_patrol_toast_builder_layout, null);
        TextView msgTv = view.findViewById(R.id.toast_msg);
        ImageView img = view.findViewById(R.id.toast_img);
        msgTv.setText(msg);
        msgTv.setTextColor(ContextCompat.getColor(App.getInstance(),flag==1?R.color.white:
                flag==2?R.color.red: R.color.yellow));
        img.setImageResource(flag==1?R.mipmap.icon_toast_success:flag==2?R.mipmap.icon_toast_error:
                R.mipmap.icon_toast_warning);
        return view;
    }

    /**
     *
     * @param msg msg
     * @param flag 1正常 2错误 3警告
     */
    private static void showLongCustom(int msg, int flag) {
        ToastToUtils.showCustomLong(setCustom(msg, flag));
    }

    private static void showLongCustom(String msg, int flag) {
        ToastToUtils.showCustomLong(setCustom(msg, flag));
    }

    private static void showShortCustom(int msg, int flag) {
        ToastToUtils.showCustomShort(setCustom(msg, flag));
    }

    private static void showShortCustom(String msg, int flag) {
        ToastToUtils.showCustomShort(setCustom(msg, flag));
    }
}
