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
import com.cdqj.cdqjpatrolandroid.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

/**
 * Created by lyf on 2018/9/11 17:22
 *
 * @author lyf
 * desc：提醒弹窗
 */
public class ConfirmSelectDialog {

    private DialogPlus dialog;
    private Context mContext;
    private boolean isNo = true;
    private String titleStr="提示";
    private String contentStr="确认执行？";
    private String yesStr="确认";
    private String noStr="取消";
    private int inAnimResource;
    private int outAnimResource;
    private boolean isEdit = false;
    private ConfirmDialogEditeListener editeListener;
    private ConfirmDialogListener mListener = new ConfirmDialogListener() {
        @Override
        public void onYesClick() {
        }

        @Override
        public void onNoClick() {
        }
    };

    public ConfirmSelectDialog setEditeListener(ConfirmDialogEditeListener editeListener) {
        this.editeListener = editeListener;
        return this;
    }

    public ConfirmSelectDialog(Context context) {
        mContext = context;
    }

    public ConfirmSelectDialog setEdit(boolean edit) {
        isEdit = edit;
        return this;
    }

    public ConfirmSelectDialog setNo(boolean no) {
        isNo = no;
        return this;
    }

    public ConfirmSelectDialog setTitleStr(String titleStr) {
        this.titleStr = titleStr;
        return this;
    }

    public ConfirmSelectDialog setContentStr(String contentStr) {
        this.contentStr = contentStr;
        return this;
    }

    public ConfirmSelectDialog setYesStr(String yesStr) {
        this.yesStr = yesStr;
        return this;
    }

    public ConfirmSelectDialog setNoStr(String noStr) {
        this.noStr = noStr;
        return this;
    }

    public ConfirmSelectDialog setListener(ConfirmDialogListener mListener) {
        this.mListener = mListener;
        return this;
    }

    public ConfirmSelectDialog setInAnimResource(int inAnimResource) {
        this.inAnimResource = inAnimResource;
        return this;
    }

    public ConfirmSelectDialog setOutAnimResource(int outAnimResource) {
        this.outAnimResource = outAnimResource;
        return this;
    }

    @SuppressLint("InflateParams")
    public DialogPlus initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cdqj_patrol_confirm_select_dialog, null);
        TextView title = view.findViewById(R.id.confirm_select_title);
        final EditText content = view.findViewById(R.id.confirm_select_content);
        TextView yes = view.findViewById(R.id.confirm_select_yes);
        TextView no = view.findViewById(R.id.confirm_select_no);
        TextView space = view.findViewById(R.id.confirm_select_space);
        title.setText(titleStr);
        yes.setText(yesStr);
        no.setText(noStr);
        no.setVisibility(isNo?View.VISIBLE:View.GONE);
        space.setVisibility(isNo?View.VISIBLE:View.GONE);

        if (isEdit){
            content.setHint(contentStr);
        }else {
            content.setText(contentStr);
        }
        content.setClickable(isEdit);
        content.setFocusable(isEdit);
        content.setFocusableInTouchMode(isEdit);

        dialog = DialogPlus.newDialog(mContext)
                .setContentHolder(new ViewHolder(view))
                .setContentWidth(ScreenUtils.getScreenWidth()/5*4)
                .setContentBackgroundResource(R.drawable.stroke_bg_radius_4)
                .setGravity(Gravity.CENTER)
                .setOnClickListener((dialog, view1) -> {
                    if (ObjectUtils.isEmpty(mListener)&&ObjectUtils.isEmpty(editeListener)) {
                        return;
                    }
                    if (view1.getId() == R.id.confirm_select_yes) {
                        if (!ObjectUtils.isEmpty(editeListener)) {
                            editeListener.onYesClick(content.getText().toString());
                            dialog.dismiss();
                            return;
                        }
                        if (!ObjectUtils.isEmpty(mListener)) {
                            mListener.onYesClick();
                        }
                        dialog.dismiss();
                    } else if (view1.getId() == R.id.confirm_select_no) {
                        if (!ObjectUtils.isEmpty(editeListener)) {
                            editeListener.onNoClick(content.getText().toString());
                            dialog.dismiss();
                            return;
                        }
                        if (!ObjectUtils.isEmpty(mListener)) {
                            mListener.onNoClick();
                        }
                        dialog.dismiss();
                    }
                })
                .setInAnimation(inAnimResource==0? com.orhanobut.dialogplus.R.anim.fade_in_center:inAnimResource)
                .setOutAnimation(outAnimResource==0? com.orhanobut.dialogplus.R.anim.fade_out_center:outAnimResource)
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

    public interface ConfirmDialogEditeListener {
        void onYesClick(String meg);
        void onNoClick(String meg);
    }
}
