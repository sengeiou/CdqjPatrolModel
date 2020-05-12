package com.cdqj.cdqjpatrolandroid.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.image.glide.GlideImgManager;
import com.cdqj.cdqjpatrolandroid.utils.StringUrlUtil;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.OrderBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by lyf on 2018/8/3 16:38
 *
 * @author lyf
 * desc：工单Adapter
 */
public class OrderListAdapter extends BaseQuickAdapter<OrderBean, PatrolViewHolder> {

    private Context mContext;
    /**
     * 工单步骤标识
     * 1.我的工单
     * 1）工单待接单：显示接单和拒单按钮
     * 2）工单已经接单：显示监护上报按钮
     * 2.工单派发：显示派发按钮
     * 3.工单审核：显示审核与驳回按钮
     * 4.台帐：所有按钮不显示
     */
    private int flag;

    public OrderListAdapter(int layoutResId, @Nullable List<OrderBean> data, Context context, int flag) {
        super(layoutResId, data);
        this.mContext = context;
        this.flag = flag;
    }

    @Override
    protected void convert(PatrolViewHolder helper, OrderBean item) {
        if (1 == flag) {
            // 我的工单 1、待派单 2、已派单 3、接单 4、回报处理  5、拒单 6、完成上报  7、驳回  8、作废  9、完成
            // 1）工单待接单：显示接单和拒单按钮
            // 2）工单已经接单：显示监护上报按钮
            if (item.getStatus() == 2) {
                helper.getView(R.id.order_item_receipt_submit).setVisibility(View.VISIBLE);
                helper.getView(R.id.order_item_refusal_submit).setVisibility(View.VISIBLE);
                helper.getView(R.id.order_item_supervise_submit).setVisibility(View.GONE);
            } else {
                helper.getView(R.id.order_item_receipt_submit).setVisibility(View.GONE);
                helper.getView(R.id.order_item_refusal_submit).setVisibility(View.GONE);
                helper.getView(R.id.order_item_supervise_submit).setVisibility(View.VISIBLE);
            }
            helper.getView(R.id.order_item_distribute_submit).setVisibility(View.GONE);
        } else if (2 == flag) {
            // 工单派发(隐患派发)：显示派发按钮
            helper.getView(R.id.order_item_receipt_submit).setVisibility(View.GONE);
            helper.getView(R.id.order_item_refusal_submit).setVisibility(View.GONE);
            helper.getView(R.id.order_item_distribute_submit).setVisibility(View.VISIBLE);
            helper.getView(R.id.order_item_supervise_submit).setVisibility(View.GONE);
        } else if (3 == flag) {
            // 主流程工单审核(隐患审核)：显示审核与驳回按钮
            helper.getView(R.id.order_item_receipt_submit).setVisibility(View.VISIBLE);
            helper.getView(R.id.order_item_refusal_submit).setVisibility(View.VISIBLE);
            helper.setText(R.id.order_item_receipt_submit, "通过");
            helper.setText(R.id.order_item_refusal_submit, "驳回");
            helper.getView(R.id.order_item_distribute_submit).setVisibility(View.GONE);
            helper.getView(R.id.order_item_supervise_submit).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.order_item_receipt_submit).setVisibility(View.GONE);
            helper.getView(R.id.order_item_refusal_submit).setVisibility(View.GONE);
            helper.getView(R.id.order_item_distribute_submit).setVisibility(View.GONE);
            helper.getView(R.id.order_item_supervise_submit).setVisibility(View.GONE);
        }
        helper.getView(R.id.order_item_order_type).setVisibility(item.getOrderType()==2?View.VISIBLE:View.GONE);
        GlideImgManager.loadRoundCornerImage(mContext, StringUrlUtil.transHttpUrlAndOnlyOne(item.getPicUrls()), helper.getView(R.id.order_item_img), 5);

        helper.setText(R.id.order_item_type, StringUtils.isTrimEmpty(item.getRelevanceTypeExp()) ?
                mContext.getString(R.string.null_text) : item.getRelevanceTypeExp());
        helper.setText(R.id.order_item_address, StringUtils.isTrimEmpty(item.getAddress()) ?
                mContext.getString(R.string.null_text) : item.getAddress());
        helper.setText(R.id.order_item_level, StringUtils.isTrimEmpty(item.getOrderLevelExp()) ?
                mContext.getString(R.string.null_text) : item.getOrderLevelExp());
        helper.setText(R.id.order_item_report_people, !StringUtils.isTrimEmpty(item.getReceiveUserName()) ? item.getReceiveUserName()
                :!StringUtils.isTrimEmpty(item.getAddUserExp()) ? item.getAddUserExp()
                : mContext.getString(R.string.null_text));
        helper.setText(R.id.order_item_report_people_title, !StringUtils.isTrimEmpty(item.getReceiveUserName()) ? "执行人":"上报人");
        if (flag == 1) {
            helper.setText(R.id.order_item_report_time_title, "派单时间");
            helper.setText(R.id.order_item_report_time, StringUtils.isTrimEmpty(item.getRepairDate()) ?
                    mContext.getString(R.string.null_text) : item.getRepairDate());
        } else {
            helper.setText(R.id.order_item_report_time_title, "生成时间");
            helper.setText(R.id.order_item_report_time, StringUtils.isTrimEmpty(item.getAddTime()) ?
                    mContext.getString(R.string.null_text) : item.getAddTime());
        }
        helper.setText(R.id.order_item_report_new_time, StringUtils.isTrimEmpty(item.getUpdateTime()) ?
                mContext.getString(R.string.null_text) : item.getUpdateTime());
        helper.setText(R.id.order_item_main_type, StringUtils.isTrimEmpty(item.getRelevanceTypeExp()) ?
                mContext.getString(R.string.null_text) : item.getRelevanceTypeExp());

        helper.addOnClickListener(R.id.order_item_receipt_submit)
                .addOnClickListener(R.id.order_item_refusal_submit)
                .addOnClickListener(R.id.order_item_distribute_submit)
                .addOnClickListener(R.id.order_item_supervise_submit);
    }
}
