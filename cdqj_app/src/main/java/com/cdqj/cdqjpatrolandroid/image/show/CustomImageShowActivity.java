package com.cdqj.cdqjpatrolandroid.image.show;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.comstomview.SimpleToolbar;
import com.previewlibrary.GPreviewActivity;


/**
 * 自定义的图片显示
 * @author lyf
 */
public class CustomImageShowActivity extends GPreviewActivity {

    SimpleToolbar customImageShowToolbar;

    @Override
    public int setContentLayout() {
        return R.layout.cdqj_patrol_activity_custom_image_show;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams windowLP1 = getWindow().getAttributes();
        windowLP1.dimAmount = 0.5f;
        windowLP1.width = ViewGroup.LayoutParams.MATCH_PARENT;
        windowLP1.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(windowLP1);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        customImageShowToolbar = findViewById(R.id.custom_image_show_toolbar);
        customImageShowToolbar.setRightTitleBgRes(R.color.colorPrimary);
        customImageShowToolbar.setLeftTitleText(" 返回");
        customImageShowToolbar.setLeftTitleClickListener(v -> transformOut());
        customImageShowToolbar.setVisibility(View.GONE);
    }
}
