package com.cdqj.cdqjpatrolandroid.model;

import android.annotation.SuppressLint;

import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.bean.LoginResultBean;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：登录M
 */
public class LoginModel{
    /**
     * 回调
     */
    private PatrolOnListener mOnListener;

    public LoginModel(PatrolOnListener mOnListener) {

        this.mOnListener = mOnListener;
    }

    /**
     *  登录
     *  @return Subscription
     */
    @SuppressLint("MissingPermission")
    public BaseSubscriber login(){
        return new BaseSubscriber<BasePostResponse<LoginResultBean>>() {
                    @Override
                    public void onResult(BasePostResponse<LoginResultBean> loginResultBean) {
                        mOnListener.onLoginSuccess(loginResultBean);
                    }

                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {
                        mOnListener.onFailure(e);
                        e.printStackTrace();
                    }
                };
    }

    /**
     * 回调接口
     */
    public interface PatrolOnListener {
        /**
         * 网络请求失败
         * @param e e
         */
        void onFailure(ExceptionHandle.ResponeThrowable e);
        /**
         * 登录成功
         * @param body body
         */
        void onLoginSuccess(BasePostResponse<LoginResultBean> body);
    }
}
