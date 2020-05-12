package com.cdqj.cdqjpatrolandroid.utils;

import android.animation.Animator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by lyf on 2018/8/21 18:18
 *
 * @author lyf
 * desc：
 */
public class AnimUtils {

    /**
     * 显示动画
     * @param technique 动画效果
     * @param time 时间
     * @param view 控件
     * @param flag 是否显示
     */
    public static void setAnima(Techniques technique, int time, final View view, final boolean flag) {
        YoYo.with(technique)
                .duration(time)
                .repeat(0)
                .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                .interpolate(new AccelerateDecelerateInterpolator())
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(flag ? View.VISIBLE : View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(view);
    }
}
