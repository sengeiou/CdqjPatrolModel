package com.cdqj.cdqjpatrolandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cdqj.cdqjpatrolandroid.comstomview.CustomGridView;
import com.cdqj.cdqjpatrolandroid.image.show.CustomImageShowActivity;
import com.cdqj.cdqjpatrolandroid.image.show.ImageViewBean;
import com.cdqj.cdqjpatrolandroid.image.show.PictureGridAdapter;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.OrderSuperviseReportBean;
import com.previewlibrary.GPreviewBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lyf on 2018/8/3 16:38
 *
 * @author lyf
 * desc：地图隐患Adapter
 */
public class MapHdFileAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ImageViewBean> mThumbViewInfoList;
    private List<OrderSuperviseReportBean> data;

    public MapHdFileAdapter(List<OrderSuperviseReportBean> data, Context context) {
        this.data = data;
        this.mContext = context;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.cdqj_patrol_order_supervise_report_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        OrderSuperviseReportBean item = data.get(position);
        if (item.getUrls().length <= 4) {
            viewHolder.imageView.setVisibility(View.GONE);
        } else {
            viewHolder.imageView.setVisibility(View.VISIBLE);
        }
        PictureGridAdapter adapter = new PictureGridAdapter(mContext,
                getArrayData(item.getUrls(), true));
        viewHolder.rcView.setAdapter(adapter);
        // GridView是显示了一行
        viewHolder.rcView.setTag(true);
        viewHolder.imageView.setTag(viewHolder.rcView);
        viewHolder.rcView.setTag(viewHolder.rcView.getId(),item.getUrls());
        viewHolder.rcView.setOnItemClickListener((parent1, view, position1, id) -> {
            String[] urls = (String[]) parent1.getTag(parent1.getId());
            mThumbViewInfoList = new ArrayList<>();
            for (String reId:urls) {
                mThumbViewInfoList.add(new ImageViewBean(String.valueOf(reId)));
            }
            //在你点击时，调用computeBoundsBackward（）方法
            computeBoundsBackward((CustomGridView) parent1, parent1.getFirstVisiblePosition());
            GPreviewBuilder.from((Activity)mContext)
                    .to(CustomImageShowActivity.class)
                    .setData(mThumbViewInfoList)
                    .setCurrentIndex(position1)
                    //是否在黑屏区域点击返回
                    .setSingleFling(false)
                    //是否禁用图片拖拽返回
                    .setDrag(false)
                    .setType(GPreviewBuilder.IndicatorType.Dot)
                    .start();
        });
        viewHolder.imageView.setOnClickListener(v -> {
            CustomGridView gridView = (CustomGridView) v.getTag();
            boolean isShowOneLine = !(boolean)gridView.getTag();
            PictureGridAdapter adapter1 = new PictureGridAdapter(mContext,
                    getArrayData(item.getUrls(), isShowOneLine));
            gridView.setAdapter(adapter1);
            adapter1.notifyDataSetChanged();
            gridView.setTag(isShowOneLine);
        });
        return convertView;
    }


    class ViewHolder {
        ImageView imageView;
        CustomGridView rcView;

        ViewHolder(View view) {
            imageView = view.findViewById(R.id.supervise_report_more);
            rcView = view.findViewById(R.id.supervise_report_gv);
        }
    }


    /**
     * 查找信息
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    private void computeBoundsBackward(CustomGridView gridView, int firstCompletelyVisiblePos) {
        for (int i = firstCompletelyVisiblePos; i < mThumbViewInfoList.size(); i++) {
            View itemView = gridView.getChildAt(i - firstCompletelyVisiblePos);
            Rect bounds = new Rect();
            if (itemView != null) {
                //需要显示过度控件
                ImageView thumbView = itemView.findViewById(R.id.gv_item_img);
                //拿到在控件屏幕可见中控件显示为矩形Rect信息
                thumbView.getGlobalVisibleRect(bounds);
            }
            mThumbViewInfoList.get(i).setBounds(bounds);
        }
    }

    /**
     * 设置是否显示一行数据
     * @param urls 资源ID/Url
     * @param flag CustomGridView
     * @return String[]
     */
    private String[] getArrayData(String[] urls, boolean flag) {
        if (urls.length > 4) {
            if (flag) {
                return Arrays.copyOf(urls, 4);
            }
        }
        return urls;
    }
}
