package com.cdqj.cdqjpatrolandroid.bean;

import java.util.List;

/**
 * Created by lyf on 2018/10/29 19:36
 *
 * @author lyf
 * desc：
 */
public class ResultMapSearchBean {

    private List<SiteBean> site;
    private List<UserLayerBean> users;
    private List<HdOrderBean> hidden;
    private List<DeviceBean> device;

    public List<SiteBean> getSite() {
        return site;
    }

    public void setSite(List<SiteBean> site) {
        this.site = site;
    }

    public List<UserLayerBean> getUsers() {
        return users;
    }

    public void setUsers(List<UserLayerBean> users) {
        this.users = users;
    }

    public List<HdOrderBean> getHidden() {
        return hidden;
    }

    public void setHidden(List<HdOrderBean> hidden) {
        this.hidden = hidden;
    }

    public List<DeviceBean> getDevice() {
        return device;
    }

    public void setDevice(List<DeviceBean> device) {
        this.device = device;
    }
}
