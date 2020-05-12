package com.cdqj.cdqjpatrolandroid.comstomview.dialogplus;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.utils.DataUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnDismissListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyf on 2018/7/27 15:51
 *
 * @author lyf
 * desc：  多选以及单选框 可以选择是否可以展开收缩
 *
 */
public class CheckDialog {
    private List selectAll = new ArrayList<>();
    private List selectAllPosition = new ArrayList<>();
    private int singlePosition = 0;
    private boolean isSingleTextHeader;
    /**
     * 网格布局或者ListView布局，默认ListView
     */
    private Holder holder;
    private TextView titleTv;
    private Context context;
    /**
     * 默认标题内容
     */
    private String title;
    private List data;
    private OnDismissListener dismissListener;
    private OnCancelListener cancelListener;
    private boolean expanded;
    private boolean isSingle;
    private OnCheckDialogSelectListener checkDialogSelect;
    private DialogPlus dialog;
    private CheckDialogBaseAdapter adapter;
    /**
     * 头部,默认带有取消及确认
     */
    private View header;
    private int mGravity;

    public CheckDialog(CheckDialogBuild checkDialogBuild) {
        context = checkDialogBuild.context;
        data = checkDialogBuild.data;
        isSingle = checkDialogBuild.isSingle;
        dismissListener = checkDialogBuild.dismissListener;
        cancelListener = checkDialogBuild.cancelListener;
        expanded = checkDialogBuild.expanded;
        title = checkDialogBuild.title;
        checkDialogSelect = checkDialogBuild.checkDialogSelect;
        adapter = checkDialogBuild.adapter;
        header = checkDialogBuild.header;
        isSingleTextHeader = checkDialogBuild.isSingleTextHeader;
        holder = checkDialogBuild.holder;
        mGravity = checkDialogBuild.mGravity;
        initDialog();
    }

    private void initDialog() {
        if (header == null) {
            header = View.inflate(context, R.layout.cdqj_patrol_dialog_plus_head,null);
            titleTv = header.findViewById(R.id.dialog_plus_item_title);
            Button btEsc = header.findViewById(R.id.dialog_plus_item_esc);
            Button btSub = header.findViewById(R.id.dialog_plus_item_submit);
            if (!isSingleTextHeader) {
                btEsc.setVisibility(View.VISIBLE);
                btSub.setVisibility(View.VISIBLE);
                titleTv.setText(title);
            } else {
                // 只需要提示
                btEsc.setVisibility(View.GONE);
                btSub.setVisibility(View.GONE);
                titleTv.setText(title);
            }
        }
        adapter.setSingleFlag(isSingle);
        dialog = DialogPlus.newDialog(context)
                .setContentHolder(holder==null?new ListHolder():holder)
                .setCancelable(true)
                .setHeader(header)
                .setGravity(mGravity)
                .setAdapter(adapter)
                .setOnClickListener((dialog, view) -> {
                    if (checkDialogSelect != null) {
                        int i = view.getId();
                        if (i == R.id.dialog_plus_item_submit) {
                            if (isSingle) {
                                checkDialogSelect.onCheckSingle(singlePosition);
                            } else {
                                checkDialogSelect.onCheckSelect(selectAll, selectAllPosition);
                            }
                            dialog.dismiss();

                        } else if (i == R.id.dialog_plus_item_esc) {
                            dialog.dismiss();

                        }
                    }
                })
                .setOnItemClickListener((dialog, item, view, position) -> {
                    if (isSingle) {
                        if (position!= -1) {
                            adapter.setPosition(position);
                            adapter.notifyDataSetChanged();
                            singlePosition = position;
                            if (isSingleTextHeader) {
                                // 点击即执行后续监听操作
                                checkDialogSelect.onItemClickListener(singlePosition);
                                dismiss();
                            }
                        }
                    } else {
                        ImageView imgView = view.findViewById(R.id.dialog_item_true);
                        if (imgView.getVisibility() == View.VISIBLE) {
                            imgView.setVisibility(View.GONE);
                            selectAll.remove(data.get(position));
                            selectAllPosition.remove(position+"");
                        } else {
                            imgView.setVisibility(View.VISIBLE);
                            selectAll.add(data.get(position));
                            selectAllPosition.add(position+"");
                        }
                        titleTv.setText(DataUtils.listToString(selectAll));
                        adapter.notifyDataSetChanged();
                    }
                })
                .setOnDismissListener(dismissListener)
                .setExpanded(expanded)
                // .setContentWidth(800)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnCancelListener(cancelListener)
                // .setOverlayBackgroundResource(isBackground ? 0 : android.R.color.transparent)
                // 无阴影背景
                // .setContentBackgroundResource(R.drawable.corner_background)
                // .setOutMostMargin(0, 100, 0, 0)
                .create();
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public static CheckDialogBuild build(Context context, List data) {
        return new CheckDialogBuild(context, data);
    }
}
