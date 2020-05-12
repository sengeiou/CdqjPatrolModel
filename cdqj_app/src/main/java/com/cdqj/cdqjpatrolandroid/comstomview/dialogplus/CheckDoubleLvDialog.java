package com.cdqj.cdqjpatrolandroid.comstomview.dialogplus;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.utils.DataUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyf on 2018/7/27 15:51
 *
 * @author lyf
 * desc：  二级菜单 一级单选 二级多选
 */
public class CheckDoubleLvDialog {
    private List selectAll = new ArrayList<>();
    private List selectAllPosition = new ArrayList<>();
    private int singleGroupPosition = 0;
    private int singleChildPosition = 0;
    private int gorupPosition = 0;
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
    private OnDoubuleSelectListener checkDialogSelect;
    private DialogPlus dialog;
    private CheckDialogBaseAdapter adapter;
    private List<String> item1;
    private List<ArrayList<String>> item2;
    /**
     * 头部,默认带有取消及确认
     */
    private View header;
    private int mGravity;

    public CheckDoubleLvDialog(CheckDoubleLvDialogBuild checkDialogBuild) {
        context = checkDialogBuild.context;
        data = checkDialogBuild.data;
        item1 = checkDialogBuild.item1;
        item2 = checkDialogBuild.item2;
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
            header = View.inflate(context, R.layout.cdqj_patrol_dialog_plus_head, null);
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
        adapter.setSingleFlag(true);
        View pickerContent = View.inflate(context, R.layout.cdqj_patrol_dialog_plus_double_lv, null);
        ListView leftLv = pickerContent.findViewById(R.id.doubule_lv_left);
        final ListView rightLv = pickerContent.findViewById(R.id.doubule_lv_right);
        leftLv.setAdapter(adapter);
        leftLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                final DialogAdapter adapter1 = new DialogAdapter(item2.get(position), context);
                adapter1.setSingleFlag(isSingle);
                gorupPosition = position;
                adapter.setPosition(position);
                rightLv.setAdapter(adapter1);
                rightLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (isSingle) {
                            if (i != -1) {
                                adapter1.setPosition(i);
                                singleGroupPosition = position;
                                singleChildPosition = i;
                                if (isSingleTextHeader) {
                                    // 点击即执行后续监听操作
                                    checkDialogSelect.onSingleSelect(singleGroupPosition, singleChildPosition);
                                    dismiss();
                                }
                            }
                        } else {
                            ImageView imgView = view.findViewById(R.id.dialog_item_true);
                            if (imgView.getVisibility() == View.VISIBLE) {
                                imgView.setVisibility(View.GONE);
                                selectAll.remove(item2.get(position).get(i));
                                selectAllPosition.remove(i + "");
                            } else {
                                imgView.setVisibility(View.VISIBLE);
                                selectAll.add(item2.get(position).get(i));
                                selectAllPosition.add(i + "");
                            }
                            titleTv.setText(DataUtils.listToString(selectAll));
                            adapter1.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
        dialog = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(pickerContent))
                .setCancelable(true)
                .setHeader(header)
                .setGravity(mGravity)
                .setAdapter(adapter)
//                .setMargin(0, BarUtils.getStatusBarHeight(),0,0)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        if (checkDialogSelect != null) {
                            int i = view.getId();
                            if (i == R.id.dialog_plus_item_submit) {
                                if (isSingle) {
                                    checkDialogSelect.onSingleSelect(singleGroupPosition, singleChildPosition);
                                } else {
                                    checkDialogSelect.onDoubuleSelect(selectAll, selectAllPosition, gorupPosition);
                                }
                                dialog.dismiss();
                            } else if (i == R.id.dialog_plus_item_esc) {
                                dialog.dismiss();
                            }
                        }
                    }
                })
                .setOnDismissListener(dismissListener)
                .setExpanded(expanded)
                // .setContentWidth(800)
                .setContentHeight(ScreenUtils.getScreenHeight() / 2)
                .setOnCancelListener(cancelListener)
                // .setOverlayBackgroundResource(isBackground ? 0 : android.R.color.transparent)
                // 无阴影背景
                // .setContentBackgroundResource(R.drawable.corner_background)
                // .setOutMostMargin(0, 100, 0, 0)
                .create();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public static CheckDoubleLvDialogBuild build(Context context, List<String> item1, List<ArrayList<String>> item2) {
        return new CheckDoubleLvDialogBuild(context, item1, item2);
    }

    public void show() {
        dialog.show();
    }

    public interface OnDoubuleSelectListener {
        void onDoubuleSelect(List titles, List positions, int gourpPosition);

        void onSingleSelect(int gourpPosition, int childPosition);
    }
}
