package com.cdqj.cdqjpatrolandroid.comstomview.dialogplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.R;

import java.util.List;

/**
 * Created by lyf on 2018/5/16 10:13
 *
 * @author lyf
 * desc：网格适配器
 */
public class DialogGridPicAdapter extends CheckDialogBaseAdapter {

    private Context context;
    private List<CustomGridPicWindowBean> data;
    private LayoutInflater inflater;

    public DialogGridPicAdapter(Context context, List<CustomGridPicWindowBean> data) {
        super();
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.cdqj_patrol_gv_coustom_grid_pic_item, null);
            holder.title = convertView.findViewById(R.id.gv_main_item_text);
            holder.icon = convertView.findViewById(R.id.gv_main_item_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (ObjectUtils.isNotEmpty(data.get(position))) {
            holder.title.setText(!StringUtils.isTrimEmpty(data.get(position).getTitle())?data.get(position).getTitle():"");
            if (data.get(position).getResourceId()!=0) {
                holder.icon.setImageResource(data.get(position).getResourceId());
            }
        }
        return convertView;
    }

    class ViewHolder {
        TextView title;
        ImageView icon;
    }
}
