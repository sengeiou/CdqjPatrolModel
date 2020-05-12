package com.cdqj.cdqjpatrolandroid.comstomview.dialogplus;

/**
 * Created by lyf on 2018/2/2.
 * 布局bean
 */
public class CustomGridPicWindowBean {

    private int resourceId;
    private String title;
    /**
     * 是否选中
     */
    private boolean isSelect;

    public CustomGridPicWindowBean() {
    }

    public CustomGridPicWindowBean(int resourceId, String title) {
        this.resourceId = resourceId;
        this.title = title;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
