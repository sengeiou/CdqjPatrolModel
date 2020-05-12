package com.cdqj.cdqjpatrolandroid.event;

public class CenterEvent {

    private boolean isRefresh;

    public CenterEvent(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }
}
