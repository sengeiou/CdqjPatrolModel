package com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.cdqj.cdqjpatrolandroid.comstomview.MaxListView;
import com.cdqj.cdqjpatrolandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyf on 2017/9/29.
 * 自定义下拉样式(筛选)
 */
public class SelectSpinnerPopWindow extends PopupWindow  implements AdapterView.OnItemClickListener {

    private Context mContext;
    private int position;
    private View view;
    private AbsListView mListView;
    private TextView btn;
    private BaseSpinnerAdapter mAdapter;
    private SpinnerBuild.IOnItemSelectListener mItemSelectListener;
    private boolean isListView, isSingle;
    private List<SelectSpinnerBean> mList;
    private List<SelectSpinnerBean> selectList;

    public SelectSpinnerPopWindow(Context context, int position, boolean isListView, boolean isSingle, List<SelectSpinnerBean> mList)  {
        super(context);
        mContext = context;
        this.position = position;
        this.isListView = isListView;
        this.isSingle = isSingle;
        this.mList = mList;
        init();
    }


    public void setItemListener(SpinnerBuild.IOnItemSelectListener listener){
        mItemSelectListener = listener;
    }

    public void setAdapter(BaseSpinnerAdapter adapter){
        mAdapter = adapter;
        mAdapter.setSingleFlag(isSingle);
        mListView.setAdapter(mAdapter);
    }

    /**
     * 设置最大高度
     * @param height 单位dp
     */
    public void setListMaxHeight(int height) {
        if (mListView instanceof MaxListView) {
            ((MaxListView)mListView).setListViewHeight(height == 0?ConvertUtils.dp2px(200):ConvertUtils.dp2px(height));
        }
    }


    @SuppressLint("InflateParams")
    private void init() {
        if (isListView) {
            view = LayoutInflater.from(mContext).inflate(R.layout.cdqj_patrol_select_spinner_window_layout, null);
            mListView = view.findViewById(R.id.select_spinner_window_listView);
            ((MaxListView)mListView).setListViewHeight(ConvertUtils.dp2px(200));
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.cdqj_patrol_spinner_window_layout, null);
            mListView = view.findViewById(R.id.spinner_window_gridView);
        }
        btn = view.findViewById(R.id.spinner_window_btn);
        if (isSingle) {
            btn.setVisibility(View.GONE);
        } else {
            btn.setVisibility(View.VISIBLE);
        }
        btn.setOnClickListener(v -> {
            dismiss();
            mItemSelectListener.onReView();
            selectList = new ArrayList<>();
            for (int i=0;i < mList.size();i ++) {
                if (mList.get(i).isSelect()) {
                    selectList.add(mList.get(i));
                }
            }
            mItemSelectListener.onSubmitClick(position, selectList);
        });
        mListView.setOnItemClickListener(this);
        setPopupWindow();
    }


    public void refreshData(List<SelectSpinnerBean> list, int selIndex) {
        if (list != null && selIndex  != -1){
            if (mAdapter != null){
                mAdapter.refreshData(list, selIndex);
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
        if (isSingle) {
            dismiss();
            mItemSelectListener.onReView();
            if (mItemSelectListener != null){
                mItemSelectListener.onItemClick(position, pos, mList);
            }
        } else {
            mList.get(pos).setSelect(!mList.get(pos).isSelect());
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint({"InlinedApi", "ClickableViewAccessibility"})
    private void setPopupWindow() {
        // 设置View
        this.setContentView(view);
        // 设置弹出窗口的宽
        this.setWidth(ScreenUtils.getScreenWidth());
        // 设置弹出窗口不可以关闭
        this.setFocusable(false);
        // 设置动画
        // this.setAnimationStyle(R.style.pop_window_anim_style_top);
        // 设置背景透明
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));
        // 如果触摸位置在窗口外面则销毁
        view.setOnTouchListener((v, event) -> {
            int height = mListView.getBottom();
            int y = (int) event.getY();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (y > height) {
                    dismiss();
                    mItemSelectListener.onReView();
                }
            }
            return true;
        });
    }

    public void isShowAndDismiss() {
       if (isShowing()) {
           dismiss();
       }
    }

    public void setWindowHeight(int height) {
        // 设置弹出窗口的高
        this.setHeight(height);
    }
}
