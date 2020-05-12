package com.cdqj.cdqjpatrolandroid.fingerprint;

/**
 * 版权：成都千嘉科技公司所有
 *
 * @author lyf
 * 创建日期： 2020/4/18
 * 创建时间： 11:40
 * 描述：回调接口
 */
public interface FingerprintCallback {

    /**
     * 无指纹硬件或者指纹硬件不可用
     */
    void onHwUnavailable();

    /**
     * 未添加指纹
     */
    void onNoneEnrolled();

    /**
     * 验证成功
     */
    void onSucceeded();

    /**
     * 验证失败
     */
    void onFailed();

    /**
     * 密码登录
     */
    void onUsepwd();

    /**
     * 取消验证
     */
    void onCancel();
}
