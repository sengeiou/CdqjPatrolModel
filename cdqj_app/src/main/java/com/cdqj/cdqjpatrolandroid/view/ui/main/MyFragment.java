package com.cdqj.cdqjpatrolandroid.view.ui.main;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmDialogListener;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmSelectDialog;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.PictureSelectDialog;
import com.cdqj.cdqjpatrolandroid.config.CdqjInitDataConfig;
import com.cdqj.cdqjpatrolandroid.config.GlobalConfig;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.image.glide.GlideImgManager;
import com.cdqj.cdqjpatrolandroid.utils.AESUtils;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.utils.GsonUtils;
import com.cdqj.cdqjpatrolandroid.util.OtherUtil;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.base.BaseFragment;
import com.cdqj.cdqjpatrolandroid.bean.LoginResultBean;
import com.cdqj.cdqjpatrolandroid.bean.MyFragmentTaskNumberBean;
import com.cdqj.cdqjpatrolandroid.bean.UpImgResultBean;
import com.cdqj.cdqjpatrolandroid.bean.UsernameBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.presenter.MyFragmentPresenter;
import com.cdqj.cdqjpatrolandroid.utils.PatrolEnterPointActivity;
import com.cdqj.cdqjpatrolandroid.utils.PictureProcessingUtil;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.StringUrlUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.version.VersionHelper;
import com.cdqj.cdqjpatrolandroid.view.i.IMyFragmentView;
import com.cdqj.cdqjpatrolandroid.view.ui.my.AppShareActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lyf on 2018/8/13 15:51
 *
 * @author lyf
 * desc：我的界面
 */
public class MyFragment extends BaseFragment<MyFragmentPresenter> implements IMyFragmentView {

    public static final String DO_WORK_STATUS_CHANGE = "do_work_status_change";

    ImageView myInfoIcon;
    TextView myInfoName;
    TextView myInfoPosition;
    TextView myInfoGroup;
    TextView myInfoStatus;
    TextView myTaskCurrentCount;
    TextView myTaskTotalCount;
    TextView myPlanCurrentCount;
    TextView myPlanTotalCount;
    TextView myInfoMsgTitle;
    TextView myInfoMsgContext;
    TextView myInfoMsgDetail;
    TextView myFunctionVersionUpload;
    TextView offWorkBtn;
    ImageView point;
    SmartRefreshLayout myInfoRefreshLayout;
    TextView myFunctionMsgReview;
    TextView downData;
    ConstraintLayout msgCl;
    RelativeLayout relativeLayout;

    private String tokenStr;
    private LoginResultBean userInfo;

    private CancelReceiver cancelReceiver;

    /**
     * 拍照相关
     */
    private String imgPath;

    private String urlImg = "";

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_fragment_my;
    }

    @Override
    protected void findView(View rootView) {
        myInfoIcon = rootView.findViewById(R.id.my_info_icon);
        myInfoName = rootView.findViewById(R.id.my_info_name);
        myInfoPosition = rootView.findViewById(R.id.my_info_position);
        myInfoGroup = rootView.findViewById(R.id.my_info_group);
        myInfoStatus = rootView.findViewById(R.id.my_info_status);
        myTaskCurrentCount = rootView.findViewById(R.id.my_task_current_count);
        myTaskTotalCount = rootView.findViewById(R.id.my_task_total_count);
        myPlanCurrentCount = rootView.findViewById(R.id.my_plan_current_count);
        myPlanTotalCount = rootView.findViewById(R.id.my_plan_total_count);
        myInfoMsgTitle = rootView.findViewById(R.id.my_info_msg_title);
        myInfoMsgContext = rootView.findViewById(R.id.my_info_msg_context);
        myInfoMsgDetail = rootView.findViewById(R.id.my_info_msg_detail);
        myFunctionVersionUpload = rootView.findViewById(R.id.my_function_version_upload);
        offWorkBtn = rootView.findViewById(R.id.my_function_off_work_btn);
        point = rootView.findViewById(R.id.my_function_version_point);
        myInfoRefreshLayout = rootView.findViewById(R.id.my_info_refreshLayout);
        myFunctionMsgReview = rootView.findViewById(R.id.my_function_msg_review);
        downData = rootView.findViewById(R.id.my_function_down_data);
        msgCl = rootView.findViewById(R.id.my_info_msg_cl);
        relativeLayout = rootView.findViewById(R.id.my_function_version_rl);

        offWorkBtn.setVisibility(CdqjInitDataConfig.isNeedWork ? View.VISIBLE : View.GONE);

        relativeLayout.setVisibility(CdqjInitDataConfig.isLib ? View.GONE : View.VISIBLE);
    }

    @Override
    protected String getTitleText() {
        return null;
    }

    /**
     * 界面初始化
     */
    @SuppressLint({"InflateParams", "SetTextI18n"})
    private void initView() {
        Bundle arguments = getArguments();
        assert arguments != null;
        userInfo = arguments.getParcelable("userInfo");
        mPresenter = new MyFragmentPresenter(this);

        myInfoRefreshLayout.setEnableLoadMore(false);
        //myInfoRefreshLayout.setRefreshHeader(new MyFragmentHeader(getActivity()));
        myInfoRefreshLayout.setOnRefreshListener(refreshLayout -> {
            myInfoRefreshLayout.autoRefresh();
            mPresenter.getTaskNumber(false);
        });

        if (ObjectUtils.isNotEmpty(userInfo)) {
            try {
                tokenStr = AESUtils.decrypt(userInfo.getToken());
            } catch (Exception e) {
                e.printStackTrace();
                ToastBuilder.showShortError(R.string.aes_code_error);
            }
            if (!AppUtils.getAppVersionName().equals(userInfo.getAppinfos().getVersion())) {
                myFunctionVersionUpload.setText("在线更新");
                point.setVisibility(View.VISIBLE);
            } else {
                myFunctionVersionUpload.setText(userInfo.getAppinfos().getVersion() + "(点击分享)");
                point.setVisibility(View.GONE);
            }

        }

        if (!StringUtils.isTrimEmpty(tokenStr)) {
            UsernameBean bean = GsonUtils.gsonBuilder.create().fromJson(tokenStr, UsernameBean.class);
            myInfoName.setText(StringUtils.isTrimEmpty(bean.getTrueName()) ? "未知" : bean.getTrueName());
            myInfoPosition.setText(StringUtils.isTrimEmpty(bean.getPost()) ? "暂无岗位" : bean.getPost());
            myInfoGroup.setText(StringUtils.isTrimEmpty(bean.getDepartName()) ? "暂未设置" : bean.getDepartName());
            urlImg = StringUtils.isTrimEmpty(bean.getPhoto()) ?
                    "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3424218392,2266062193&fm=27&gp=0.jpg" : StringUrlUtil.transHttpUrlAndOnlyOne(bean.getPhoto());

            PreferencesUtil.putString(Constant.TRUE_NAME, StringUtils.isTrimEmpty(bean.getTrueName()) ? "未知" : bean.getTrueName());
            PreferencesUtil.putInt(Constant.USER_ID, bean.getId());
            PreferencesUtil.putInt(Constant.DOMAIN_ID, bean.getDomainId());
            if (CdqjInitDataConfig.isNeedWork) {
                GlobalConfig.isDoWork = bean.getStatus() != 2;
            }
            setWorkViewData();
        }

        GlideImgManager.loadCircleImage(getActivity(), urlImg, myInfoIcon);
    }

    private void setWorkViewData() {
//        myInfoStatus.setText((bean.getStatus() == 1 || bean.getStatus() == 3) ? "上班中" : bean.getStatus() == 4 ? "已越界" : "已下班");
        myInfoStatus.setText(GlobalConfig.isDoWork ? "上班中" : "已下班");
        offWorkBtn.setText(GlobalConfig.isDoWork ? "下班" : "上班");
    }

    /**
     * 数据初始化
     */
    @Override
    public void initData() {
        // for cancel action on notification.
        cancelReceiver = new CancelReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DO_WORK_STATUS_CHANGE);
        Objects.requireNonNull(getActivity()).registerReceiver(cancelReceiver, filter);
    }

    @Override
    protected MyFragmentPresenter createPresenter() {
        return new MyFragmentPresenter(this);
    }

    @Override
    protected void loadData() {
        mPresenter.getTaskNumber(true);
    }

    @Override
    public void initView(View rootView) {
        initView();
    }

    @Override
    public void initListener() {
        myFunctionVersionUpload.setOnClickListener(this::setOnClick);
        myFunctionMsgReview.setOnClickListener(this::setOnClick);
        downData.setOnClickListener(this::setOnClick);
        msgCl.setOnClickListener(this::setOnClick);
        myInfoIcon.setOnClickListener(this::setOnClick);
        offWorkBtn.setOnClickListener(this::setOnClick);
    }

    /**
     * 设置监听
     *
     * @param view 监听控件
     */
    public void setOnClick(View view) {
        int i = view.getId();
        if (i == R.id.my_function_version_upload) {
            if (ObjectUtils.isNotEmpty(userInfo) &&
                    AppUtils.getAppVersionName().equals(userInfo.getAppinfos().getVersion())) {
//                    ToastBuilder.showShort(R.string.version_update_new);
                ActivityUtils.startActivity(new Intent(getActivity(), AppShareActivity.class).putExtra("appPath", userInfo.getAppinfos().getFilepath()));
            } else {
                if (!CdqjInitDataConfig.isLib) {
                    new VersionHelper(getActivity(), userInfo, ((CdqjMainActivity) Objects.requireNonNull(getActivity())).cancelReceiver).show();
                }
            }
        } else if (i == R.id.my_function_msg_review) {// 意见反馈
            PatrolEnterPointActivity.gotoMsgFeedbackActivity(getActivity());

        } else if (i == R.id.my_function_down_data) {// 离线数据管理
            ToastBuilder.showShortWarning(R.string.application_not_open);
//                 PatrolEnterPointActivity.gotoOffLineDataActivity(getActivity());
        } else if (i == R.id.my_info_msg_cl) {// 跳转到消息列表界面
            PatrolEnterPointActivity.gotoMsgListActivity(getActivity());

        } else if (i == R.id.my_function_off_work_btn) {// 上班
            new ConfirmSelectDialog(getActivity())
                    .setContentStr("是否执行" + (GlobalConfig.isDoWork ? "下班" : "上班") + "操作")
                    .setListener(new ConfirmDialogListener() {
                        @Override
                        public void onYesClick() {
                            mPresenter.submitChangeWorkStatus(GlobalConfig.isDoWork ? 2 : 1
                                    , PreferencesUtil.getInt(Constant.USER_ID));
                        }

                        @Override
                        public void onNoClick() {

                        }
                    })
                    .initView().show();

        } else if (i == R.id.my_info_icon) {// 头像更新等
            new PictureSelectDialog(getActivity())
                    .setSee(true)
                    .setListener(position -> {
                        if (position == 0) {// 相机
                            imgPath = PictureProcessingUtil.takePicture(MyFragment.this);

                        } else if (position == 1) {// 相册
                            PictureProcessingUtil.checkPicture(MyFragment.this);

                        } else if (position == 2) {// 查看大图
                            PictureProcessingUtil.imgSingeShow(getActivity(), urlImg, (ImageView) view);

                        }
                    })
                    .initView().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_IMAGE_CAPTURE) {// 拍照
                if (!StringUtils.isTrimEmpty(imgPath)) {
                    mPresenter.updateImg(imgPath, Constant.IMG_TYPE);
                } else {
                    ToastBuilder.showShortWarning(R.string.picture_error);
                }
            } else if (requestCode == Constant.REQUEST_CODE_IMAGE_CHECK) {// 相册
                Uri uri = data.getData();
                imgPath = OtherUtil.getImageAbsolutePath(getActivity(), uri);
                if (!StringUtils.isTrimEmpty(imgPath)) {
                    mPresenter.updateImg(imgPath, Constant.IMG_TYPE);
                } else {
                    ToastBuilder.showShortWarning(R.string.picture_error);
                }
            }
        } else {
            ToastBuilder.showShortWarning(R.string.picture_error);
        }
    }

    @Override
    public void onDestroyView() {
        Objects.requireNonNull(getActivity()).unregisterReceiver(cancelReceiver);
        super.onDestroyView();
    }

    @Override
    public void onUpdateImgSuccess(BasePostResponse<List<UpImgResultBean>> basePostResponse) {
        if (basePostResponse.isSuccess()) {
            String filePath = basePostResponse.getObj().get(0).getFilePath();
            // 文件上传成功
            mPresenter.updateImgTo(filePath, PreferencesUtil.getInt(Constant.USER_ID));
        } else {
            ToastBuilder.showShortError(basePostResponse.getMsg());
        }
    }

    @Override
    public void onUpdateImgToSuccess(BasePostResponse<Object> basePostResponse) {
        if (basePostResponse.isSuccess()) {
            // 文件上传成功
            urlImg = basePostResponse.getObj().toString();
            GlideImgManager.loadCircleImage(getActivity(), StringUrlUtil.transHttpUrlAndOnlyOne(basePostResponse.getObj().toString()), myInfoIcon);
            ToastBuilder.showShort("更换头像成功");
        } else {
            ToastBuilder.showShortError(basePostResponse.getMsg());
        }
    }

    @Override
    public void onChangeWorkStatusSuccess(BasePostResponse<Object> basePostResponse) {
        if (basePostResponse.isSuccess()) {
            // 上下班操作成功
            ToastBuilder.showShort((GlobalConfig.isDoWork ? "下班" : "上班") + "操作成功");
            GlobalConfig.isDoWork = !GlobalConfig.isDoWork;
            setWorkViewData();
        } else {
            ToastBuilder.showShortError(basePostResponse.getMsg());
        }
    }

    @Override
    public void onGetTaskNumberSuccess(BasePostResponse<MyFragmentTaskNumberBean> basePostResponse) {
        // 任务数量获取结果
        if (basePostResponse.isSuccess()) {
            // [1,2,3] 总量，未完成，完成
            myTaskCurrentCount.setText(String.valueOf(basePostResponse.getObj().getOrder().get(1)));
            myTaskTotalCount.setText(String.valueOf(basePostResponse.getObj().getOrder().get(0)));
            myPlanCurrentCount.setText(String.valueOf(basePostResponse.getObj().getPlan().get(1)));
            myPlanTotalCount.setText(String.valueOf(basePostResponse.getObj().getPlan().get(0)));
        } else {
            myTaskCurrentCount.setText("0");
            myTaskTotalCount.setText("0");
            myPlanCurrentCount.setText("0");
            myPlanTotalCount.setText("0");
            ToastBuilder.showShortWarning(basePostResponse.getMsg());
        }
    }

    @Override
    public void onFailure(String s) {
        ToastBuilder.showShortError(s);
    }

    @Override
    public void showProgress() {
        baseShowProgress("图片上传中...");
    }

    @Override
    public void showProgress(String str) {
        baseShowProgress(str);
    }

    @Override
    public void hideProgress() {
        myInfoRefreshLayout.finishRefresh();
        baseHideProgress();
    }

    @Override
    public void onSuccess(String msg) {

    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable msg) {
        baseOnFailure(msg);
    }

    class CancelReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (DO_WORK_STATUS_CHANGE.equals(intent.getAction())) {
                offWorkBtn.setText(GlobalConfig.isDoWork ? "下班" : "上班");
                myInfoStatus.setText(GlobalConfig.isDoWork ? "上班中" : "已下班");
            }
        }
    }

}
