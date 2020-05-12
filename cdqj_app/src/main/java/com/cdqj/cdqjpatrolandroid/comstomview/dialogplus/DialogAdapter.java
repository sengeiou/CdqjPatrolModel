package com.cdqj.cdqjpatrolandroid.comstomview.dialogplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdqj.cdqjpatrolandroid.R;

import java.util.ArrayList;

public class DialogAdapter extends CheckDialogBaseAdapter {

    private ArrayList<String> data;
    private Context context;

    public DialogAdapter(ArrayList<String> data, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.cdqj_patrol_dialog_plus_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.dialogItemTv.setText(data.get(position));
        if (isSingle) {
            if (this.position == position) {
                viewHolder.dialogItemImg.setVisibility(View.VISIBLE);
            } else {
                viewHolder.dialogItemImg.setVisibility(View.GONE);
            }
        }
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
        ImageView dialogItemImg;

        ViewHolder(View view) {
            dialogItemTv = view.findViewById(R.id.dialog_item_name);
            dialogItemLine = view.findViewById(R.id.dialog_item_line);
            dialogItemImg = view.findViewById(R.id.dialog_item_true);
        }
    }
}
