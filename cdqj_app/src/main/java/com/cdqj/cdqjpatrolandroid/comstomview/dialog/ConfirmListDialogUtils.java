package com.cdqj.cdqjpatrolandroid.comstomview.dialog;

import android.content.Context;

import com.cdqj.cdqjpatrolandroid.bean.GridSelectBean;

import java.util.List;

/**
 * 弹窗常用的工具类
 * @author lyf
 */
public class ConfirmListDialogUtils {

    public static void showDialogReturnValue(Context context, List<GridSelectBean> list, OnDialogReturnValueClick onDialogReturnValueClick) {
        new ConfirmListDialog(context)
                .setTitleStr("请选择")
                .setList(list)
                .setConfirmListDialogListener(new ConfirmListDialogListener() {
                    @Override
                    public void onCheckSelect(List<GridSelectBean> mList) {

                    }

                    @Override
                    public void onCheckSingle(GridSelectBean bean, int position) {

                    }

                    @Override
                    public void onItemClickListener(GridSelectBean bean, int position) {
                        onDialogReturnValueClick.onItemGetValue(bean);
                    }
                }).show();
    }

    public interface OnDialogReturnValueClick {
        void onItemGetValue(GridSelectBean bean);
    }

}
