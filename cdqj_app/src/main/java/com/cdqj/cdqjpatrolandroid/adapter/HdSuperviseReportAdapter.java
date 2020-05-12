package com.cdqj.cdqjpatrolandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.comstomview.CustomGridView;
import com.cdqj.cdqjpatrolandroid.image.show.CustomImageShowActivity;
import com.cdqj.cdqjpatrolandroid.image.show.ImageViewBean;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.FileType;
import com.cdqj.cdqjpatrolandroid.bean.HdOrderBean;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.StringUrlUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.previewlibrary.GPreviewBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyf on 2018/8/3 16:38
 *
 * @author lyf
 * desc：上报文件Adapter
 */
public class HdSuperviseReportAdapter extends BaseQuickAdapter<OrderSuperviseReportBean, PatrolViewHolder> {

    private Context mContext;
    private ArrayList<ImageViewBean> mThumbViewInfoList;
    private HdOrderBean bean;

    public HdSuperviseReportAdapter(int layoutResId, @Nullable List<OrderSuperviseReportBean> data, Context context, HdOrderBean bean) {
        super(layoutResId, data);
        this.mContext = context;
        this.bean = ObjectUtils.isEmpty(bean)?new HdOrderBean():bean;
    }

    @Override
    protected void convert(PatrolViewHolder helper, final OrderSuperviseReportBean item) {
        helper.setText(R.id.supervise_report_year, StringUtils.isTrimEmpty(item.getReportTime())?
                mContext.getString(R.string.null_text):(item.getReportTime().split("-")[0]));
        helper.setText(R.id.supervise_report_day, StringUtils.isTrimEmpty(item.getReportTime())?
                mContext.getString(R.string.null_text):(item.getReportTime().split("-")[1] + "."
                + item.getReportTime().split("-")[2].split(" ")[0]));
        helper.setText(R.id.supervise_report_user, StringUtils.isTrimEmpty(item.getReportUserName())?
                mContext.getString(R.string.null_text):item.getReportUserName());
        helper.setText(R.id.supervise_report_type, StringUtils.isTrimEmpty(bean.getHdObjectExp())?
                mContext.getString(R.string.null_text):bean.getHdObjectExp());
        helper.setText(R.id.supervise_report_address, StringUtils.isTrimEmpty(bean.getAddress())?
                mContext.getString(R.string.null_text):bean.getAddress());
        ArrayList<FileType> fileTypes = new ArrayList<>();
        if (!TextUtils.isEmpty(item.getReportPicture())) {
            for (String url : item.getReportPicture().split(",")) {
                fileTypes.add(new FileType(url, 1));
            }
        }
        if (!TextUtils.isEmpty(item.getReportVoice())) {
            for (String url : item.getReportVoice().split(",")) {
                fileTypes.add(new FileType(url, 2));
            }
        }
        if (!TextUtils.isEmpty(item.getReportVideo())) {
            for (String url : item.getReportVideo().split(",")) {
                fileTypes.add(new FileType(url, 3));
            }
        }
        ImageView imageView = helper.getView(R.id.supervise_report_more);
        CustomGridView rcView = helper.getView(R.id.supervise_report_gv);
        if (fileTypes.size() <= 4) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
        OrderFileTypeAdapter adapter = new OrderFileTypeAdapter(mContext,
                getArrayData(fileTypes, true));
        rcView.setAdapter(adapter);
        // GridView是显示了一行
        rcView.setTag(true);
        imageView.setTag(rcView);
        rcView.setTag(rcView.getId(), fileTypes);
        rcView.setOnItemClickListener((parent, view, position, id) -> {
            if (fileTypes.get(position).getType() == 2) {
                PictureSelector.create((Activity) mContext).externalPictureAudio(PreferencesUtil.getString(Constant.FILE_SERVICE_PATH) + fileTypes.get(position).getUrl());
                return;
            }
            if (fileTypes.get(position).getType() == 3) {
                PictureSelector.create((Activity) mContext).externalPictureVideo(PreferencesUtil.getString(Constant.FILE_SERVICE_PATH) + fileTypes.get(position).getUrl());
                return;
            }
            if (fileTypes.get(position).getType() == 1) {
                mThumbViewInfoList = new ArrayList<>();
                for (int i = 0;i < fileTypes.size();i ++) {
                    if (fileTypes.get(i).getType() == 1) {
                        FileType reId = fileTypes.get(i);
                        mThumbViewInfoList.add(new ImageViewBean(StringUrlUtil.transHttpUrl(reId.getUrl())));
                    }
                }
                //在你点击时，调用computeBoundsBackward（）方法
                computeBoundsBackward((CustomGridView) parent, parent.getFirstVisiblePosition());
                GPreviewBuilder.from((Activity) mContext)
                        .to(CustomImageShowActivity.class)
                        .setData(mThumbViewInfoList)
                        .setCurrentIndex(position)
                        //是否在黑屏区域点击返回
                        .setSingleFling(false)
                        //是否禁用图片拖拽返回
                        .setDrag(false)
                        .setType(GPreviewBuilder.IndicatorType.Dot)
                        .start();
            }
        });
        imageView.setOnClickListener(v -> {
            CustomGridView gridView = (CustomGridView) v.getTag();
            boolean isShowOneLine = !(boolean) gridView.getTag();
            OrderFileTypeAdapter adapter1 = new OrderFileTypeAdapter(mContext,
                    getArrayData(fileTypes, isShowOneLine));
            gridView.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();
            gridView.setTag(isShowOneLine);
        });
    }

    /**
     * 设置是否显示一行数据
     *
     * @param flag CustomGridView
     * @return String[]
     */
    private ArrayList<FileType> getArrayData(ArrayList<FileType> fileTypes, boolean flag) {
        ArrayList<FileType> newFile = new ArrayList<>();
        if (fileTypes.size() > 4) {
            if (flag) {
                for (int i = 0; i < 4; i++) {
                    newFile.add(fileTypes.get(i));
                }
                return newFile;
            }
        }
        return fileTypes;
    }

    /**
     * 查找信息
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    private void computeBoundsBackward(CustomGridView gridView, int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos; i < mThumbViewInfoList.size(); i++) {
            View itemView = gridView.getChildAt(i - firstCompletelyVisiblePos);
            Rect bounds = new Rect();
            if (itemView != null) {
                //需要显示过度控件
                ImageView thumbView = itemView.findViewById(R.id.gv_item_img);
                //拿到在控件屏幕可见中控件显示为矩形Rect信息
                thumbView.getGlobalVisibleRect(bounds);
            }
            mThumbViewInfoList.get(i).setBounds(bounds);
        }
    }
}
