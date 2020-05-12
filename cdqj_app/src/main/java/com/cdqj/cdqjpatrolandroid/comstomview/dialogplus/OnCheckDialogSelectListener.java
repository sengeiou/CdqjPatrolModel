package com.cdqj.cdqjpatrolandroid.comstomview.dialogplus;

import java.util.List;

public interface OnCheckDialogSelectListener {
    void onCheckSelect(List titles, List positions);
    void onCheckSingle(int position);
    void onItemClickListener(int position);
}
