package com.cdqj.cdqjpatrolandroid.comstomview.dialogplus;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyf on 2018/9/13 14:40
 *
 * @author lyf
 * desc：导航选择
 */
public class NavSelectDialog {

    private DialogPlus dialog;
    private List<CustomGridPicWindowBean> windowData;
    private String titleStr="请选择导航";
    private Context mContext;
    private NavOnItemClick mOnItemClick;
    private int gravity = Gravity.CENTER;
    private int width = ScreenUtils.getScreenWidth()/5*4;
    private int height = ViewGroup.LayoutParams.WRAP_CONTENT;

    public NavSelectDialog(Context context) {
        mContext = context;
        // 导航弹窗数据初始化
        windowData = new ArrayList<>();
        CustomGridPicWindowBean windowBean1 = new CustomGridPicWindowBean();
        windowBean1.setTitle("百度导航");
        windowBean1.setResourceId(R.mipmap.icon_bd);
        windowData.add(windowBean1);
        CustomGridPicWindowBean windowBean2 = new CustomGridPicWindowBean();
        windowBean2.setTitle("高德导航");
        windowBean2.setResourceId(R.mipmap.icon_gd);
        windowData.add(windowBean2);
    }

    public NavSelectDialog setWindowData(List<CustomGridPicWindowBean> windowData) {
        this.windowData = windowData;
        return this;
    }

    public NavSelectDialog setTitleStr(String titleStr) {
        this.titleStr = titleStr;
        return this;
    }

    public NavSelectDialog setOnItemClick(NavOnItemClick onItemClick) {
        mOnItemClick = onItemClick;
        return this;
    }

    public NavSelectDialog setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public NavSelectDialog setWidth(int width) {
        this.width = width;
        return this;
    }

    public NavSelectDialog setHeight(int height) {
        this.height = height;
        return this;
    }

    public DialogPlus initDialog() {
        View header = View.inflate(mContext, R.layout.cdqj_patrol_dialog_plus_head,null);
        TextView title = header.findViewById(R.id.dialog_plus_item_title);
        Button btEsc = header.findViewById(R.id.dialog_plus_item_esc);
        Button btSub = header.findViewById(R.id.dialog_plus_item_submit);
        title.setText(titleStr);
        // 只需要提示
        btEsc.setVisibility(View.GONE);
        btSub.setVisibility(View.GONE);
        dialog = DialogPlus.newDialog(mContext)
                .setHeader(header)
                .setContentWidth(width)
                .setContentHeight(height)
                .setPadding(ConvertUtils.dp2px(10), ConvertUtils.dp2px(15),
                        ConvertUtils.dp2px(10), ConvertUtils.dp2px(15))
                .setContentBackgroundResource(R.drawable.stroke_bg_radius_4)
                .setAdapter(new DialogGridPicAdapter(mContext, windowData))
                .setContentHolder(new GridHolder(windowData.size()>3?3:windowData.size()))
                .setGravity(gravity)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        dialog.dismiss();
                        if (ObjectUtils.isEmpty(mOnItemClick)) {
                            return;
                        }
                        mOnItemClick.onItemClick(dialog, item, view, position);
                    }
                }).create();
        return dialog;
    }

    public interface NavOnItemClick{
        /**
         * 监听接口
         * @param dialog dialog
         * @param item item
         * @param view view
         * @param position position
         */
        void onItemClick(DialogPlus dialog, Object item, View view, int position);
    }
}
