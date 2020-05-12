package com.cdqj.cdqjpatrolandroid.comstomview.dialogplus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.widget.BaseAdapter;

import com.blankj.utilcode.util.ObjectUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;

import java.util.ArrayList;

/**
 * Created by lyf on 2018/9/11 17:22
 *
 * @author lyf
 * desc：图片选择弹窗
 */
public class PictureSelectDialog {

    private DialogPlus dialog;
    private Context mContext;
    private int inAnimResource;
    private int outAnimResource;
    private BaseAdapter adapter;
    private ArrayList<String> data;
    private OnPictureSelectListener mListener;
    /**
     * 是否查看大图
     */
    private boolean isSee;

    public PictureSelectDialog(Context context) {
        mContext = context;
    }

    public PictureSelectDialog setListener(OnPictureSelectListener mListener) {
        this.mListener = mListener;
        data = new ArrayList<>();
        data.add("相机");
        data.add("从相册选择");
        return this;
    }

    public PictureSelectDialog setInAnimResource(int inAnimResource) {
        this.inAnimResource = inAnimResource;
        return this;
    }

    public PictureSelectDialog setOutAnimResource(int outAnimResource) {
        this.outAnimResource = outAnimResource;
        return this;
    }

    public PictureSelectDialog setSee(boolean see) {
        isSee = see;
        return this;
    }

    public PictureSelectDialog setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
        return this;
    }

    @SuppressLint("InflateParams")
    public DialogPlus initView() {
        if (isSee) {
            data.add("查看大图");
        }
        adapter = new PictureDialogAdapter(data, mContext);
        dialog = DialogPlus.newDialog(mContext)
                .setContentHolder(new ListHolder())
                .setAdapter(adapter)
                .setFooter(R.layout.cdqj_patrol_picture_select_dialog_footer)
                .setGravity(Gravity.BOTTOM)
                .setOnClickListener((dialog, view1) -> {
                    if (ObjectUtils.isEmpty(mListener)) {
                        return;
                    }
                    if (view1.getId() == R.id.picture_select_title) {
                        dialog.dismiss();
                    }
                })
                .setOnItemClickListener((dialog, item, view, position) -> {
                    mListener.onItemClickListener(position);
                    dialog.dismiss();
                })
                .setInAnimation(inAnimResource==0? com.orhanobut.dialogplus.R.anim.slide_in_bottom:inAnimResource)
                .setOutAnimation(outAnimResource==0? com.orhanobut.dialogplus.R.anim.slide_out_bottom:outAnimResource)
                .create();
        return dialog;
    }
}
