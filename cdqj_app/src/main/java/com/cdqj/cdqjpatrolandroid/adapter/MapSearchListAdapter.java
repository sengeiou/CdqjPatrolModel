package com.cdqj.cdqjpatrolandroid.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.GridSelectBean;
import com.cdqj.cdqjpatrolandroid.bean.MapSearchBean;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by lyf on 2018/8/21 15:47
 *
 * @author lyf
 * desc：地图搜索Adapter
 */
public class MapSearchListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, PatrolViewHolder> {

    private Context context;
    private List<MultiItemEntity> data;
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MapSearchListAdapter(List<MultiItemEntity> data, Context context) {
        super(data);
        this.data = data;
        this.context = context;
        addItemType(TYPE_LEVEL_0, R.layout.cdqj_patrol_map_search_list_item);
        addItemType(TYPE_LEVEL_1, R.layout.cdqj_patrol_map_search_road_item);

    }


    @Override
    protected void convert(PatrolViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                //GridSelectBean gridSelectBean = (GridSelectBean) item;
                TextView line = helper.getView(R.id.map_search_item_line);
                TextView title = helper.getView(R.id.map_search_item_title);
                line.setVisibility(0==helper.getAdapterPosition()? View.GONE:View.VISIBLE);
                //line.setVisibility("道路".equals(gridSelectBean.getTitle())? View.GONE:View.VISIBLE);
                title.setText(((GridSelectBean)item).getTitle());
                //title.setVisibility("道路".equals(gridSelectBean.getTitle())? View.GONE:View.VISIBLE);
                break;
            case TYPE_LEVEL_1:
                final MapSearchBean bean = (MapSearchBean) item;
                helper.getView(R.id.item_road).setVisibility(bean.getType()==MapSearchBean.ROAD||bean.getType()==MapSearchBean.DEVICE?View.VISIBLE:View.GONE);
                helper.getView(R.id.item_pipeline).setVisibility(bean.getType()==MapSearchBean.OTHER||bean.getType()==MapSearchBean.PERSONNEL
                        ||bean.getType()==MapSearchBean.SITE||bean.getType()==MapSearchBean.HD?View.VISIBLE:View.GONE);
                helper.getView(R.id.map_search_item_line).setVisibility(bean.getSize()==bean.getMaxSize()-1?View.INVISIBLE:View.VISIBLE);
                helper.getView(R.id.item_pipeline_line).setVisibility(bean.getSize()==bean.getMaxSize()-1?View.INVISIBLE:View.VISIBLE);
                helper.addOnClickListener(R.id.item_road_goto)
                        .addOnClickListener(R.id.item_pipeline_img);
                switch (bean.getType()) {
                    case MapSearchBean.ROAD:
                    case MapSearchBean.DEVICE:
                        helper.setText(R.id.item_address, StringUtils.isTrimEmpty(bean.getName())?
                        mContext.getString(R.string.null_text):bean.getName());
                        helper.setText(R.id.item_area, StringUtils.isTrimEmpty(bean.getAddress())?
                                mContext.getString(R.string.null_text):bean.getAddress());
                        break;
                    case MapSearchBean.HD:
                    case MapSearchBean.PERSONNEL:
                    case MapSearchBean.OTHER:
                    case MapSearchBean.SITE:
                        helper.setText(R.id.item_pipeline_name, StringUtils.isTrimEmpty(bean.getName())?
                                mContext.getString(R.string.null_text):bean.getName());
                        helper.setText(R.id.item_pipeline_status, StringUtils.isTrimEmpty(bean.getAddress())?
                                mContext.getString(R.string.null_text):bean.getAddress());
                        helper.setText(R.id.item_pipeline_type, StringUtils.isTrimEmpty(bean.getLevel())?
                                mContext.getString(R.string.null_text):bean.getLevel());
                        break;
                        default:break;
                }
                break;
            default:break;
        }
    }
}
