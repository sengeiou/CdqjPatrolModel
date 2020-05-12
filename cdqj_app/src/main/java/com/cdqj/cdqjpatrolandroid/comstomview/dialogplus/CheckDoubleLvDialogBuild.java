package com.cdqj.cdqjpatrolandroid.comstomview.dialogplus;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnDismissListener;

import java.util.ArrayList;
import java.util.List;

public class CheckDoubleLvDialogBuild {
    Context context;
    List data;
    List item1;
    List item2;
    OnDismissListener dismissListener;
    OnCancelListener cancelListener;
    boolean expanded;
    String title;
    CheckDoubleLvDialog.OnDoubuleSelectListener checkDialogSelect;
    boolean isSingle;
    boolean isSingleTextHeader;
    Holder holder;
    CheckDialogBaseAdapter adapter;
    View header;
    int mGravity = Gravity.BOTTOM;

    public CheckDoubleLvDialogBuild setHolder(Holder holder) {
        this.holder = holder;
        return this;
    }

    public CheckDoubleLvDialogBuild setSingleTextHeader(Boolean isSingleTextHeader) {
        this.isSingleTextHeader = isSingleTextHeader;
        return this;
    }

    public CheckDoubleLvDialogBuild setSingle(Boolean single) {
        isSingle = single;
        return this;
    }

    public CheckDoubleLvDialogBuild setTitle(String title) {
        this.title = title;
        return this;
    }

    public CheckDoubleLvDialogBuild setGravity(int mGravity) {
        this.mGravity = mGravity;
        return this;
    }

    public CheckDoubleLvDialogBuild setCheckDialogSelect(CheckDoubleLvDialog.OnDoubuleSelectListener checkDialogSelect) {
        this.checkDialogSelect = checkDialogSelect;
        return this;
    }

    public CheckDoubleLvDialogBuild setExpanded(Boolean expanded) {
        this.expanded = expanded;
        return this;
    }

    public CheckDoubleLvDialogBuild(Context context, List<String> item1,List<ArrayList<String>> item2) {
        this.context = context;
        this.item1 = item1;
        this.item2 = item2;
    }

    public CheckDoubleLvDialogBuild setDismissListener(OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }

    public CheckDoubleLvDialogBuild setCancelListener(OnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
        return this;
    }

    public CheckDoubleLvDialogBuild setBaseAdapter(CheckDialogBaseAdapter adapter) {
        this.adapter = adapter;
        return this;
    }

    public CheckDoubleLvDialogBuild setHeader(View header) {
        this.header = header;
        return this;
    }

    public CheckDoubleLvDialog creatDialog(){
        return new CheckDoubleLvDialog(this);
    }
}