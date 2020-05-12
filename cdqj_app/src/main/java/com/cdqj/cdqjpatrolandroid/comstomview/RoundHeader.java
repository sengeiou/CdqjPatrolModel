package com.cdqj.cdqjpatrolandroid.comstomview;

import android.content.Context;
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

public class RoundHeader extends LinearLayout implements RefreshHeader {
    ImageView imageView;
    private Animation operatingAnim;

    public RoundHeader(Context context) {
        super(context);
        initView(context);
    }

    public RoundHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RoundHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context context) {
        setGravity(Gravity.CENTER);
        imageView = new ImageView(context);
        imageView.setImageResource(R.mipmap.patrol_logo);
        LayoutParams params = new LayoutParams(ConvertUtils.dp2px(30),ConvertUtils.dp2px(30));
        params.setMargins(0, ConvertUtils.dp2px(5),0, ConvertUtils.dp2px(5));
        imageView.setLayoutParams(params);
        operatingAnim = AnimationUtils.loadAnimation(context, R.anim.rotation_360);
        addView(imageView);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {

    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        imageView.clearAnimation();
        return 500;
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
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
