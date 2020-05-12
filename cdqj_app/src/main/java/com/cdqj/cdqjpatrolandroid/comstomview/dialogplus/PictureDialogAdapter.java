package com.cdqj.cdqjpatrolandroid.comstomview.dialogplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cdqj.cdqjpatrolandroid.R;

import java.util.ArrayList;

public class PictureDialogAdapter extends BaseAdapter {

    private ArrayList<String> data;
    private Context context;

    public PictureDialogAdapter(ArrayList<String> data, Context context) {
        this.data = data;
        this.context = context;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cdqj_patrol_dialog_picture_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.dialogItemTv.setText(data.get(position));
        if (position == data.size() - 1) {
            viewHolder.dialogItemLine.setVisibility(View.GONE);
        } else {
            viewHolder.dialogItemLine.setVisibility(View.VISIBLE);
        }
        return convertView;
    }


    class ViewHolder {
        TextView dialogItemTv;
        TextView dialogItemLine;

        ViewHolder(View view) {
            dialogItemTv = view.findViewById(R.id.dialog_picture_item_name);
            dialogItemLine = view.findViewById(R.id.dialog_picture_item_line);
        }
    }
}
