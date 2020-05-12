package com.cdqj.cdqjpatrolandroid.view.ui.base;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.base.BaseFragment;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;

import java.util.Objects;

/**
 * Created by lyf on 2018/7/24 14:59
 *
 * @author lyf
 * desc：
 */
public class SimpleCardFragment extends BaseFragment {

    public static final String PATROL_MAIN_BOTTOM_BAR_ACTION = "patrol_main_bottom_bar_action";

    TextView title;

    private String mTitle;

    public static SimpleCardFragment getInstance(String title) {
        SimpleCardFragment sf = new SimpleCardFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void initView(View rootView) {
        title.setText(mTitle);
        Log.e("cdqj","SimpleCardFragment-->onCreateView:" + mTitle);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_fr_simple_card;
    }

    @Override
    protected void findView(View rootView) {
        title = rootView.findViewById(R.id.card_title_tv);
        title.setOnClickListener(view -> {
            // 是否是地图界面点击
            if ("Switch ViewPager 地图".equals(mTitle)) {
                Intent intent = new Intent();
                intent.setAction(PATROL_MAIN_BOTTOM_BAR_ACTION);
                Objects.requireNonNull(getActivity()).sendBroadcast(intent);
            }
        });
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void loadData() {

    }
}
