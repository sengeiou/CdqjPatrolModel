package com.cdqj.cdqjpatrolandroid.comstomview.dialogplus;


import android.widget.BaseAdapter;

/**
 * Created by lyf on 2018/5/16 11:20
 *
 * @author lyf
 * desc：
 */
public abstract class CheckDialogBaseAdapter extends BaseAdapter {

    int position = -1;

    public void setPosition(int position) {
        this.position = position;
        notifyDataSetChanged();
    }

    boolean isSingle;

    public void setSingleFlag(boolean isSingle) {
        this.isSingle = isSingle;
    }
}
