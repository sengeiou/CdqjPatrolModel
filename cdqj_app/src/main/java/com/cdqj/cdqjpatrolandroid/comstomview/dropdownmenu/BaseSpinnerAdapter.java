package com.cdqj.cdqjpatrolandroid.comstomview.dropdownmenu;

import android.widget.BaseAdapter;


import java.util.List;

/**
 * Created by lyf on 2017/9/29.
 *
 */
public abstract class BaseSpinnerAdapter extends BaseAdapter{

    private List<SelectSpinnerBean> mObjects;


    public int position = -1;

    public boolean isSingle;

    public void setPosition(int position) {
        this.position = position;
    }

    public void setSingleFlag(boolean isSingle) {
        this.isSingle = isSingle;
    }

    public void refreshData(List<SelectSpinnerBean> objects, int selIndex){
        mObjects = objects;
        if (selIndex < 0){
            selIndex = 0;
        }
        if (selIndex >= mObjects.size()){
            selIndex = mObjects.size() - 1;
        }
    }
}
