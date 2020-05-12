package com.cdqj.cdqjpatrolandroid.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;

/**
 * Created by lyf on 2018/7/30 15:56
 *
 * @author lyf
 * desc：
 */
public class DrawableUtils {

    /**
     * 设置图片
     *
     * @param view      控件
     * @param id        图片资源
     * @param direction 方向 1 left, 2 top, 3 right, 4 bottom
     * @param padding   间距
     */
    public static void setDrawable(Context context, TextView view, int id, int direction, int padding) {
        Drawable drawable = null;
        if (id != 0) {
            drawable = ContextCompat.getDrawable(context, id);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        view.setCompoundDrawables(1 == direction ? drawable : null, 2 == direction ? drawable : null,
                3 == direction ? drawable : null, 4 == direction ? drawable : null);
        view.setCompoundDrawablePadding(ConvertUtils.dp2px(padding));
    }

    /**
     * 设置图片
     *
     * @param view 控件
     * @param id   图片资源
     */
    public static void setDrawable(Context context, TextView view, int id) {
        setDrawable(context, view, id, 1, 0);
    }

    /**
     * 设置图片
     *
     * @param view 控件
     * @param leftId   图片资源
     * @param rightId   图片资源
     */
    public static void setDrawable(Context context, TextView view, int leftId, int rightId) {
        Drawable drawableLeft = null;
        Drawable drawableRight = null;
        if (leftId != 0) {
            drawableLeft = ContextCompat.getDrawable(context, leftId);
            drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
        }
        if (rightId != 0) {
            drawableRight = ContextCompat.getDrawable(context, rightId);
            drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
        }
        view.setCompoundDrawables(drawableLeft, null, drawableRight,null);
        view.setCompoundDrawablePadding(ConvertUtils.dp2px(0));
    }
}
