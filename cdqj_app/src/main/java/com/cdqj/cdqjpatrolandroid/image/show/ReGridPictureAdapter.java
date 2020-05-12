package com.cdqj.cdqjpatrolandroid.image.show;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.image.glide.GlideImgManager;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by lyf on 2018/8/8 11:19
 *
 * @author lyf
 * desc：资源图片 RecyclerView
 */
public class ReGridPictureAdapter extends BaseQuickAdapter<Integer, PictureViewHolder>{

    private Context mContext;

    public ReGridPictureAdapter(List<Integer> data, Context mContext) {
        super(R.layout.cdqj_patrol_gv_grid_file_item, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(PictureViewHolder helper, Integer item) {
        GlideImgManager.loadRoundCornerImage(mContext, item,
                (ImageView) helper.itemView.findViewById(R.id.gv_item_img), 5);

    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        return super.getItemView(layoutResId, parent);
    }
}
