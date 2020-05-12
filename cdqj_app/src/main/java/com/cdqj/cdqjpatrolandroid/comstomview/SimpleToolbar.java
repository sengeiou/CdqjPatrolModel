package com.cdqj.cdqjpatrolandroid.comstomview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.R;

import org.jetbrains.annotations.Nullable;

/**
 * Created by lyf on 2017/6/15.
 * 自定义toolbar
 * 添加返回this
 */
public class SimpleToolbar extends Toolbar {
    Context context;
    /**
     * 左侧Title
     */
    private TextView mTxtLeftTitle;
    /**
     * 中间Title
     */
    private TextView mTxtMiddleTitle;
    /**
     * 右侧Title
     */
    private TextView mTxtRightTitle;

    public TextView getmTxtLeftTitle() {
        return mTxtLeftTitle;
    }

    public TextView getmTxtMiddleTitle() {
        return mTxtMiddleTitle;
    }

    public TextView getmTxtRightTitle() {
        return mTxtRightTitle;
    }

    public SimpleToolbar(Context context) {
        super(context);
        this.context = context;

        initViwe(context, null);
    }

    public SimpleToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViwe(context, attrs);

    }

    public SimpleToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViwe(context, attrs);
    }

    protected void initViwe(Context context, AttributeSet attrs) {

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.simple_toolbar);
        String title = mTypedArray.getString(R.styleable.simple_toolbar_simple_title);
        // 加载布局
        LayoutInflater.from(getContext()).inflate(R.layout.cdqj_patrol_simple_toolbar, this);
        mTxtLeftTitle = findViewById(R.id.simple_title_back);
        mTxtMiddleTitle = findViewById(R.id.simple_title_name);
        mTxtRightTitle = findViewById(R.id.simple_title_rightTv);
        if (!StringUtils.isEmpty(title)) {
            mTxtMiddleTitle.setText(title);
        }
        if (context != null && context instanceof Activity) {
            final Activity activity = (Activity) context;
            mTxtLeftTitle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.finish();
                }
            });
        }
        mTypedArray.recycle();
    }

    /**
     * 设置中间title的内容
     *
     * @param text title的内容
     */
    public SimpleToolbar setMainTitle(String text) {
        this.setTitle(" ");
        mTxtMiddleTitle.setVisibility(View.VISIBLE);
        mTxtMiddleTitle.setText(text);
        return this;
    }

    /**
     * 设置title中间背景色
     *
     * @param color title中间颜色
     */
    public SimpleToolbar setMainTitleBgRes(int color) {
        mTxtMiddleTitle.setBackgroundResource(color);
        return this;
    }

    /**
     * 设置中间title的内容文字的颜色
     *
     * @param color title的内容文字的颜色
     */
    public SimpleToolbar setMainTitleColor(int color) {
        mTxtMiddleTitle.setTextColor(color);
        return this;
    }

    /**
     * 设置title左边文字
     *
     * @param text title左边文字
     */
    public SimpleToolbar setLeftTitleText(String text) {
        mTxtLeftTitle.setVisibility(View.VISIBLE);
        mTxtLeftTitle.setText(text);
        return this;
    }

    /**
     * 设置title左边文字颜色
     *
     * @param color title左边文字颜色
     */
    public SimpleToolbar setLeftTitleColor(int color) {
        mTxtLeftTitle.setTextColor(color);
        return this;
    }

    /**
     * 设置title左边图标
     *
     * @param res title左边图标
     */
    public SimpleToolbar setLeftTitleDrawable(int res) {
        Drawable dwLeft = ContextCompat.getDrawable(getContext(), res);
        dwLeft.setBounds(0, 0, dwLeft.getMinimumWidth(), dwLeft.getMinimumHeight());
        mTxtLeftTitle.setCompoundDrawables(dwLeft, null, null, null);
        return this;
    }

    /**
     * 设置title左边点击事件
     *
     * @param onClickListener title左边点击事件
     */
    public SimpleToolbar setLeftTitleClickListener(OnClickListener onClickListener) {
        mTxtLeftTitle.setOnClickListener(onClickListener);
        return this;
    }

    /**
     * 设置title左边背景色
     *
     * @param color title左边颜色
     */
    public SimpleToolbar setLeftTitleBgRes(int color) {
        mTxtLeftTitle.setBackgroundResource(color);
        return this;
    }

    /**
     * 设置title右边文字
     *
     * @param text title右边文字
     */
    public SimpleToolbar setRightTitleText(String text) {
        mTxtRightTitle.setVisibility(View.VISIBLE);
        mTxtRightTitle.setText(text);
        return this;
    }

    /**
     * 设置title右边文字颜色
     *
     * @param color title右边文字颜色
     */
    public SimpleToolbar setRightTitleColor(int color) {
        mTxtRightTitle.setTextColor(color);
        return this;
    }

    /**
     * 设置title右边图标
     *
     * @param res title右边图标
     */
    public SimpleToolbar setRightTitleDrawable(int res) {
        Drawable dwRight = ContextCompat.getDrawable(getContext(), res);
        dwRight.setBounds(0, 0, dwRight.getMinimumWidth(), dwRight.getMinimumHeight());
        mTxtRightTitle.setCompoundDrawables(null, null, dwRight, null);
        return this;
    }

    /**
     * 设置title右边背景色
     *
     * @param color title右边颜色
     */
    public SimpleToolbar setRightTitleBgRes(int color) {
        mTxtRightTitle.setBackgroundResource(color);
        return this;
    }

    /**
     * 设置title右边点击事件
     *
     * @param onClickListener title右边点击事件
     */
    public SimpleToolbar setRightTitleClickListener(OnClickListener onClickListener) {
        mTxtRightTitle.setOnClickListener(onClickListener);
        return this;
    }
}
