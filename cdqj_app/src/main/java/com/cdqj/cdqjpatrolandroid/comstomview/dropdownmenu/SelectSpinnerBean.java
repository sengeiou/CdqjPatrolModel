package com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu;

/**
 * Created by lyf on 2018/2/2.
 * 布局bean
 */
public class SelectSpinnerBean {

    private boolean isSelect;
    private String title;
    private String value;

    public SelectSpinnerBean() {
    }

    public SelectSpinnerBean(boolean isSelect, String title, String value) {
        this.isSelect = isSelect;
        this.title = title;
        this.value = value;
    }

    public SelectSpinnerBean(boolean isSelect, String title) {
        this.isSelect = isSelect;
        this.title = title;
    }

    public SelectSpinnerBean(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public SelectSpinnerBean(String title) {
        this.title = title;
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

}
