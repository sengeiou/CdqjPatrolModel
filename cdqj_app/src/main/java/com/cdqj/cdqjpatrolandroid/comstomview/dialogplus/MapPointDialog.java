package com.cdqj.cdqjpatrolandroid.comstomview.dialogplus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

/**
 * Created by lyf on 2018/9/11 17:22
 *
 * @author lyf
 * desc：定位提醒提醒弹窗
 */
public class MapPointDialog {

    private DialogPlus dialog;
    private Context mContext;
    private boolean isNo = true;
    private String titleStr="请输入查询坐标点";
    private String yesStr="确认";
    private String noStr="取消";
    private String xHintStr = "请输入经度（X）";
    private String yHintStr = "请输入纬度（Y）";
    private int inAnimResource;
    private int outAnimResource;
    private boolean isEdit = false;
    private ConfirmDialogEditeListener editeListener;
    private ConfirmDialogListener mListener;

    public MapPointDialog setEditeListener(ConfirmDialogEditeListener editeListener) {
        this.editeListener = editeListener;
        return this;
    }

    public MapPointDialog(Context context) {
        mContext = context;
    }

    public MapPointDialog setEdit(boolean edit) {
        isEdit = edit;
        return this;
    }

    public MapPointDialog setNo(boolean no) {
        isNo = no;
        return this;
    }

    public MapPointDialog setTitleStr(String titleStr) {
        this.titleStr = titleStr;
        return this;
    }

    public MapPointDialog setYesStr(String yesStr) {
        this.yesStr = yesStr;
        return this;
    }

    public MapPointDialog setNoStr(String noStr) {
        this.noStr = noStr;
        return this;
    }

    public MapPointDialog setListener(ConfirmDialogListener mListener) {
        this.mListener = mListener;
        return this;
    }

    public MapPointDialog setInAnimResource(int inAnimResource) {
        this.inAnimResource = inAnimResource;
        return this;
    }

    public MapPointDialog setOutAnimResource(int outAnimResource) {
        this.outAnimResource = outAnimResource;
        return this;
    }

    public MapPointDialog setxHintStr(String xHintStr) {
        this.xHintStr = xHintStr;
        return this;
    }

    public MapPointDialog setyHintStr(String yHintStr) {
        this.yHintStr = yHintStr;
        return this;
    }

    @SuppressLint("InflateParams")
    public DialogPlus initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cdqj_patrol_map_point_diaolog, null);
        TextView title = view.findViewById(R.id.map_point_title);
        final EditText x = view.findViewById(R.id.map_point_x);
        final EditText y = view.findViewById(R.id.map_point_y);
        TextView yes = view.findViewById(R.id.confirm_select_yes);
        TextView no = view.findViewById(R.id.confirm_select_no);
        x.setHint(xHintStr);
        y.setHint(yHintStr);
        title.setText(titleStr);
        yes.setText(yesStr);
        no.setText(noStr);
        no.setVisibility(isNo?View.VISIBLE:View.GONE);


        dialog = DialogPlus.newDialog(mContext)
                .setContentHolder(new ViewHolder(view))
                .setContentWidth(ScreenUtils.getScreenWidth()/5*4)
                .setContentBackgroundResource(R.drawable.stroke_bg_radius_4)
                .setGravity(Gravity.CENTER)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        if (ObjectUtils.isEmpty(mListener)&&ObjectUtils.isEmpty(editeListener)) {
                            return;
                        }
                        if (view.getId() == R.id.confirm_select_yes) {
                            if (!ObjectUtils.isEmpty(mListener)) {
                                mListener.onYesClick();
                            }
                            if (!ObjectUtils.isEmpty(editeListener)) {
                                editeListener.onYesClick(StringUtils.isTrimEmpty(x.getText().toString())
                                        || StringUtils.isTrimEmpty(y.getText().toString()) ?
                                        "":x.getText().toString() + "," + y.getText().toString());
                            }
                            dialog.dismiss();
                        } else if (view.getId() == R.id.confirm_select_no) {
                            if (!ObjectUtils.isEmpty(mListener)) {
                                mListener.onNoClick();
                            }
                            if (!ObjectUtils.isEmpty(editeListener)) {
                                editeListener.onNoClick(StringUtils.isTrimEmpty(x.getText().toString())
                                        || StringUtils.isTrimEmpty(y.getText().toString()) ?
                                        "":x.getText().toString() + "," + y.getText().toString());
                            }
                            dialog.dismiss();
                        }
                    }
                })
                .setInAnimation(inAnimResource==0? com.orhanobut.dialogplus.R.anim.fade_in_center:inAnimResource)
                .setOutAnimation(outAnimResource==0? com.orhanobut.dialogplus.R.anim.fade_out_center:outAnimResource)
                .create();
        return dialog;
    }
    public interface ConfirmDialogEditeListener {
        void onYesClick(String meg);
        void onNoClick(String meg);
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
