package com.cdqj.cdqjpatrolandroid.bean;

/**
 * Created by lyf on 2018/11/14 13:52
 *
 * @author lyf
 * desc：
 */
public class MainGridBean {

    private int urlResId;
    private String name;
    private Class activity;

    public MainGridBean() {
    }

    public MainGridBean(String name, Class activity) {
        this.name = name;
        this.activity = activity;
    }

    public MainGridBean(int urlResId, String name, Class activity) {
        this.urlResId = urlResId;
        this.name = name;
        this.activity = activity;
    }

    public int getUrlResId() {
        return urlResId;
    }

    public void setUrlResId(int urlResId) {
        this.urlResId = urlResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "MainGridBean{" +
                "urlResId=" + urlResId +
                ", name='" + name + '\'' +
                ", activity=" + activity +
                '}';
    }
}
