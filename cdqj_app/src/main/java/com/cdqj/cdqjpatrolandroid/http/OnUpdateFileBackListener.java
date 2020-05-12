package com.cdqj.cdqjpatrolandroid.http;

import com.cdqj.cdqjpatrolandroid.bean.UpImgResultBean;

import java.util.List;

/**
 * Created by lyf on 2018/10/17 10:50
 *
 * @author lyf
 * desc：文件上传回调
 */
public interface OnUpdateFileBackListener {
    /**
     * 网络请求失败
     *
     * @param e e
     */
    void onFailure(String e);

    /**
     * 网络请求失败
     *
     * @param e e
     */
    void onFailure(ExceptionHandle.ResponeThrowable e);

    /**
     * 头像上传结果
     * @param type 上传文件类型
     *
     * @param body body
     * @param position 扩展标识
     */
    void onUpdateFileSuccess(BasePostResponse<List<UpImgResultBean>> body, int type, int position);
}
