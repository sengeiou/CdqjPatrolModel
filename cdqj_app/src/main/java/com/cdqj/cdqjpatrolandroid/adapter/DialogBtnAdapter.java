package com.cdqj.cdqjpatrolandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cdqj.cdqjpatrolandroid.bean.TabEntity;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.utils.DrawableUtils;

import java.util.List;


/**
 * Created by lyf on 2018/8/3 16:32
 *
 * @author lyf
 * desc：弹窗按钮格式adapter
 */
public class DialogBtnAdapter extends BaseAdapter {

    private Context mContext;
    private List<TabEntity> mList;

    public DialogBtnAdapter(Context context, List<TabEntity> list) {
        mContext = context;
        mList = list;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cdqj_patrol_dialog_btn_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTextView.setText(mList.get(position).getTabTitle());
        if (mList.get(position).getTabSelectedIcon() !=0) {
            DrawableUtils.setDrawable(mContext, viewHolder.mTextView, mList.get(position).getTabSelectedIcon(), R.mipmap.icon_goto);
        } else {
            DrawableUtils.setDrawable(mContext, viewHolder.mTextView, 0, R.mipmap.icon_goto);
        }
        return convertView;
    }


    class ViewHolder {
        TextView mTextView;

        ViewHolder(View view) {
            mTextView = view.findViewById(R.id.btn_name);
        }
    }
}
