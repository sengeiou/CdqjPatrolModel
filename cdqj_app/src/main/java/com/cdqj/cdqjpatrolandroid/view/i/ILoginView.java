package com.cdqj.cdqjpatrolandroid.view.i;

import com.cdqj.cdqjpatrolandroid.base.BaseView;
import com.cdqj.cdqjpatrolandroid.bean.LoginResultBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;

/**
 * Created by lyf on 2018/9/3 14:43
 *
 * @author lyf
 * desc：登录
 */
public interface ILoginView extends BaseView {

    /**
     * 登录成功返回结果
     * @param body body
     */
    void onLoginSuccess(BasePostResponse<LoginResultBean> body, boolean fingerprint);
}
