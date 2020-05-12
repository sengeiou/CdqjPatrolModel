package com.cdqj.cdqjpatrolandroid.view.ui.main;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.comstomview.dialog.ConfirmDialogUtils;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmDialogListener;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmSelectDialog;
import com.cdqj.cdqjpatrolandroid.config.GlobalConfig;
import com.cdqj.cdqjpatrolandroid.fingerprint.FingerprintCallback;
import com.cdqj.cdqjpatrolandroid.fingerprint.FingerprintVerifyManager;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.utils.AESUtils;
import com.cdqj.cdqjpatrolandroid.utils.BarOtherUtils;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.utils.GsonUtils;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.bean.LoginBean;
import com.cdqj.cdqjpatrolandroid.bean.LoginResultBean;
import com.cdqj.cdqjpatrolandroid.http.BasePostResponse;
import com.cdqj.cdqjpatrolandroid.http.BaseSubscriber;
import com.cdqj.cdqjpatrolandroid.http.RetrofitManager;
import com.cdqj.cdqjpatrolandroid.service.ServiceConstant;
import com.cdqj.cdqjpatrolandroid.utils.DataUtils;
import com.cdqj.cdqjpatrolandroid.utils.PatrolEnterPointActivity;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.utils.TransformUtils;
import com.cdqj.cdqjpatrolandroid.view.i.ILoginView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录界面
 *
 * @author lyf
 * 创建时间 2018年9月3日 09:57:47
 */
public class LoginActivity extends BaseActivity implements ILoginView {
    EditText etLoginUsername;
    ImageView loginUsernameIv;
    EditText etLoginPassword;
    TextView activityVersionName;
    ImageView loginPswShow;
    TextView lineLoginUsername;
    TextView lineLoginPassword;
    /**
     * 网络切换
     */
    TextView change;

    private boolean hasFocus;

    /**
     * 登录重试次数
     * 超过五次停止重试登录
     */
    private int resCount;

    /**
     * 指纹登录构建对象
     */
    private FingerprintVerifyManager.Builder builder;
    private FingerprintCallback callback = new FingerprintCallback() {
        @Override
        public void onHwUnavailable() {
            // 当硬件模块不可用时，回调 onHwUnavailable() 方法。
//                XDialogUtils.showXPopupConfirm(LoginActivity.this, "指纹模块不能使用，请您选择密码登录！");
        }

        @Override
        public void onNoneEnrolled() {
            // 当手机上未添加指纹时，回调 onNoneEnrolled() 方法；

        }

        @Override
        public void onSucceeded() {
            // 指纹验证成功  执行指纹登录
            if (ObjectUtils.isNotEmpty(body)) {
                getLoginToRes();
                return;
            }
            // 快捷登录修改标识
            PreferencesUtil.putBoolean(Constant.FINGERPRINT, true);
            try {
                login(PreferencesUtil.getString(Constant.USER_NAME),
                        PreferencesUtil.getString(Constant.PASS_WORD_HIDE), false, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailed() {
            // 指纹验证失败
//                ToastBuilder.showShortWarning("当前指纹不匹配");
        }

        @Override
        public void onUsepwd() {
            // 当用户选择密码验证时，回调 onUsepwd() 方法；
            // 初始化指纹登录标识
            PreferencesUtil.putBoolean(Constant.FINGERPRINT, false);
        }

        @Override
        public void onCancel() {
            // 当用户取消指纹验证框时，回调 onCancel() 方法；
            if (ObjectUtils.isNotEmpty(body)) {
                getLoginToRes();
            }
        }
    };

    /**
     * 界面初始化
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        // 指纹登录
        builder = new FingerprintVerifyManager.Builder(this);
        builder.usepwdVisible(true).callback(callback);

        //整个界面往上移动
        mImmersionBar.keyboardEnable(false);
        mImmersionBar.keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN).init();


        // 泸州内外网切换/大同隐藏
        change.setVisibility(View.GONE);

        activityVersionName.setText("版本：V" + AppUtils.getAppVersionName());

        PreferencesUtil.putBoolean(Constant.NET_STATE, true);
        etLoginUsername.setText(PreferencesUtil.getString(Constant.USER_NAME));
        etLoginPassword.setText(PreferencesUtil.getString(Constant.PASS_WORD));
        etLoginUsername.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                // 此处为获取焦点时的处理内容
                etLoginUsername.setTextColor(ContextCompat.getColor(
                        LoginActivity.this, R.color.theme_one));
                lineLoginUsername.setBackgroundColor(ContextCompat.getColor(
                        LoginActivity.this, R.color.theme_one));
                loginUsernameIv.setImageResource(R.mipmap.icon_username_clean);
            } else {
                // 此处为失去焦点时的处理内容
                etLoginUsername.setTextColor(ContextCompat.getColor(
                        LoginActivity.this, R.color.text_auxiliary));
                lineLoginUsername.setBackgroundColor(ContextCompat.getColor(
                        LoginActivity.this, R.color.line));
                loginUsernameIv.setImageResource(R.mipmap.icon_username_clean_to);
            }
        });
        etLoginPassword.setOnFocusChangeListener((v, hasFocus) -> {
            this.hasFocus = hasFocus;
            if (hasFocus) {
                etLoginPassword.setTextColor(ContextCompat.getColor(
                        LoginActivity.this, R.color.theme_one));
                lineLoginPassword.setBackgroundColor(ContextCompat.getColor(
                        LoginActivity.this, R.color.theme_one));
                loginPswShow.setImageResource(ObjectUtils.isNotEmpty(loginPswShow.getTag()) && "show".equals(loginPswShow.getTag().toString()) ?
                        R.mipmap.icon_psw_show : R.mipmap.icon_psw_hid);
            } else {
                etLoginPassword.setTextColor(ContextCompat.getColor(
                        LoginActivity.this, R.color.text_auxiliary));
                lineLoginPassword.setBackgroundColor(ContextCompat.getColor(
                        LoginActivity.this, R.color.line));
                loginPswShow.setImageResource(ObjectUtils.isNotEmpty(loginPswShow.getTag()) && "show".equals(loginPswShow.getTag().toString()) ?
                        R.mipmap.icon_psw_show_to : R.mipmap.icon_psw_hid_to);
            }
        });
        etLoginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听  输入结束执行该方法 即文本框改变之后 会执行的动作
                if (StringUtils.isEmpty(s.toString())) {
                    loginUsernameIv.setVisibility(View.INVISIBLE);
                } else {
                    loginUsernameIv.setVisibility(View.VISIBLE);
                }
            }
        });
        etLoginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听  输入结束执行该方法 即文本框改变之后 会执行的动作
                if (StringUtils.isEmpty(s.toString())) {
                    loginPswShow.setVisibility(View.INVISIBLE);
                } else {
                    loginPswShow.setVisibility(View.VISIBLE);
                }
            }
        });
        if (getIntent().getBooleanExtra("token_Invalid", false)) {
            // token失效登录
            try {
                resCount++;
                if (resCount >= 5) {
                    ToastBuilder.showShortError("网络或服务器异常，无法登录");
                    return;
                }
                login(PreferencesUtil.getString(Constant.USER_NAME), PreferencesUtil.getString(Constant.PASS_WORD), true, false);
            } catch (Exception e) {
                e.printStackTrace();
                hideProgress();
                ToastBuilder.showShortError(R.string.aes_code_error);
            }
        }
    }

    @Override
    public void initData() {
        new Handler().postDelayed(() -> {
            // 指纹登录开启则直接操作指纹去登录 TODO
            if (PreferencesUtil.getBoolean(Constant.FINGERPRINT, false)) {
                builder.build();
            }
        }, 500);
    }

    /**
     * 执行登录
     *
     * @param username    用户名
     * @param password    密码
     * @param isFlag      是否自动登录
     * @param fingerprint 是否指纹登录
     */
    private void login(String username, String password, boolean isFlag, boolean fingerprint) throws Exception {
        if (isFlag) {
            resCount++;
            if (resCount >= 5) {
                ToastBuilder.showShortError("网络或服务器异常，无法登录");
                return;
            }
        }
        // core/platform/login 用户登陆，登陆成功后，返回登陆成功得token字符串
        LoginBean bean = new LoginBean();
        bean.setUsername(username);
        bean.setPassword(password);
        bean.setMobileId(DeviceUtils.getAndroidID());
        Gson gson = GsonUtils.gsonBuilder.create();
        String beanStr = gson.toJson(bean);
        Map<String, String> map = new HashMap<>(1);
        map.put("token", AESUtils.encrypt(beanStr));
        try {
            map.put("token", AESUtils.encrypt(GsonUtils.gsonBuilder.create().toJson(bean)));
            showProgress("登录中...");
            RetrofitManager.getApiService().login(map)
                    .compose(TransformUtils.defaultSchedulers())
                    .subscribe(new BaseSubscriber<BasePostResponse<LoginResultBean>>() {
                        @Override
                        public void onResult(BasePostResponse<LoginResultBean> r) {
                            onLoginSuccess(r, fingerprint);
                            hideProgress();
                        }

                        @Override
                        public void onError(ExceptionHandle.ResponeThrowable e) {
                            onFailure(e);
                            hideProgress();
//                            ConfirmDialogUtils.showDialogDef(LoginActivity.this, e.getMessage());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            hideProgress();
            ToastBuilder.showShortWarning("请查看请求地址是否正确");
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_login;
    }

    @Override
    protected void findView() {
        etLoginUsername = findViewById(R.id.et_login_username);
        loginUsernameIv = findViewById(R.id.login_username_iv);
        etLoginPassword = findViewById(R.id.et_login_password);
        activityVersionName = findViewById(R.id.activity_version_name);
        loginPswShow = findViewById(R.id.login_psw_show);
        lineLoginUsername = findViewById(R.id.line_login_username);
        lineLoginPassword = findViewById(R.id.line_login_password);
        change = findViewById(R.id.login_psw_change);
    }

    @Override
    protected String getTitleText() {
        return null;
    }


    @Override
    public void initListener() {
        findViewById(R.id.login_content_img).setOnLongClickListener(this::addLongClick);
        findViewById(R.id.btn_login_submit).setOnClickListener(this::setClick);
        findViewById(R.id.login_psw_show).setOnClickListener(this::setClick);
        findViewById(R.id.login_username_iv).setOnClickListener(this::setClick);
        findViewById(R.id.login_fast).setOnClickListener(this::setClick);
        findViewById(R.id.login_psw_change).setOnClickListener(this::setClick);
        findViewById(R.id.activity_version_name).setOnClickListener(this::setClick);
//        findViewById(R.id.login_content_img).setOnClickListener(this::setClick);
    }

    public boolean addLongClick(View view) {
        int i = view.getId();
        if (i == R.id.login_content_img) {// 长按进入设置界面
            PatrolEnterPointActivity.gotoSetActivity(this);
        }
        return false;
    }

    public void setClick(View view) {
        int i = view.getId();
        if (i == R.id.login_fast) {// 快捷登录 指纹登录 不再提示 初始化
            PreferencesUtil.putBoolean(Constant.FINGERPRINT_ESC, false);
            builder.build();
        } else if (i == R.id.btn_login_submit) {// 初始化网络请求
            RetrofitManager.mOkHttpClient = null;
            RetrofitManager.mApiRetrofit = null;

            String username = etLoginUsername.getText().toString();
            String password = etLoginPassword.getText().toString();
            if (StringUtils.isTrimEmpty(username)) {
                ToastBuilder.showShortWarning("请输入有效用户名");
                return;
            }
            if (StringUtils.isTrimEmpty(password)) {
                ToastBuilder.showShortWarning("请输入有效密码");
                return;
            }
            PreferencesUtil.putString(Constant.USER_NAME, username);
            if (!StringUtils.isTrimEmpty(etLoginPassword.getText().toString())) {
                PreferencesUtil.putString(Constant.PASS_WORD, password);
            }
            try {
                login(username, password, false, false);
            } catch (Exception e) {
                e.printStackTrace();
                hideProgress();
                ToastBuilder.showShortError(R.string.aes_code_error);
            }
        } else if (i == R.id.login_psw_change) {// 网络切换 ToastBuilder.showShort("忘记密码");
            PatrolEnterPointActivity.gotoSetActivity(this);
        } else if (i == R.id.activity_version_name) {// ComUtil.showSound(R.raw.audio_msg_new);

        } else if (i == R.id.login_psw_show) {// 密码显示与否
            if (ObjectUtils.isEmpty(loginPswShow.getTag())) {
                //默认状态显示密码--设置文本
                loginPswShow.setTag("show");
                loginPswShow.setImageResource(hasFocus ? R.mipmap.icon_psw_show : R.mipmap.icon_psw_show_to);
                etLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else if ("hid".equals(loginPswShow.getTag().toString())) {
                //选择状态 显示明文--设置为可见的密码
                loginPswShow.setTag("show");
                loginPswShow.setImageResource(hasFocus ? R.mipmap.icon_psw_show : R.mipmap.icon_psw_show_to);
                etLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //默认状态显示密码--设置文本
                loginPswShow.setTag("hid");
                loginPswShow.setImageResource(hasFocus ? R.mipmap.icon_psw_hid : R.mipmap.icon_psw_hid_to);
                etLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }

        } else if (i == R.id.login_username_iv) {
            etLoginUsername.setText("");
            loginUsernameIv.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (StringUtils.isEmpty(etLoginUsername.getText().toString())) {
            loginUsernameIv.setVisibility(View.INVISIBLE);
        } else {
            loginUsernameIv.setVisibility(View.VISIBLE);
        }
        if (StringUtils.isEmpty(etLoginPassword.getText().toString())) {
            loginPswShow.setVisibility(View.INVISIBLE);
        } else {
            loginPswShow.setVisibility(View.VISIBLE);
        }
    }

    public void getLoginToRes() {
        showProgress("数据初始化...");
        PatrolEnterPointActivity.gotoCdqjMainActivity(this, body.getObj());
        finish();
    }

    private BasePostResponse<LoginResultBean> body;

    @Override
    public void onLoginSuccess(BasePostResponse<LoginResultBean> body, boolean fingerprint) {
        if (body.isSuccess()) {
            if (!fingerprint) {
                // 非指纹登录成功时保存指纹登录需要的密码
                PreferencesUtil.putString(Constant.PASS_WORD_HIDE, etLoginPassword.getText().toString());
            }
            this.body = body;
            GlobalConfig.loginStatus = true;
            // 存储TOKEN
            PreferencesUtil.putString(ServiceConstant.TRACE_ENTITY_NAME, PreferencesUtil.getString(Constant.USER_NAME));
            PreferencesUtil.putString(Constant.TOKEN, body.getObj().getToken());
            if (ObjectUtils.isNotEmpty(body.getObj().getDomains()) && !body.getObj().getDomains().isEmpty()) {
                PreferencesUtil.putInt(Constant.DOMAIN_NAME_ID, DataUtils.getDoMainId(body.getObj().getDomains().get(0).getDomainName()));
            }
            PreferencesUtil.putString(Constant.APP_VERSION, ObjectUtils.isNotEmpty(body.getObj().getAppinfos()) ?
                    body.getObj().getAppinfos().getVersion() : "1.0.0");
            if (BarOtherUtils.canAuthenticate(this) &&
                    !PreferencesUtil.getBoolean(Constant.FINGERPRINT_ESC, false) &&
                    !PreferencesUtil.getBoolean(Constant.FINGERPRINT, false)) {
                // 登录成功提示用户是否启动指纹登录
                // 1 启用指纹登录 则下次进入自动启用指纹登录
                // 2 未启用指纹登录 则下次成功后提示是否启用指纹登录
                new ConfirmSelectDialog(this)
                        .setContentStr("下次是否启用指纹快捷登录？(如果您不小心点错‘不再提示’，可点击登录按钮下方‘快捷登录’按钮)")
                        .setYesStr("启用")
                        .setNoStr("不再提示")
                        .setListener(new ConfirmDialogListener() {
                            @Override
                            public void onYesClick() {
                                // 指纹登录显示 改变指纹登录标识
                                PreferencesUtil.putBoolean(Constant.FINGERPRINT, true);
                                builder.build();
                            }

                            @Override
                            public void onNoClick() {
                                // 不再提示 则不再判断指纹登录
                                PreferencesUtil.putBoolean(Constant.FINGERPRINT_ESC, true);
                                getLoginToRes();
                            }
                        }).show();
            } else {
                getLoginToRes();
            }
        } else {
            hideProgress();
            ConfirmDialogUtils.showDialogDef(this, body.getMsg());
        }
    }

    @Override
    public void showProgress() {
        baseShowProgress("登录中...");
    }

    @Override
    public void showProgress(String str) {
        baseShowProgress(str);
    }

    @Override
    public void hideProgress() {
        baseHideProgress();
    }

    @Override
    public void onSuccess(String msg) {

    }

    @Override
    public void onFailure(ExceptionHandle.ResponeThrowable msg) {
        baseOnFailure(msg);
    }

    @Override
    public void onBackPressed() {
        back();
    }

    /**
     * 返回
     */
    public void back() {
        new ConfirmSelectDialog(this)
                .setTitleStr("提示")
                .setContentStr("确定退出APP")
                .setYesStr("确定")
                .setNoStr("取消")
                .setListener(new ConfirmDialogListener() {
                    @Override
                    public void onYesClick() {
                        AppUtils.exitApp();
                    }

                    @Override
                    public void onNoClick() {
                    }
                }).initView().show();
    }
}
