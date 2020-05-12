package com.cdqj.cdqjpatrolandroid.config;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.base.App;
import com.cdqj.cdqjpatrolandroid.bean.LoginBean;
import com.cdqj.cdqjpatrolandroid.bean.LoginResultBean;
import com.cdqj.cdqjpatrolandroid.bean.UsernameBean;
import com.cdqj.cdqjpatrolandroid.comstomview.dialog.ConfirmDialogUtils;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.http.RetrofitManager;
import com.cdqj.cdqjpatrolandroid.http.RetrofitUtils;
import com.cdqj.cdqjpatrolandroid.service.ServiceConstant;
import com.cdqj.cdqjpatrolandroid.utils.AESUtils;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.utils.DataUtils;
import com.cdqj.cdqjpatrolandroid.utils.GsonUtils;
import com.cdqj.cdqjpatrolandroid.utils.PatrolEnterPointActivity;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.utils.TransformUtils;
import com.cdqj.cdqjpatrolandroid.view.ui.main.LoginActivity;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import static com.cdqj.cdqjpatrolandroid.http.RetrofitUtils.hideProgress;
import static com.cdqj.cdqjpatrolandroid.http.RetrofitUtils.showProgress;

/**
 * 版权：成都千嘉科技公司所有
 *
 * @author lyf
 * 创建日期： 2019/10/30
 * 创建时间： 10:11
 * 描述：巡线初始化基础数据配置
 */
public class CdqjInitDataConfig {

    /**
     * 常量标识
     * 判断是否是Lib库
     * 是否需要登录升级等
     * 默认是
     */
    public static boolean isLib = true;

    /**
     * 是否需要上下班 默认不需要
     */
    public static boolean isNeedWork = false;

    /**
     * 如果是第三方库导入 则配置基础相关信息等
     * @param requestUrl 请求地址
     * @param imgUrl 文件服务器地址
     * @param userName 用户名
     * @param psw 密码
     */
    public static void initPatrolData(Context context, String requestUrl, String imgUrl, String userName, String psw) {
        if (CdqjInitDataConfig.isLib) {
            if (!StringUtils.isTrimEmpty(requestUrl)) {
                PreferencesUtil.putString(Constant.REQUEST_ADDRESS, requestUrl);
            }
            if (!StringUtils.isTrimEmpty(imgUrl)) {
                PreferencesUtil.putString(Constant.FILE_SERVICE_PATH, imgUrl);
            }
            if (!StringUtils.isTrimEmpty(userName)) {
                PreferencesUtil.putString(Constant.USER_NAME, userName);
            }
            if (!StringUtils.isTrimEmpty(psw)) {
                PreferencesUtil.putString(Constant.PASS_WORD, psw);
                PreferencesUtil.putString(Constant.PASS_WORD_HIDE, psw);
            }
            login(context, userName, psw);
        } else {
            ActivityUtils.startActivity(new Intent(App.getInstance(), LoginActivity.class));
        }
    }

    /**
     * 如果是第三方库导入 则配置基础相关信息等
     * @param userName 用户名
     * @param psw 密码
     */
    public static void initPatrolData(Context context, String userName, String psw) {
        if (CdqjInitDataConfig.isLib) {
            if (!StringUtils.isTrimEmpty(userName)) {
                PreferencesUtil.putString(Constant.USER_NAME, userName);
            }
            if (!StringUtils.isTrimEmpty(psw)) {
                PreferencesUtil.putString(Constant.PASS_WORD, psw);
                PreferencesUtil.putString(Constant.PASS_WORD_HIDE, psw);
            }
            login(context, userName, psw);
        } else {
            ActivityUtils.startActivity(new Intent(App.getInstance(), LoginActivity.class));
        }
    }

    private static void login(Context context, String userName, String psw) {
        LoginBean bean = new LoginBean();
        bean.setUsername(userName);
        bean.setPassword(psw);
        bean.setMobileId(DeviceUtils.getAndroidID());
        Gson gson = GsonUtils.gsonBuilder.create();
        String beanStr = gson.toJson(bean);
        Map<String, String> map = new HashMap<>(1);
        try {
            map.put("token", AESUtils.encrypt(beanStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            map.put("token", AESUtils.encrypt(GsonUtils.gsonBuilder.create().toJson(bean)));
            showProgress(context, "数据初始化...");
            RetrofitManager.getApiService().login(map)
                    .compose(TransformUtils.defaultSchedulers())
                    .subscribe(new BaseSubscriber<BasePostResponse<LoginResultBean>>() {
                        @Override
                        public void onResult(BasePostResponse<LoginResultBean> r) {
                            hideProgress();
                            onLoginSuccess(context, r);
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable e) {
                            hideProgress();
                            String msg = "数据初始化失败-1:" + e.message + "/" + e.code;
                            ConfirmDialogUtils.showDialogDef(context, msg);
//                            ToastBuilder.showLongError(msg);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            hideProgress();
            ToastBuilder.showShortWarning("请查看请求地址是否正确");
        }
    }

    private static void onLoginSuccess(Context context, BasePostResponse<LoginResultBean> body) {
        GlobalConfig.loginStatus = true;
        // 存储TOKEN
        PreferencesUtil.putString(ServiceConstant.TRACE_ENTITY_NAME, PreferencesUtil.getString(Constant.USER_NAME));
        PreferencesUtil.putString(Constant.TOKEN, body.getObj().getToken());
        String tokenStr = "";
        try {
            tokenStr = AESUtils.decrypt(body.getObj().getToken());
        } catch (Exception e) {
            e.printStackTrace();
            ToastBuilder.showShortError(R.string.aes_code_error);
        }
        if (!StringUtils.isTrimEmpty(tokenStr)) {
            UsernameBean bean = GsonUtils.gsonBuilder.create().fromJson(tokenStr, UsernameBean.class);
            PreferencesUtil.putString(Constant.TRUE_NAME, StringUtils.isTrimEmpty(bean.getTrueName()) ? "未知" : bean.getTrueName());
            PreferencesUtil.putInt(Constant.USER_ID, bean.getId());
        }
        if (ObjectUtils.isNotEmpty(body.getObj().getDomains()) && !body.getObj().getDomains().isEmpty()) {
            PreferencesUtil.putInt(Constant.DOMAIN_NAME_ID, DataUtils.getDoMainId(body.getObj().getDomains().get(0).getDomainName()));
        }
        if (!CdqjInitDataConfig.isNeedWork) {
            // 如果不需要上班操作，则默认执行上班
            showProgress(context, "数据初始化...");
            RetrofitManager.mOkHttpClient = null;
            RetrofitUtils.doWork(new RetrofitUtils.PatrolOnListener() {
                @Override
                public void onFailure(ExceptionHandle.ResponeThrowable e) {
                    hideProgress();
                    String msg = "数据初始化失败-2:" + e.message + "/" + e.code;
                    ConfirmDialogUtils.showDialogDef(context, msg);
//                    ToastBuilder.showLongError(msg);
                }

                @Override
                public void onSubmitOffWorkSuccess(BasePostResponse<Object> response) {
                    // 上班成功与否
                    hideProgress();
                    if (response.isSuccess()) {
                        GlobalConfig.isDoWork = true;
                        showProgress(context, "界面初始化...");
                        PatrolEnterPointActivity.gotoCdqjMainActivity(context, body.getObj());
                    } else {
                        ToastBuilder.showShortWarning(response.getMsg());
                    }
                }
            }, 1);
        } else {
            showProgress(context, "界面初始化...");
            PatrolEnterPointActivity.gotoCdqjMainActivity(context, body.getObj());
        }
    }

}
