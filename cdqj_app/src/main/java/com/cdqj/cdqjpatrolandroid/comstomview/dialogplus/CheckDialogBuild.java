package com.cdqj.cdqjpatrolandroid.comstomview.dialogplus;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnDismissListener;

import java.util.List;

public class CheckDialogBuild {
    Context context;
    List data;
    OnDismissListener dismissListener;
    OnCancelListener cancelListener;
    boolean expanded;
    String title;
    OnCheckDialogSelectListener checkDialogSelect;
    boolean isSingle;
    boolean isSingleTextHeader;
    Holder holder;
    CheckDialogBaseAdapter adapter;
    View header;
    int mGravity = Gravity.BOTTOM;

    public CheckDialogBuild setHolder(Holder holder) {
        this.holder = holder;
        return this;
    }

    public CheckDialogBuild setSingleTextHeader(Boolean isSingleTextHeader) {
        this.isSingleTextHeader = isSingleTextHeader;
        return this;
    }

    public CheckDialogBuild setSingle(Boolean single) {
        isSingle = single;
        return this;
    }

    public CheckDialogBuild setTitle(String title) {
        this.title = title;
        return this;
    }

    public CheckDialogBuild setTitle(int mGravity) {
        this.mGravity = mGravity;
        return this;
    }

    public CheckDialogBuild setCheckDialogSelect(OnCheckDialogSelectListener checkDialogSelect) {
        this.checkDialogSelect = checkDialogSelect;
        return this;
    }

    public CheckDialogBuild setExpanded(Boolean expanded) {
        this.expanded = expanded;
        return this;
    }

    public CheckDialogBuild(Context context, List data) {
        this.context = context;
        this.data = data;
    }

    public CheckDialogBuild setDismissListener(OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }

    public CheckDialogBuild setCancelListener(OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
        return this;
    }

    public CheckDialogBuild setBaseAdapter(CheckDialogBaseAdapter adapter) {
        this.adapter = adapter;
        return this;
    }

    public CheckDialogBuild setHeader(View header) {
        this.header = header;
        return this;
    }

    public CheckDialog creatDialog(){
        return new CheckDialog(this);
    }
}