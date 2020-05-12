package com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu;

import java.util.List;

/**
 * Created by lyf on 2018/10/24 16:16
 *
 * @author lyf
 * desc：查询筛选实体类
 */
public class SearchTabBean {

    /**
     * 是否单个显示（是否显示多个等） 默认是
     */
    private boolean isSingle = true;

    /**
     * 是否列表/网格
     */
    private boolean isListView = true;
    private boolean isSelect;
    private String title;
    private String value;
    private List<SelectSpinnerBean> mBeans;

    public SearchTabBean() {
    }

    public SearchTabBean(boolean isSelect, String title, String value) {
        this.isSelect = isSelect;
        this.title = title;
        this.value = value;
    }

    public SearchTabBean(boolean isSelect, String title) {
        this.isSelect = isSelect;
        this.title = title;
    }

    public SearchTabBean(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public SearchTabBean(String title) {
        this.title = title;
    }

    public SearchTabBean(String title, String value, List<SelectSpinnerBean> beans) {
        this.title = title;
        this.value = value;
        mBeans = beans;
    }

    public SearchTabBean(String title, List<SelectSpinnerBean> beans) {
        this.title = title;
        mBeans = beans;
    }

    public SearchTabBean(boolean isSingle, boolean isListView, String title, List<SelectSpinnerBean> beans) {
        this.isSingle = isSingle;
        this.isListView = isListView;
        this.title = title;
        mBeans = beans;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<SelectSpinnerBean> getBeans() {
        return mBeans;
    }

    public void setBeans(List<SelectSpinnerBean> beans) {
        mBeans = beans;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }

    public boolean isListView() {
        return isListView;
    }

    public void setListView(boolean listView) {
        isListView = listView;
    }
}
