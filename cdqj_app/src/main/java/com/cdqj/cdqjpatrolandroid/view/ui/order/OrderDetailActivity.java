package com.cdqj.cdqjpatrolandroid.view.ui.order;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.base.BasePictureActivity;
import com.cdqj.cdqjpatrolandroid.base.BaseRecyclerView;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.CheckDialog;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.CheckDialogBuild;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.DialogAdapter;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.OnCheckDialogSelectListener;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.image.bean.BasePhotoBean;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.adapter.OrderSuperviseReportAdapter;
import com.cdqj.cdqjpatrolandroid.bean.OrderBean;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.bean.UpImgResultBean;
import com.cdqj.cdqjpatrolandroid.bean.UserCom;
import com.cdqj.cdqjpatrolandroid.comstomview.StateView;
import com.cdqj.cdqjpatrolandroid.event.MsgEvent;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.OnUpdateFileBackListener;
import com.cdqj.cdqjpatrolandroid.presenter.OrderDetailPresenter;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.i.IOrderDetailView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 工单详情
 *
 * @author lyf
 * @date 2018年8月6日 11:21:06
 */
public class OrderDetailActivity extends BasePictureActivity<OrderDetailPresenter> implements IOrderDetailView, OnUpdateFileBackListener {

    SmartRefreshLayout orderDetailRefreshLayout;
    TextView orderDetailType;
    TextView orderDetailLevel;
    TextView orderDetailReportCount;
    TextView orderDetailSubmit;
    TextView orderDetailAddress;
    TextView orderDetailPeople;
    TextView orderDetailPeopleTitle;
    TextView orderDetailReportTime;
    TextView orderDetailSubmitTo;
    ConstraintLayout orderDetailCl;
    BaseRecyclerView orderDetailRv;

    ArrayList<UpImgResultBean> pictureResult = new ArrayList<>();
    ArrayList<UpImgResultBean> audioResult = new ArrayList<>();
    ArrayList<UpImgResultBean> videoResult = new ArrayList<>();
    /**
     * 上报列表
     */
    List<OrderSuperviseReportBean> beans = new ArrayList<>();

    /**
     * 上报界面控件初始化
     */
    private TextView title;
    private TextView submit;
    private ImageView esc;
    private BaseRecyclerView rvPicture;
    private BaseRecyclerView rvAudio;
    private BaseRecyclerView rvVideo;
    private EditText etRemark;
    private ArrayList<LocalMedia> selectPictureList = new ArrayList<>();
    private ArrayList<LocalMedia> selectAudioList = new ArrayList<>();
    private ArrayList<LocalMedia> selectVideoList = new ArrayList<>();
    /**
     * 是否显示上报按钮
     * 2 显示
     * 1 隐藏
     */
    private int flag = 1;
    private DialogPlus mDialogPlus;
    /**
     * 声明上报弹窗
     */
    private View mView;
    private OrderBean mBean;
    private OrderSuperviseReportAdapter rvAdapter;

    /**
     * 人员筛选弹窗数据
     */
    private List<String> mListType = new ArrayList<>();
    private List<UserCom> userComs;

    /**
     * 界面初始化
     */
    @SuppressLint("InflateParams")
    @Override
    public void initView() {
        // 初始化上报弹窗
        mView = LayoutInflater.from(this).inflate(R.layout.cdqj_patrol_order_supervise_report_window, null);
        title = mView.findViewById(R.id.supervise_report_title);
        esc = mView.findViewById(R.id.supervise_report_ecs);
        submit = mView.findViewById(R.id.supervise_report_submit);
        etRemark = mView.findViewById(R.id.supervise_report_remark);
        rvPicture = mView.findViewById(R.id.supervise_report_picture_gv);
        rvAudio = mView.findViewById(R.id.supervise_report_audio_gv);
        rvVideo = mView.findViewById(R.id.supervise_report_video_gv);
        super.initView();
        mStateView = StateView.inject(orderDetailRv);
        mStateView.setOnRetryClickListener(() -> {
            page = 1;
            mPresenter.getOrderReports(mBean.getId() + "");
        });
        //是否启用上拉加载功能
        orderDetailRefreshLayout.setEnablePureScrollMode(true);
        mBean = getIntent().getParcelableExtra("OrderBean");
        flag = getIntent().getIntExtra("flag", 1);

        if (ObjectUtils.isNotEmpty(mBean)) {
            assert titleToolbar != null;
            titleToolbar.setMainTitle(StringUtils.isTrimEmpty(mBean.getRelevanceTypeExp()) ? "详情" : mBean.getRelevanceTypeExp());
            orderDetailType.setText(StringUtils.isTrimEmpty(mBean.getRelevanceTypeExp()) ?
                    this.getString(R.string.null_text) : mBean.getRelevanceTypeExp());
            orderDetailLevel.setText(StringUtils.isTrimEmpty(mBean.getOrderLevelExp()) ?
                    this.getString(R.string.null_text) : mBean.getOrderLevelExp());
            orderDetailAddress.setText(StringUtils.isTrimEmpty(mBean.getAddress()) ?
                    this.getString(R.string.null_text) : mBean.getAddress());
            orderDetailPeople.setText(!StringUtils.isTrimEmpty(mBean.getReceiveUserName()) ? mBean.getReceiveUserName()
                    : !StringUtils.isTrimEmpty(mBean.getAddUserExp()) ? mBean.getAddUserExp()
                    : getString(R.string.null_text));
            orderDetailPeopleTitle.setText(!StringUtils.isTrimEmpty(mBean.getReceiveUserName()) ? "执行人" : "上报人");
            orderDetailReportTime.setText(StringUtils.isTrimEmpty(mBean.getAddTime()) ?
                    this.getString(R.string.null_text) : mBean.getAddTime());

        }

        setReportWindow();

        if (2 == flag) {
            titleToolbar.setRightTitleText("转移");
            titleToolbar.setRightTitleColor(R.color.theme_one);
            titleToolbar.setRightTitleClickListener(v -> {
                // 工单转移
                showSelectMan();
            });
            orderDetailSubmit.setVisibility(View.VISIBLE);
            orderDetailSubmitTo.setVisibility(View.VISIBLE);
        } else {
            orderDetailSubmit.setVisibility(View.GONE);
            orderDetailSubmitTo.setVisibility(View.GONE);
        }

        orderDetailRv.setLayoutManager(new LinearLayoutManager(this));
        rvAdapter = new OrderSuperviseReportAdapter(
                R.layout.cdqj_patrol_order_supervise_report_item, beans, this, mBean.getAddress(), mBean.getRelevanceTypeExp());
        rvAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);

        rvAdapter.bindToRecyclerView(orderDetailRv);

        mPresenter.getOrderReports(String.valueOf(mBean.getId()));
    }

    @Override
    protected List<BasePhotoBean> setRvView() {
        List<BasePhotoBean> basePhotoBeans = new ArrayList<>();
        selectPictureList.clear();
        // 照片
        basePhotoBeans.add(new BasePhotoBean(rvPicture, selectPictureList, PictureMimeType.ofImage(), 9));
        // 视频
        selectVideoList.clear();
        basePhotoBeans.add(new BasePhotoBean(rvVideo, selectVideoList, PictureMimeType.ofVideo(), 9));
        // 录音
        selectAudioList.clear();
        basePhotoBeans.add(new BasePhotoBean(rvAudio, selectAudioList, PictureMimeType.ofAudio(), 9));
        return basePhotoBeans;
    }

    @Override
    protected OrderDetailPresenter createPresenter() {
        return new OrderDetailPresenter(this, this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_order_detail;
    }

    @Override
    protected void findView() {
        orderDetailRefreshLayout = findViewById(R.id.order_detail_refreshLayout);
        orderDetailType = findViewById(R.id.order_detail_type);
        orderDetailLevel = findViewById(R.id.order_detail_level);
        orderDetailReportCount = findViewById(R.id.order_detail_report_count);
        orderDetailSubmit = findViewById(R.id.order_detail_submit);
        orderDetailAddress = findViewById(R.id.order_detail_address);
        orderDetailPeople = findViewById(R.id.order_detail_people);
        orderDetailPeopleTitle = findViewById(R.id.order_detail_people_title);
        orderDetailReportTime = findViewById(R.id.order_detail_report_time);
        orderDetailSubmitTo = findViewById(R.id.order_detail_submit_to);
        orderDetailCl = findViewById(R.id.order_detail_cl);
        orderDetailRv = findViewById(R.id.order_detail_rv);
    }

    @Override
    protected String getTitleText() {
        return "工单详情";
    }

    /**
     * 上报弹窗初始化
     */
    private void setReportWindow() {
        // 图片
//        selectPictureList.clear();
        // 视频
//        selectVideoList.clear();
        // 录音
//        selectAudioList.clear();
        initClick();
    }

    @Override
    public void initListener() {
        findViewById(R.id.order_detail_submit).setOnClickListener(this::setClick);
        findViewById(R.id.order_detail_submit_to).setOnClickListener(this::setClick);
    }

    /**
     * 监护上报/完成
     */
    public void setClick(View view) {
        int i = view.getId();
        if (i == R.id.order_detail_submit) {// 监护上报
            showReportWindow(true);
        } else if (i == R.id.order_detail_submit_to) {// 完成
            mPresenter.finishOrder(mBean.getRelevanceId() + "", mBean.getId() + "");
        }
    }

    private void showReportWindow(boolean flag) {
        title.setText(flag ? "监护上报" : "完成上报");
        if (ObjectUtils.isEmpty(mDialogPlus)) {
            mDialogPlus = DialogPlus.newDialog(this)
                    // Select different holder.ListHolder|ViewHolder(自定义布局)|GridHolder
                    .setContentHolder(new ViewHolder(mView))
                    .setContentHeight(ScreenUtils.getScreenHeight() / 3 * 2)
                    .setMargin(ConvertUtils.dp2px(10), 0, ConvertUtils.dp2px(10), 0)
                    .setPadding(ConvertUtils.dp2px(10), ConvertUtils.dp2px(10),
                            ConvertUtils.dp2px(10), ConvertUtils.dp2px(10))
                    // Set dialog position. BOTTOM (default), TOP or CENTER. You can also combine other Gravity options.
                    // 设置对话框位置。底部（默认），顶部或中心。你也可以结合其他重力选项。
                    .setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)
                    .setContentBackgroundResource(R.drawable.stroke_bg_radius_top)
                    // Define if the dialog is cancelable and should be closed when back pressed or out of dialog is clicked
                    // 定义对话框是否是可取消的，当被按下或关闭对话框时，应该关闭对话框
                    .setCancelable(true)
                    // Set Adapter, this adapter will be used to fill the content for ListHolder and GridHolder.
                    // This is required if the content holder is ListHolder or GridHolder.
                    // It is not required if the content holder is ViewHolder.
                    // 设置适配器，这个适配器将被用来填充ListHolder和GridHolder的内容。
                    // 如果内容持有者是ListHolder或GridHolder，则这是必需的。如果内容持有者是ViewHolder，则不需要。
                    // .setAdapter(adapter)
                    // Set animation resources
                    .setOnClickListener((dialog, view) -> {
                        int i1 = view.getId();
                        if (i1 == R.id.supervise_report_submit) {
                            if (selectPictureList.isEmpty() && selectAudioList.isEmpty() && selectVideoList.isEmpty()) {
                                ToastBuilder.showShort("请至少添加一种文件");
                                return;
                            }
                            showProgress("文件上传中...");
                            pictureResult.clear();
                            audioResult.clear();
                            videoResult.clear();
                            StringBuilder pics = new StringBuilder();
                            StringBuilder audios = new StringBuilder();
                            StringBuilder videos = new StringBuilder();
                            for (int i = 0; i < selectPictureList.size(); i++) {
                                if (selectPictureList.get(i).isCompressed()) {
                                    pics.append(selectPictureList.get(i).getCompressPath());
                                    if (i != selectPictureList.size() - 1) {
                                        pics.append(",");
                                    }
                                } else {
                                    pics.append(selectPictureList.get(i).getPath());
                                    if (i != selectPictureList.size() - 1) {
                                        pics.append(",");
                                    }
                                }
                            }
                            if (!StringUtils.isTrimEmpty(pics.toString())) {
                                mPresenter.upLoadFile(pics.toString(), Constant.IMG_TYPE);
                            }
                            for (int i = 0; i < selectAudioList.size(); i++) {
                                audios.append(selectAudioList.get(i).getPath());
                                if (i != selectAudioList.size() - 1) {
                                    audios.append(",");
                                }
                            }
                            if (!StringUtils.isTrimEmpty(audios.toString())) {
                                mPresenter.upLoadFile(audios.toString(), Constant.AUDIO_TYPE);
                            }
                            for (int i = 0; i < selectVideoList.size(); i++) {
                                videos.append(selectVideoList.get(i).getPath());
                                if (i != selectVideoList.size() - 1) {
                                    videos.append(",");
                                }
                            }
                            if (!StringUtils.isTrimEmpty(videos.toString())) {
                                mPresenter.upLoadFile(videos.toString(), Constant.VIDEO_TYPE);
                            }

                        } else if (i1 == R.id.supervise_report_ecs) {
                            if (ObjectUtils.isNotEmpty(dialog)) {
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            }

                        }
                    })
                    .setInAnimation(R.anim.slide_in_bottom)
                    .setOutAnimation(R.anim.slide_out_bottom)
                    .create();
        }
        if (mDialogPlus.isShowing()) {
            mDialogPlus.dismiss();
        }
        mDialogPlus.show();
    }

    @Override
    public void onUpdateFileSuccess(BasePostResponse<List<UpImgResultBean>> beans, int type, int flag) {
        if (beans.isSuccess()) {
            if (type == Constant.IMG_TYPE) {
                pictureResult.addAll(beans.getObj());
            } else if (type == Constant.AUDIO_TYPE) {
                audioResult.addAll(beans.getObj());
            } else if (type == Constant.VIDEO_TYPE) {
                videoResult.addAll(beans.getObj());
            }
            if (pictureResult.size() == selectPictureList.size() && audioResult.size() == selectAudioList.size()
                    && videoResult.size() == selectVideoList.size()) {
                showProgress("数据上传中...");
                StringBuilder reportPicture = new StringBuilder();
                StringBuilder reportVoice = new StringBuilder();
                StringBuilder reportVideo = new StringBuilder();
                for (UpImgResultBean bean : pictureResult) {
                    reportPicture.append(bean.getFilePath());
                    reportPicture.append(",");
                }
                for (UpImgResultBean bean : audioResult) {
                    reportVoice.append(bean.getFilePath());
                    reportVoice.append(",");
                }
                for (UpImgResultBean bean : videoResult) {
                    reportVideo.append(bean.getFilePath());
                    reportVideo.append(",");
                }
                HashMap<String, String> map = new HashMap<>(4);
                map.put("orderId", mBean.getId() + "");
                map.put("relevanceId", mBean.getRelevanceId() + "");
                map.put("reportDescript", etRemark.getText().toString());
                if (reportPicture.length() > 0) {
                    map.put("reportPicture", reportPicture.substring(0, reportPicture.length() - 1));
                }
                if (reportVoice.length() > 0) {
                    map.put("reportVoice", reportVoice.substring(0, reportVoice.length() - 1));
                }
                if (reportVideo.length() > 0) {
                    map.put("reportVideo", reportVideo.substring(0, reportVideo.length() - 1));
                }
                mPresenter.addupdate(map);
            }
        } else {
            if (type == Constant.IMG_TYPE) {
                StringBuilder pics = new StringBuilder();
                for (int i = 0; i < selectPictureList.size(); i++) {
                    if (selectPictureList.get(i).isCompressed()) {
                        pics.append(selectPictureList.get(i).getCompressPath());
                        if (i != selectPictureList.size() - 1) {
                            pics.append(",");
                        }
                    } else {
                        pics.append(selectPictureList.get(i).getPath());
                        if (i != selectPictureList.size() - 1) {
                            pics.append(",");
                        }
                    }
                }
                if (!StringUtils.isTrimEmpty(pics.toString())) {
                    mPresenter.upLoadFile(pics.toString(), Constant.IMG_TYPE);
                }

            } else if (type == Constant.AUDIO_TYPE) {
                StringBuilder audios = new StringBuilder();
                for (int i = 0; i < selectAudioList.size(); i++) {
                    audios.append(selectAudioList.get(i).getPath());
                    if (i != selectAudioList.size() - 1) {
                        audios.append(",");
                    }
                }
                if (!StringUtils.isTrimEmpty(audios.toString())) {
                    mPresenter.upLoadFile(audios.toString(), Constant.AUDIO_TYPE);
                }
            } else if (type == Constant.VIDEO_TYPE) {
                StringBuilder videos = new StringBuilder();
                for (int i = 0; i < selectVideoList.size(); i++) {
                    videos.append(selectVideoList.get(i).getPath());
                    if (i != selectVideoList.size() - 1) {
                        videos.append(",");
                    }
                }
                if (!StringUtils.isTrimEmpty(videos.toString())) {
                    mPresenter.upLoadFile(videos.toString(), Constant.VIDEO_TYPE);
                }
            }
        }
    }

    @Override
    public void onAddupdateSuccse(BasePostResponse<Object> body) {
        ToastBuilder.showShort(body.getMsg());
        hideProgress();
        mDialogPlus.dismiss();
        selectPictureList.clear();
        selectAudioList.clear();
        selectVideoList.clear();
        initClick();
        mPresenter.getOrderReports(mBean.getId() + "");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onGetOrderReportsSuccse(BasePostResponse<List<OrderSuperviseReportBean>> basePostResponse) {
        orderDetailReportCount.setText("上报次数：" + basePostResponse.getObj().size());
        rvAdapter.setNewData(basePostResponse.getObj());
    }

    @Override
    public void onFinishOrder(BasePostResponse<Object> basePostResponse) {
        // 保存成功
        if (basePostResponse.isSuccess()) {
            ToastBuilder.showShort("保存成功");
            // 通知主界面刷新
            EventBus.getDefault().post(new MsgEvent(true));
            finish();
        } else {
            ToastBuilder.showShortWarning(basePostResponse.getMsg());
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
    public void onFailure(String e) {
        hideProgress();
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable msg) {
        baseOnFailure(msg);
    }

    /**
     * 人员筛选
     */
    private void showSelectMan() {
        if (mListType.isEmpty()) {
            mPresenter.getUserCombobox();
            return;
        }
        DialogAdapter adapter = new DialogAdapter((ArrayList<String>) mListType, this);
        CheckDialogBuild dialogBuild = CheckDialog.build(this, mListType)
                .setSingle(true)
                .setSingleTextHeader(true)
                .setExpanded(true)
                .setTitle("请选择执行人员")
                .setBaseAdapter(adapter)
                .setCheckDialogSelect(new OnCheckDialogSelectListener() {
                    @Override
                    public void onCheckSelect(List titles, List positions) {

                    }

                    @Override
                    public void onCheckSingle(int position) {

                    }

                    @Override
                    public void onItemClickListener(int position) {
                        mPresenter.transferOrder(String.valueOf(mBean.getId()), userComs.get(position).getValue(), userComs.get(position).getText());
                    }
                });
        dialogBuild.creatDialog().show();
    }

    @Override
    public void onGetUserCombobox(ArrayList<UserCom> userComs) {
        this.userComs = userComs;
        for (UserCom userCom : userComs) {
            mListType.add(userCom.getText());
        }
        showSelectMan();
    }

}
