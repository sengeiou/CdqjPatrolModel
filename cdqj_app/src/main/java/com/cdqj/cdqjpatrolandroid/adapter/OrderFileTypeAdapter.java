package com.cdqj.cdqjpatrolandroid.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cdqj.cdqjpatrolandroid.comstomview.SquareImageView;
import com.cdqj.cdqjpatrolandroid.image.glide.GlideImgManager;
import com.cdqj.cdqjpatrolandroid.utils.StringUrlUtil;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.FileType;

import java.util.ArrayList;

/**
 * 图片|文件展示adapter
 */
public class OrderFileTypeAdapter extends BaseAdapter {
    private ArrayList<FileType> fileTypes;
    private LayoutInflater mInflater;
    private Context context;

    public OrderFileTypeAdapter(Context context, ArrayList<FileType> fileTypes) {
        this.fileTypes = fileTypes;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return fileTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return fileTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderFileTypeAdapter.ViewHolderPic holder;
        if (convertView == null) {
            holder = new OrderFileTypeAdapter.ViewHolderPic();
            convertView = mInflater.inflate(R.layout.cdqj_patrol_gv_grid_file_item, null);
            holder.pic = convertView.findViewById(R.id.gv_item_img);
            convertView.setTag(holder);
        } else {
            holder = (OrderFileTypeAdapter.ViewHolderPic) convertView.getTag();
        }
        if (fileTypes.get(position).getType() == 1) {
            GlideImgManager.loadRoundCornerImage(context, StringUrlUtil.transHttpUrl(fileTypes.get(position).getUrl()),
                    holder.pic, 5);
        } else {
            holder.pic.setImageResource(fileTypes.get(position).getType() == 2?
                    R.mipmap.icon_voice:R.mipmap.icon_video);
        }
        return convertView;
    }

    private class ViewHolderPic {
        /**
         * 图片
         */
        SquareImageView pic;
    }
}
