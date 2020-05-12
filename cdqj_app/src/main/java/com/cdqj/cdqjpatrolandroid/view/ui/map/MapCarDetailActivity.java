package com.cdqj.cdqjpatrolandroid.view.ui.map;

import com.blankj.utilcode.util.ObjectUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.bean.CarBean;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;

/**
 * 地图 车辆详情界面
 * @author lyf
 */
public class MapCarDetailActivity extends BaseActivity {

    private CarBean carBean;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_map_car_detail;
    }

    @Override
    protected void findView() {

    }

    @Override
    protected String getTitleText() {
        return "车辆详情";
    }

    @Override
    public void initView() {
        carBean = getIntent().getParcelableExtra("CarBean");
        if (ObjectUtils.isEmpty(carBean)) {
            ToastBuilder.showShortWarning("车辆信息获取失败");
            return;
        }

    }
}
