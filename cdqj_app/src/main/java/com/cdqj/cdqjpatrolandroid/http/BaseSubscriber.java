package com.cdqj.cdqjpatrolandroid.http;

import android.annotation.SuppressLint;
import android.content.Intent;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.cdqj.cdqjpatrolandroid.config.CdqjInitDataConfig;
import com.cdqj.cdqjpatrolandroid.config.GlobalConfig;
import com.cdqj.cdqjpatrolandroid.http.ExceptionHandle;
import com.cdqj.cdqjpatrolandroid.base.App;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;
import com.cdqj.cdqjpatrolandroid.view.ui.main.LoginActivity;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by gj on 2018/5/11.
 * 该类仅供参考，实际业务Code, 根据需求来定义，
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private boolean isNeedCahe;

    public BaseSubscriber() {
    }

    public abstract void onResult(T t);

    @SuppressLint("MissingPermission")
    @Override
    public void onStart() {
        super.onStart();
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtils.isConnected()) {
            ToastBuilder.showShortWarning("无网络，读取缓存数据");
            onCompleted();
        }
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof ExceptionHandle.ResponeThrowable) {
            if (((ExceptionHandle.ResponeThrowable)e).code == 403 && !CdqjInitDataConfig.isLib) {
                GlobalConfig.loginStatus = false;
                ToastBuilder.showShortError("登录状态失效，请您重新登录");
                ActivityUtils.startActivity(new Intent(App.getInstance(), LoginActivity.class));
                ActivityUtils.finishAllActivities();
            } else if (((ExceptionHandle.ResponeThrowable)e).code == 500) {
                ((ExceptionHandle.ResponeThrowable)e).message = "服务器繁忙，请您稍后再试：500";
                onError((ExceptionHandle.ResponeThrowable) e);
            } else {
                onError((ExceptionHandle.ResponeThrowable) e);
            }
        } else {
            if (e instanceof HttpException) {
                if (((HttpException)e).code() == 403 && !CdqjInitDataConfig.isLib) {
                    GlobalConfig.loginStatus = false;
                    ToastBuilder.showShortError("登录状态失效，请您重新登录");
                    ActivityUtils.startActivity(new Intent(App.getInstance(), LoginActivity.class).putExtra("token_Invalid", true));
                    ActivityUtils.finishAllActivities();
                } else if (((HttpException)e).code() == 500) {
                    onError(ExceptionHandle.handleException(e));
                } else {
                    onError(ExceptionHandle.handleException(e));
                }
            } else {
                onError(ExceptionHandle.handleException(e));
            }

        }
    }

    public abstract void onError(ExceptionHandle.ResponeThrowable e);

    @Override
    public void onNext(T o) {
        onResult(o);
    }
}