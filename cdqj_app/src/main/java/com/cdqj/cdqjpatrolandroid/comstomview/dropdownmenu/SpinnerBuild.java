package com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu;


import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;

import java.util.List;

/**
 * Created by lyf on 2017/9/29.
 * 筛选框构建类
 * @author lyf
 * @date 2018年7月30日 19:09:38
 */
public class SpinnerBuild {

    private static SpinnerBuild mInstance;

    private SelectSpinnerPopWindow mPopWindow;
    private Context mContext;
    private List<SelectSpinnerBean> mList;
    /**
     * 监听位置
     */
    private int position;
    /**
     * 上一个监听控件位置
     */
    private int lastPosition;
    /**
     * 相对布局
     */
    private View showView;
    /**
     * 父界面
     */
    private View parent;
    /**
     * 默认展示item 默认0
     */
    private int defIndex;
    /**
     * 方向 1上，2左，3下，4右 默认3
     */
    private int direction;
    /**
     * 布局位置 默认下
     */
    private int gravity;
    /**
     * ListViw最大高度/单位dp  默认200dp
     */
    private int listMaxHeight;

    /**
     * 是否单个显示（是否显示多个等） 默认是
     */
    private boolean isSingle;

    /**
     * 是否列表/网格
     */
    private boolean isListView;

    private IOnItemSelectListener mItemSelectListener;

    private BaseSpinnerAdapter mAdapter;

    public SpinnerBuild() {
    }

    private SpinnerBuild(Context context, List<SelectSpinnerBean> list, int position, View showView, View parent,
                         IOnItemSelectListener mItemSelectListener) {
        mContext = context;
        mList = list;
        this.position = position;
        this.showView = showView;
        this.parent = parent;
        this.mItemSelectListener = mItemSelectListener;
        defIndex = 0;
        direction = 3;
        isSingle = true;
        isListView = true;
        gravity = Gravity.START | Gravity.TOP;
    }

    /**
     * 单例模式（解决Pop重复弹出的问题）
     * @param context 上下文
     * @param list 数据
     * @param position 点击位置
     * @param showView 相对显示控件布局
     * @param parent 父界面
     * @param mItemSelectListener 监听
     * @return SpinnerBuild
     */
    public SpinnerBuild build(Context context, List<SelectSpinnerBean> list, int position, View showView, View parent,
                                           IOnItemSelectListener mItemSelectListener) {
        /*for (int i = 0;i < list.size();i ++) {
            if (list.get(i).isSelect()) {
                list.get(i).setSelect(!list.get(i).isSelect());
            }
        }*/
        if (mInstance == null) {
            synchronized (SpinnerBuild.class) {
                mInstance = new SpinnerBuild(context, list, position, showView, parent, mItemSelectListener);
            }
        } else {
            mContext = context;
            mList = list;
            this.position = position;
            this.showView = showView;
            this.parent = parent;
            this.mItemSelectListener = mItemSelectListener;
            defIndex = 0;
            direction = 3;
            isSingle = true;
            isListView = true;
            gravity = Gravity.START | Gravity.TOP;
            mInstance.setContext(context);
            mInstance.setPosition(position);
            mInstance.setParent(parent);
            mInstance.setShowView(showView);
            mInstance.setList(list);
            mInstance.setItemSelectListener(mItemSelectListener);
            mInstance.setDefIndex(defIndex);
            mInstance.setDirection(direction);
            mInstance.setSingle(isSingle);
            mInstance.setListView(isListView);
            mInstance.setGravity(gravity);
        }

        return mInstance;
    }

    /**
     * 获取Pop进行显示设置
     * @return SelectSpinnerPopWindow
     */
    public SelectSpinnerPopWindow getPopWindow() {
        return mPopWindow;
    }

    public boolean isListView() {
        return isListView;
    }

    public SpinnerBuild setListView(boolean listView) {
        isListView = listView;
        return this;
    }

    public Context getContext() {
        return mContext;
    }

    public SpinnerBuild setContext(Context context) {
        mContext = context;
        return this;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public SpinnerBuild setSingle(boolean single) {
        isSingle = single;
        return this;
    }

    public List<SelectSpinnerBean> getList() {
        return mList;
    }

    public SpinnerBuild setList(List<SelectSpinnerBean> list) {
        mList = list;
        return this;
    }

    public int getPoint() {
        return position;
    }

    public SpinnerBuild setPosition(int position) {
        this.position = position;
        return this;
    }

    public View getShowView() {
        return showView;
    }

    public SpinnerBuild setShowView(View showView) {
        this.showView = showView;
        return this;
    }

    public View getParent() {
        return parent;
    }

    public SpinnerBuild setParent(View parent) {
        this.parent = parent;
        return this;
    }

    public int getDefIndex() {
        return defIndex;
    }

    public SpinnerBuild setDefIndex(int defIndex) {
        this.defIndex = defIndex;
        return this;
    }

    public int getDirection() {
        return direction;
    }

    public SpinnerBuild setDirection(int direction) {
        this.direction = direction;
        return this;
    }

    public int getGravity() {
        return gravity;
    }

    public SpinnerBuild setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }
    public SpinnerBuild setListMaxHeight(int listMaxHeight) {
        this.listMaxHeight = listMaxHeight;
        return this;
    }

    public SpinnerBuild setItemSelectListener(IOnItemSelectListener itemSelectListener) {
        mItemSelectListener = itemSelectListener;
        return this;
    }

    public boolean isShow() {
        if (ObjectUtils.isNotEmpty(mPopWindow)
                && mPopWindow.isShowing()) {
            return true;
        }
        return false;
    }

    public void isShowAndDismiss() {
        mPopWindow.isShowAndDismiss();
    }

    public IOnItemSelectListener getItemSelectListener() {
        return mItemSelectListener;
    }

    /**
     * 显示弹窗
     */
    public void showPop() {
        if (ObjectUtils.isNotEmpty(mPopWindow)) {
            if (lastPosition == position && mPopWindow.isShowing()) {
                mPopWindow.isShowAndDismiss();
                return;
            }
            mPopWindow.isShowAndDismiss();
        }
        if (isListView) {
            mAdapter = new SpinnerAdapter(mContext, mList);
        } else {
            mAdapter = new SpinnerGridAdapter(mContext, mList);
        }
        //初始化PopWindow
        mPopWindow = new SelectSpinnerPopWindow(mContext, position, isListView, isSingle, mList);
        mPopWindow.refreshData(mList, defIndex);
        mPopWindow.setListMaxHeight(listMaxHeight);
        mPopWindow.setAdapter(mAdapter);
        mPopWindow.setItemListener(mItemSelectListener);
        int[] location = new int[2];
        showView.getLocationOnScreen(location);
        int xOrY = direction == 1 ? location[1] :
                (direction == 2 ? location[0] :
                (direction == 3 ? location[1] + showView.getHeight() :
                        (direction == 4 ? location[1] + showView.getWidth() : location[1] + showView.getHeight())));
        mPopWindow.setWindowHeight(direction==2||direction==3?ScreenUtils.getScreenHeight() - xOrY
                - (BarUtils.isNavBarVisible((Activity) mContext)?BarUtils.getNavBarHeight():0)
                :ScreenUtils.getScreenHeight());
        mPopWindow.setWidth(direction==1||direction==4?ScreenUtils.getScreenWidth() - xOrY:ScreenUtils.getScreenWidth());
        mPopWindow.showAtLocation(parent, gravity, direction==1||direction==4?xOrY:0, direction==2||direction==3?xOrY:0);
        lastPosition = position;
    }


    public interface IOnItemSelectListener{

        /**
         * 单选item点击事件
         * @param position position 点击控件位置
         * @param pos pos 位置
         * @param spinnerBeanList spinnerBeanList 数据
         */
        void onItemClick(int position, int pos, List<SelectSpinnerBean> spinnerBeanList);

        /**
         * 多选button点击事件
         * @param position position 点击控件
         * @param spinnerBeanList spinnerBeanList 数据
         */
        void onSubmitClick(int position, List<SelectSpinnerBean> spinnerBeanList);

        /**
         * 界面更新
         */
        void onReView();
    }

}
