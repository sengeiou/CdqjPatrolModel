package com.cdqj.cdqjpatrolandroid.bean;

import com.cdqj.cdqjpatrolandroid.adapter.MapSearchListAdapter;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by lyf on 2018/8/21 15:50
 *
 * @author lyf
 * desc：
 */
public class GridSelectBean extends AbstractExpandableItem<MapSearchBean> implements MultiItemEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * 选中图标
     */
    private int selectedIcon;

    /**
     * 未选中图标
     */
    private int unSelectedIcon;

    /**
     * 是否选中
     */
    private boolean selectStatus;

    /**
     * 值
     */
    private String value;

    public GridSelectBean() {
        super();
    }

    public GridSelectBean(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public GridSelectBean(String title, int selectedIcon, int unSelectedIcon, boolean selectStatus) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
        this.selectStatus = selectStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSelectedIcon() {
        return selectedIcon;
    }

    public void setSelectedIcon(int selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    public int getUnSelectedIcon() {
        return unSelectedIcon;
    }

    public void setUnSelectedIcon(int unSelectedIcon) {
        this.unSelectedIcon = unSelectedIcon;
    }

    public boolean isSelectStatus() {
        return selectStatus;
    }

    public void setSelectStatus(boolean selectStatus) {
        this.selectStatus = selectStatus;
    }

    @Override
    public int getItemType() {
        return MapSearchListAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getLevel() {
        return MapSearchListAdapter.TYPE_LEVEL_0;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
