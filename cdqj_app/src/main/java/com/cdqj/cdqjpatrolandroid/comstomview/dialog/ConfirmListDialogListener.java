package com.cdqj.cdqjpatrolandroid.comstomview.dialog;



import com.cdqj.cdqjpatrolandroid.bean.GridSelectBean;

import java.util.List;

public interface ConfirmListDialogListener {
    void onCheckSelect(List<GridSelectBean> mList);
    void onCheckSingle(GridSelectBean bean, int position);
    void onItemClickListener(GridSelectBean bean, int position);
}

