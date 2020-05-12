package com.cdqj.cdqjpatrolandroid.view.ui.main;

import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.StringUtils;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.base.BaseActivity;
import com.cdqj.cdqjpatrolandroid.base.BasePresenter;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;

/**
 * 设置界面
 *
 * @author lyf
 * @date 2018年9月3日 10:11:18
 */
public class SetActivity extends BaseActivity {

    EditText setHost;
    EditText setFileHost;

    /**
     * 界面初始化
     */
    @Override
    public void initView() {
        if (StringUtils.isTrimEmpty(PreferencesUtil.getString(Constant.REQUEST_ADDRESS))) {
            PreferencesUtil.putString(Constant.REQUEST_ADDRESS, Constant.REQUEST_ADDRESS_DEFAULT);
        }
        if (StringUtils.isTrimEmpty(PreferencesUtil.getString(Constant.FILE_SERVICE_PATH))) {
            PreferencesUtil.putString(Constant.FILE_SERVICE_PATH, Constant.FILE_SERVICE_PATH_DEFAULT);
        }
        setHost.setText(PreferencesUtil.getString(Constant.REQUEST_ADDRESS));
        setFileHost.setText(PreferencesUtil.getString(Constant.FILE_SERVICE_PATH));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.cdqj_patrol_activity_set;
    }

    @Override
    protected void findView() {
        setHost = findViewById(R.id.set_host);
        setFileHost = findViewById(R.id.set_file_host);
    }

    @Override
    protected String getTitleText() {
        return "设置";
    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_setting).setOnClickListener(this::setClick);
        findViewById(R.id.btn_res).setOnClickListener(this::setClick);
    }

    public void setClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_setting) {
            if (StringUtils.isTrimEmpty(setHost.getText().toString())) {
                ToastBuilder.showShortWarning("请输入正确的请求地址");
            } else {
                PreferencesUtil.putString(Constant.REQUEST_ADDRESS, setHost.getText().toString());
            }
            if (StringUtils.isTrimEmpty(setFileHost.getText().toString())) {
                ToastBuilder.showShortWarning("请输入正确的文件服务器地址");
            } else {
                PreferencesUtil.putString(Constant.FILE_SERVICE_PATH, setFileHost.getText().toString());
            }
            finish();

        } else if (i == R.id.btn_cs) {
            if (StringUtils.isTrimEmpty(setHost.getText().toString())) {
                ToastBuilder.showShortWarning("请输入正确的请求地址");
            } else {
                /*
                 * 泸州测试         http://172.16.102.227:12080/
                 * 产品（大同）测试  http://172.16.102.227:10080/
                 * 泸州内网 http://192.168.8.174:9090/
                 */
                PreferencesUtil.putString(Constant.REQUEST_ADDRESS, "http://192.168.8.174:9090/");
            }
            if (StringUtils.isTrimEmpty(setFileHost.getText().toString())) {
                ToastBuilder.showShortWarning("请输入正确的文件服务器地址");
            } else {
                PreferencesUtil.putString(Constant.FILE_SERVICE_PATH, "http://192.168.8.174:9090/");
            }
            finish();
        } else if (i == R.id.btn_res) {
            if (StringUtils.isTrimEmpty(setHost.getText().toString())) {
                ToastBuilder.showShortWarning("请输入正确的请求地址");
            } else {
                PreferencesUtil.putString(Constant.REQUEST_ADDRESS, Constant.REQUEST_ADDRESS_DEFAULT);
            }
            if (StringUtils.isTrimEmpty(setFileHost.getText().toString())) {
                ToastBuilder.showShortWarning("请输入正确的文件服务器地址");
            } else {
                PreferencesUtil.putString(Constant.FILE_SERVICE_PATH, Constant.FILE_SERVICE_PATH_DEFAULT);
            }
            finish();
        }
    }
}
