package com.cdqj.cdqjpatrolandroid.bean;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by lyf on 2018/7/24 16:47
 *
 * @author lyf
 * desc：首页底部导航
 */
public class TabEntity implements CustomTabEntity {

    /**
     * 标题
     */
    public String title;

    /**
     * 选中图标
     */
    public int selectedIcon;

    /**
     * 未选中图标
     */
    public int unSelectedIcon;

    public TabEntity(String title) {
        this.title = title;
    }

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
