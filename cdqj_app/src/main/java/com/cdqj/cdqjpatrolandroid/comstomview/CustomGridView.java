package com.cdqj.cdqjpatrolandroid.comstomview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 解决在ScrollView里布局GridView时，GridView以单行显示出来的问题
 * Created by lyf on 2017/9/28.
 */

public class CustomGridView extends GridView {

    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomGridView(Context context) {
        super(context);
    }

    public CustomGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
