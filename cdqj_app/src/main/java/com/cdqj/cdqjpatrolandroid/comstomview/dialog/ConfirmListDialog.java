package com.cdqj.cdqjpatrolandroid.comstomview.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.comstomview.MaxListView;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.bean.GridSelectBean;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyf on 2018/10/30 10:15
 *
 * @author lyf
 * desc：
 */
public class ConfirmListDialog {


    private DialogPlus dialog;
    private Context mContext;
    /**
     * 是否单选 默认是
     */
    private boolean isSingle = true;
    /**
     * 来源
     *      1.默认
     *      2.居中
     *      3.状态显示
     */
    private int flag = 1;
    /**
     * 是否搜索 默认否
     */
    private boolean isSearch;
    private String titleStr="提示";
    private String titleSearchStr ="请输入查询类容";
    /**
     * 数据
     */
    private List<GridSelectBean> mList;
    private List<GridSelectBean> selectList = new ArrayList<>();
    private Holder holder;

    private DialogAdapter mAdapter;
    private View header;
    private View footer;
    private int mGravity = Gravity.CENTER;

    private int inAnimResource;
    private int outAnimResource;

    /**
     * 已选数量
     */
    private int count;
    /**
     * 多选最多可选数量
     */
    private int maxCount = -1;

    private ConfirmListDialogListener mConfirmListDialogListener;
    private OnDismissListener dismissListener;

    public ConfirmListDialog(Context context) {
        mContext = context;
    }

    public ConfirmListDialog setTitleSearchStr(String titleSearchStr) {
        this.titleSearchStr = titleSearchStr;
        return this;
    }

    public ConfirmListDialogListener getConfirmListDialogListener() {
        return mConfirmListDialogListener;
    }

    public ConfirmListDialog setConfirmListDialogListener(ConfirmListDialogListener confirmListDialogListener) {
        mConfirmListDialogListener = confirmListDialogListener;
        return this;
    }

    public DialogPlus getDialog() {
        return dialog;
    }

    public ConfirmListDialog setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        return this;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public ConfirmListDialog setSingle(boolean single) {
        isSingle = single;
        return this;
    }

    public boolean isSearch() {
        return isSearch;
    }

    public ConfirmListDialog setSearch(boolean search) {
        isSearch = search;
        return this;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public ConfirmListDialog setTitleStr(String titleStr) {
        this.titleStr = titleStr;
        return this;
    }

    public List<GridSelectBean> getList() {
        return mList;
    }

    public ConfirmListDialog setList(List<GridSelectBean> list) {
        mList = list;
        return this;
    }

    public Holder getHolder() {
        return holder;
    }

    public ConfirmListDialog setHolder(Holder holder) {
        this.holder = holder;
        return this;
    }

    public DialogAdapter getAdapter() {
        return mAdapter;
    }

    public ConfirmListDialog setAdapter(DialogAdapter adapter) {
        mAdapter = adapter;
        return this;
    }

    public View getHeader() {
        return header;
    }

    public ConfirmListDialog setHeader(View header) {
        this.header = header;
        return this;
    }
    public ConfirmListDialog setDismissListener(OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }

    public View getFooter() {
        return footer;
    }

    public ConfirmListDialog setFooter(View footer) {
        this.footer = footer;
        return this;
    }

    public int getGravity() {
        return mGravity;
    }

    public ConfirmListDialog setGravity(int gravity) {
        mGravity = gravity;
        return this;
    }

    public int getInAnimResource() {
        return inAnimResource;
    }

    public ConfirmListDialog setInAnimResource(int inAnimResource) {
        this.inAnimResource = inAnimResource;
        return this;
    }

    public int getOutAnimResource() {
        return outAnimResource;
    }

    public ConfirmListDialog setFlag(int flag) {
        this.flag = flag;
        if (flag != 1) {
            isSingle = true;
        }
        return this;
    }

    public ConfirmListDialog setOutAnimResource(int outAnimResource) {
        this.outAnimResource = outAnimResource;
        return this;
    }

    @SuppressLint("InflateParams")
    private DialogPlus initView() {
        View viewMain = LayoutInflater.from(mContext).inflate(R.layout.cdqj_patrol_confirm_list_dialog, null);
        header = viewMain.findViewById(R.id.dialog_plus_header);
        footer = viewMain.findViewById(R.id.dialog_plus_footer);
        MaxListView listView = viewMain.findViewById(R.id.dialog_plus_list);

        listView.setListViewHeight(ScreenUtils.getScreenHeight()/5*3);
        footer.setVisibility(isSingle?View.GONE:View.VISIBLE);
        EditText mEditText = viewMain.findViewById(R.id.dialog_plus_search);
        TextView title = viewMain.findViewById(R.id.dialog_plus_title);
        mEditText.setVisibility(isSearch?View.VISIBLE:View.GONE);
        mEditText.setHint(titleSearchStr);
        title.setText(titleStr);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听  输入结束执行该方法 即文本框改变之后 会执行的动作
                selectList = new ArrayList<>();
                if (StringUtils.isTrimEmpty(s.toString())) {
                    selectList.clear();
                    mAdapter.setData(mList);
                    mAdapter.notifyDataSetChanged();
                } else {
                    selectList.addAll(mList);
                    for (int i = 0; i < selectList.size(); i++) {
                        if (!selectList.get(i).getTitle().toLowerCase().contains(s.toString().toLowerCase())) {
                            selectList.remove(i);
                            i--;
                        }
                    }
                    mAdapter.setData(selectList);
                }
            }
        });

        if (flag == 2) {
            mAdapter = new DialogAdapter(mContext ,R.layout.cdqj_patrol_dialog_plus_item_to, mList, isSingle, flag);
        } else if (flag == 3) {
            mAdapter = new DialogAdapter(mContext ,R.layout.cdqj_patrol_dialog_plus_item_three, mList, true, flag);
        } else {
            if (ObjectUtils.isEmpty(mAdapter)) {
                mAdapter = new DialogAdapter(mContext ,R.layout.cdqj_patrol_dialog_plus_item, mList, isSingle, flag);
            }
        }

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (isSingle) {
                // 单选 点击即监听
                if (position!= -1) {
                    if (ObjectUtils.isNotEmpty(mConfirmListDialogListener)) {
                        mConfirmListDialogListener.onItemClickListener(mAdapter.getData().get(position), position);
                    }
                    dialog.dismiss();
                }
            } else {
                if (maxCount != -1) {
                    if (count < maxCount) {
                        if (selectList.size()>0) {
                            GridSelectBean bean = selectList.get(position);
                            bean.setSelectStatus(!bean.isSelectStatus());
                            mAdapter.setData(selectList);
                        } else {
                            mList.get(position).setSelectStatus(!mList.get(position).isSelectStatus());
                            mAdapter.notifyDataSetChanged();
                        }
                        if (mList.get(position).isSelectStatus()) {
                            count++;
                        } else {
                            count--;
                        }
                    } else {
                        ToastBuilder.showLongWarning("当前最多只允许选择" + maxCount + "条数据");
                    }
                } else {
                    if (selectList.size()>0) {
                        GridSelectBean bean = selectList.get(position);
                        bean.setSelectStatus(!bean.isSelectStatus());
                        mAdapter.setData(selectList);
                    } else {
                        mList.get(position).setSelectStatus(!mList.get(position).isSelectStatus());
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        dialog = DialogPlus.newDialog(mContext)
                .setContentHolder(holder==null?new ViewHolder(viewMain):holder)
                .setCancelable(true)
                //.setHeader(header)
                //.setFooter(footer)
                .setGravity(mGravity)
                //.setAdapter(mAdapter)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentWidth(ScreenUtils.getScreenWidth()/5*4)
                .setContentBackgroundResource(R.drawable.stroke_bg_radius_4)
                .setInAnimation(inAnimResource==0? com.orhanobut.dialogplus.R.anim.fade_in_center:inAnimResource)
                .setOutAnimation(outAnimResource==0? com.orhanobut.dialogplus.R.anim.fade_out_center:outAnimResource)
                .setOnClickListener((dialog, view) -> {
                    int i = view.getId();
                    if (i == R.id.dialog_plus_item_esc) {
                        dialog.dismiss();
                    } else if (i == R.id.dialog_plus_item_submit) {
                        dialog.dismiss();
                        if (ObjectUtils.isNotEmpty(mConfirmListDialogListener)) {
                            if (!isSingle) {
                                List<GridSelectBean> newList = new ArrayList<>();
                                for (GridSelectBean bean : mList) {
                                    if (bean.isSelectStatus()) {
                                        newList.add(bean);
                                    }
                                }
                                mConfirmListDialogListener.onCheckSelect(newList);
                            }
                        }
                    }
                })
                .setOnDismissListener(dismissListener)
                .create();
        return dialog;
    }

    public void show() {
        if (ObjectUtils.isEmpty(dialog)) {
            initView();
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }
}
