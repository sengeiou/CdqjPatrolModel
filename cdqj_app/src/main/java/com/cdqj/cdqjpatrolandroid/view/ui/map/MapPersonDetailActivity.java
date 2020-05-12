package com.cdqj.cdqjpatrolandroid.view.ui.map;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.comstomview.recyclerview.SpacesItemDecoration;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.image.glide.GlideImgManager;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.MapPersonListAdapter;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.bean.PersonLogBean;
import com.cdqj.cdqjpatrolandroid.bean.UserLayerBean;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.http.BaseGetResponse;
import com.cdqj.cdqjpatrolandroid.presenter.MapPersonDetailPresenter;
import com.cdqj.cdqjpatrolandroid.utils.NavigationUtil;
import com.cdqj.cdqjpatrolandroid.utils.PatrolEnterPointActivity;
import com.cdqj.cdqjpatrolandroid.utils.PictureProcessingUtil;
import com.cdqj.cdqjpatrolandroid.utils.StringUrlUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IMapPersonDetailView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 地图人员弹窗详情
 *
 * @author lyf
 * @date 2018年10月19日 11:43:12
 */
public class MapPersonDetailActivity extends BaseActivity<MapPersonDetailPresenter> implements IMapPersonDetailView {

    TextView mapPersonDialogTitle;
    TextView mapPersonDialogLevel;
    TextView mapPersonDialogDistance;
    TextView mapPersonDialogAddress;
    TextView mapPersonDialogUpdate;
    ImageView mapPersonDialogImg;
    BaseRecyclerView mapPersonDetailRv;
    TextView mapBottomBarReport;

    /**
     * 人员内容对象
     */
    private UserLayerBean personBean;
    private MapPersonListAdapter rvAdapter;

    private int id;

    /**
     * 人员上报及上下班操作列表（默认展示当天）
     */
    private List<PersonLogBean> beans = new ArrayList<>();

    /**
     * 导航点
     */
    private double navX = 30.572262, navY = 104.066513;

    /**
     * 界面初始化
     */
    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public void initView() {
        mStateView = StateView.inject(mapPersonDetailRv);
        mStateView.setOnRetryClickListener(() -> {
            page = 1;
            mPresenter.getPersonReports(String.valueOf(id), true);
        });
        mapBottomBarReport.setVisibility(View.GONE);
        assert titleToolbar != null;
        titleToolbar.setRightTitleText("列表");
        titleToolbar.setRightTitleClickListener(v -> {
            // 跳转到人员列表界面
            PatrolEnterPointActivity.gotoPersonListActivity(this);
        });

        personBean = getIntent().getParcelableExtra("UserLayerBean");

        mapPersonDetailRv.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(10)));
        mapPersonDetailRv.setLayoutManager(new LinearLayoutManager(this));
        rvAdapter = new MapPersonListAdapter(R.layout.cdqj_patrol_map_person_list_item, beans, this);
        rvAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        rvAdapter.bindToRecyclerView(mapPersonDetailRv);

        if (ObjectUtils.isNotEmpty(personBean)) {
            // 隐患
            id = personBean.getSysUserid();

            navX = personBean.getLastReportLat();
            navY = personBean.getLastReportLon();
            mapPersonDialogTitle.setText(StringUtils.isTrimEmpty(personBean.getSysStaffName()) ?
                    getString(R.string.null_text) : personBean.getSysStaffName());
            mapPersonDialogAddress.setText(StringUtils.isTrimEmpty(personBean.getAddress()) ?
                    getString(R.string.null_text) : personBean.getAddress());
            mapPersonDialogLevel.setText(StringUtils.isTrimEmpty(personBean.getSysDeptname()) ?
                    getString(R.string.null_text) : personBean.getSysDeptname());
            mapPersonDialogUpdate.setText(personBean.getLastReportTime() + " 上报");
            GlideImgManager.loadRoundCornerImage(this, StringUrlUtil.transHttpUrlAndOnlyOne(personBean.getPhoto()), mapPersonDialogImg, 5);
            mapPersonDialogDistance.setText(StringUtils.isTrimEmpty(personBean.getGroupName()) ?
                    getString(R.string.null_text) : personBean.getGroupName());
            mPresenter.getPersonReports(String.valueOf(id), true);
        } else {
            ToastBuilder.showShortWarning("人员信息获取失败");
        }
    }

    @Override
    public void initRefresh() {
        mPresenter.getPersonReports(String.valueOf(id), false);
    }

    @Override
    protected MapPersonDetailPresenter createPresenter() {
        return new MapPersonDetailPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_map_person_detail;
    }

    @Override
    protected void findView() {
        mapPersonDialogTitle = findViewById(R.id.map_person_dialog_title);
        mapPersonDialogLevel = findViewById(R.id.map_person_dialog_level);
        mapPersonDialogDistance = findViewById(R.id.map_person_dialog_distance);
        mapPersonDialogAddress = findViewById(R.id.map_person_dialog_address);
        mapPersonDialogUpdate = findViewById(R.id.map_person_dialog_update);
        mapPersonDialogImg = findViewById(R.id.map_person_dialog_img);
        mapPersonDetailRv = findViewById(R.id.map_person_detail_rv);
        mapBottomBarReport = findViewById(R.id.map_bottom_bar_report);
    }

    @Override
    protected String getTitleText() {
        return "详情";
    }

    @Override
    public void initListener() {
        findViewById(R.id.map_bottom_bar_map).setOnClickListener(this::setClick);
        findViewById(R.id.map_bottom_bar_report).setOnClickListener(this::setClick);
        findViewById(R.id.map_bottom_bar_navigation).setOnClickListener(this::setClick);
        findViewById(R.id.map_person_dialog_see).setOnClickListener(this::setClick);
        findViewById(R.id.map_person_dialog_img).setOnClickListener(this::setClick);
    }

    public void setClick(View view) {
        int i = view.getId();
        if (i == R.id.map_bottom_bar_map) {
            this.finish();
        } else if (i == R.id.map_bottom_bar_report) {
        } else if (i == R.id.map_bottom_bar_navigation) {
            NavigationUtil.showNavWindow(navX, navY, this);
        } else if (i == R.id.map_person_dialog_see) {// 查看轨迹
            PatrolEnterPointActivity.gotoPersonTrajectoryMapActivity(this, id);
        } else if (i == R.id.map_person_dialog_img) {
            PictureProcessingUtil.imgSingeShow(this, personBean.getPhoto(), (ImageView) view);
        }
    }

    @Override
    public void onGetMapPersonDetailSuccess(BaseGetResponse<PersonLogBean> body, boolean flag) {
        rvAdapter.setNewData(body.getRows());
        if (rvAdapter.getData().size() == 0) {
            mStateView.showEmpty();
        } else {
            mStateView.showContent();
        }
    }

    @Override
    public void showProgress() {
        baseShowProgress();
    }

    @Override
    public void showProgress(String str) {
        baseShowProgress(str);
    }

    @Override
    public void hideProgress() {
        baseHideProgress();
    }

    @Override
    public void onSuccess(String msg) {

    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable msg) {
        rvAdapter.setNewData(new ArrayList<>());
        baseOnFailure(msg, true);
    }
}
