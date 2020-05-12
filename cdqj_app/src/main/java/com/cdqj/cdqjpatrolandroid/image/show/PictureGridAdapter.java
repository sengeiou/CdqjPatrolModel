package com.cdqj.cdqjpatrolandroid.image.show;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.image.glide.GlideImgManager;
import com.cdqj.cdqjpatrolandroid.util.OtherUtil;


/**
 * Created by lyf on 2018/8/8 14:51
 *
 * @author lyf
 * desc：资源图片 GridView
 */
public class PictureGridAdapter  extends BaseAdapter {

    private String[] pics;
    private LayoutInflater mInflater;
    private Context context;

    public PictureGridAdapter(Context context, String[] pics) {
        this.pics = pics;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return pics.length;
    }

    @Override
    public Object getItem(int position) {
        return pics[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderPic holder;
        if (convertView == null) {
            holder = new ViewHolderPic();
            convertView = mInflater.inflate(R.layout.cdqj_patrol_gv_grid_file_item, null);
            holder.pic = convertView.findViewById(R.id.gv_item_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderPic) convertView.getTag();
        }
        if (OtherUtil.isImgUrl(pics[position])) {
            GlideImgManager.loadRoundCornerImage(context, pics[position],
                    holder.pic, 5);
        }
        return convertView;
    }

    private class ViewHolderPic {
        /**
         * 图片
         */
        ImageView pic;
    }

}
