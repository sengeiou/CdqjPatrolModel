package com.cdqj.cdqjpatrolandroid.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private List<String> mStringArray;

    public SpinnerAdapter(Context mContext, List<String> mStringArray) {
        super(mContext, android.R.layout.simple_spinner_item, mStringArray);
        this.mContext = mContext;
        this.mStringArray = mStringArray;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // 修改Spinner选择后结果的字体颜色
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        //此处text1是Spinner默认的用来显示文字的TextView
        TextView tv = convertView.findViewById(android.R.id.text1);
        tv.setText(mStringArray.get(position));
        tv.setTextSize(14f);
        tv.setTextColor(Color.BLUE);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        //修改Spinner展开后的字体颜色
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        //此处text1是Spinner默认的用来显示文字的TextView
        TextView tv = convertView.findViewById(android.R.id.text1);
        tv.setText(mStringArray.get(position));
        tv.setTextSize(16f);

        return convertView;
    }
}
