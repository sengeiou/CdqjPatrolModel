package com.cdqj.cdqjpatrolandroid.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.MsgBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by lyf on 2018/8/3 16:32
 *
 * @author lyf
 * desc：消息列表adapter
 */
public class MsgListAdapter extends BaseQuickAdapter<MsgBean, PatrolViewHolder> {

    private Context mContext;

    public MsgListAdapter(int layoutResId, @Nullable List<MsgBean> data, Context mContext) {
        super(layoutResId, data);
        this.mContext = mContext;
    }

    public MsgListAdapter(@Nullable List<MsgBean> data) {
        super(data);
    }

    public MsgListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(PatrolViewHolder helper, MsgBean item) {
        helper.setText(R.id.msg_item_send_user, StringUtils.isTrimEmpty(item.getAddUserExp())?
                mContext.getString(R.string.null_text):item.getAddUserExp());
        helper.setText(R.id.msg_item_time, StringUtils.isTrimEmpty(item.getAddTime())?
                mContext.getString(R.string.null_text):item.getAddTime());
        helper.setText(R.id.msg_item_context, StringUtils.isTrimEmpty(item.getRemarks())?
                mContext.getString(R.string.null_text):item.getRemarks());

        TextView textView = helper.getView(R.id.msg_item_status);
        textView.setText(item.getStatus() == 1?"已读":item.getStatus()==2?"未读":"删除");
        helper.setText(R.id.msg_item_see_msg, item.getStatus() == 1?"消息已读":item.getStatus()==2?"点击已读":"消息已删除");
        textView.setTextColor(item.getStatus() == 1? ContextCompat.getColor(mContext, R.color.theme_one):
                item.getStatus()==2?ContextCompat.getColor(mContext, R.color.red):ContextCompat.getColor(mContext, R.color.text_auxiliary));
        helper.addOnClickListener(R.id.msg_item_see_msg);
    }
}
