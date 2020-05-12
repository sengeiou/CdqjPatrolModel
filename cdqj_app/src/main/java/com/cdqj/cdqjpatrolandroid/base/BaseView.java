package com.cdqj.cdqjpatrolandroid.base;

import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;

/**
 * Created by lyf on 2017/6/21.
 * view界面调用的通用方法
 * 子接口集成自此类，新增界面各自需要的方法
 * @author lyf
 */
public interface BaseView {
    /**
     * 显示进度条
     */
    void showProgress();

    /**
     * 显示进度条
     */
    void showProgress(String str);

    /**
     * 隐藏进度条
     */
    void hideProgress();

    /**
     * 请求成功
     * @param msg msg 可自定义msg给予View显示
     */
    void onSuccess(String msg);

    /**
     * 请求失败
     * @param msg msg 可自定义msg给予View显示
     */
    void onFailure(ExceptionHandle.ResponeThrowable msg);
}
