package com.cdqj.cdqjpatrolandroid.comstomview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * Created by lyf on 2018/11/9 16:04
 *
 * @author lyf
 * desc：
 */
public class MyFragmentHeader extends LinearLayout implements RefreshHeader {

    private ImageView imageView;
    private Animation operatingAnim;

    public MyFragmentHeader(Context context) {
        super(context);
        init(context);
    }

    public MyFragmentHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyFragmentHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyFragmentHeader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setGravity(Gravity.CENTER);
        imageView = new ImageView(context);
        imageView.setImageResource(R.mipmap.icon_progress_to);
        LayoutParams params = new LayoutParams(ConvertUtils.dp2px(30),ConvertUtils.dp2px(30));
        params.setMargins(0, ConvertUtils.dp2px(5),0, ConvertUtils.dp2px(5));
        imageView.setLayoutParams(params);
        operatingAnim = AnimationUtils.loadAnimation(context, R.anim.rotation_360);
        addView(imageView);
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public View getView() {
        setBackgroundResource(R.mipmap.bg);
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        imageView.clearAnimation();
        return 500;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:

                break;
            case Refreshing:
                imageView.startAnimation(operatingAnim);
                break;
            case ReleaseToRefresh:
                break;
            default:break;
        }
    }
}
