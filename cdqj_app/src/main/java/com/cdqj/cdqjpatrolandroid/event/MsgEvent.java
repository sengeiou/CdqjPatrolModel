package com.cdqj.cdqjpatrolandroid.event;

/**
 * 作者    Mr.Jin
 * 时间    2018/11/30 11:10
 * 文件    YBFirecontrol
 * 描述
 */
public class MsgEvent<T> {

    /**
     * 界面
     * 1 工单
     * 2 计划
     * 3.首页
     * 4.计划加载地图元素完成
     */
    private int tag;

    /**
     * 是否刷新
     */
    private boolean isRefresh;

    /**
     * 传递的数据
     */
    private T data;

    public MsgEvent(int tag) {
        this.tag = tag;
    }

    public MsgEvent(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public MsgEvent(int tag, boolean isRefresh) {
        this.tag = tag;
        this.isRefresh = isRefresh;
    }

    public MsgEvent(int tag, T data) {
        this.tag = tag;
        this.data = data;
    }

    public MsgEvent(int tag, boolean isRefresh, T data) {
        this.tag = tag;
        this.isRefresh = isRefresh;
        this.data = data;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
