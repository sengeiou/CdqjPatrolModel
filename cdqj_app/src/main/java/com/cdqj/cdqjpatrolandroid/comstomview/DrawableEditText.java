package com.cdqj.cdqjpatrolandroid.comstomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.blankj.utilcode.util.ObjectUtils;

/**
 * Created by lyf on 2018/10/30 10:56
 *
 * @author lyf
 * desc：
 */
public class DrawableEditText extends android.support.v7.widget.AppCompatEditText {

    public DrawableEditText(Context context) {
        super(context);
    }

    public DrawableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //得到Drawable集合  分别对应 左上右下
        Drawable[] drawables = getCompoundDrawables();
        if (ObjectUtils.isNotEmpty(drawables)) {
            //获取左边图片
            Drawable drawableLeft = drawables[1];
            if (drawableLeft != null) {
                //获取文字占用长宽
                int textWidth = (int) getPaint().measureText(getText().toString());
                int textHeight = (int) getPaint().getTextSize();
                //获取图片实际长宽
                int drawableWidth = drawableLeft.getIntrinsicWidth();
                int drawableHeight = drawableLeft.getIntrinsicHeight();
                //setBounds修改Drawable在View所占的位置和大小,对应参数同样的 左上右下()
                drawableLeft.setBounds(((textWidth - getWidth()) / 2) - textWidth, (textHeight - drawableHeight) / 2, ((textWidth - getWidth()) / 2) + drawableWidth - textWidth, (textHeight + drawableHeight) / 2);
            }
        }
        super.onDraw(canvas);
    }
}