package com.cdqj.cdqjpatrolandroid.presenter;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.PhoneUtils;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.utils.AESUtils;
import com.cdqj.cdqjpatrolandroid.utils.GsonUtils;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.bean.LoginBean;
import com.cdqj.cdqjpatrolandroid.bean.LoginResultBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.model.LoginModel;
import com.cdqj.cdqjpatrolandroid.view.i.ILoginView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyf on 2018/9/3 14:44
 *
 * @author lyf
 * desc：登录P
 */
public class LoginPresenter extends BasePresenter<ILoginView> implements LoginModel.PatrolOnListener{

    private LoginModel mModel;

    public LoginPresenter(ILoginView view) {
        super(view);
        mModel = new LoginModel(this);
    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable e) {
        mView.hideProgress();
        mView.onFailure(e);
    }

    /**
     * 登录返回结果
     * @param body body
     */
    @Override
    public void onLoginSuccess(BasePostResponse<LoginResultBean> body) {
        mView.onLoginSuccess(body, false);
    }

    /**
     * 登录执行
     * @param username username
     * @param password password
     */
    @SuppressLint("MissingPermission")
    public void login(String username, String password) throws Exception {
        mView.showProgress();
        // core/platform/login 用户登陆，登陆成功后，返回登陆成功得token字符串
        LoginBean bean = new LoginBean();
        bean.setUsername(username);
        bean.setPassword(password);
        bean.setMobileId(PhoneUtils.getIMEI());
        Gson gson = GsonUtils.gsonBuilder.create();
        String beanStr = gson.toJson(bean);
        Map<String, String> map = new HashMap<>(1);
        map.put("token", AESUtils.encrypt(beanStr));
        addSubscription(mApiService.login(map), mModel.login());
    }
}
