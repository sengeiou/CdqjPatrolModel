package com.cdqj.cdqjpatrolandroid.view.ui.map;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.cdqj.cdqjpatrolandroid.comstomview.recyclerview.SpacesItemDecoration;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.MainAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.bean.MainGridBean;
import com.cdqj.cdqjpatrolandroid.view.ui.base.BaseMapUpdateActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 地图上报功能
 *
 * @author lyf
 */
public class MapUpdateActivity extends BaseActivity {

    RecyclerView mapUpdateRv;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_map_update;
    }

    @Override
    protected void findView() {
        mapUpdateRv = findViewById(R.id.map_update_rv);
    }

    @Override
    protected String getTitleText() {
        return "上报";
    }

    @Override
    public void initView() {
        // 初始化工具栏数据
        List<MainGridBean> updateType = new ArrayList<>();
        updateType.add(new MainGridBean(R.mipmap.icon_danger, "隐患", BaseMapUpdateActivity.class));
        updateType.add(new MainGridBean(R.mipmap.icon_site, "工地", BaseMapUpdateActivity.class));
        updateType.add(new MainGridBean(R.mipmap.icon_query_point, "巡检点", BaseMapUpdateActivity.class));
//        updateType.add(new MainGridBean(R.mipmap.icon_equipmenterror, "设备纠错", BaseMapUpdateActivity.class));

        mapUpdateRv.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(15)));
        mapUpdateRv.setLayoutManager(new GridLayoutManager(this, 3));

        MainAdapter adapter = new MainAdapter(R.layout.cdqj_patrol_home_grid_item, updateType);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.bindToRecyclerView(mapUpdateRv);
        adapter.setOnItemClickListener((adapter1, view, position) -> ActivityUtils.startActivity(new Intent(this, updateType.get(position).getActivity()).putExtra("flag", position)));
    }
}
