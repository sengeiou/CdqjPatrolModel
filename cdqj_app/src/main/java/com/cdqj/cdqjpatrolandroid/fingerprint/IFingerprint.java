package com.cdqj.cdqjpatrolandroid.fingerprint;

import android.app.Activity;
import android.content.Context;

/**
 * 版权：成都千嘉科技公司所有
 *
 * @author lyf
 * 创建日期： 2020/4/18
 * 创建时间： 11:42
 * 描述：
 */
public interface IFingerprint {

    /**
     * 检测指纹硬件是否可用，及是否添加指纹
     * @param context context
     * @param callback callback
     * @return boolean
     */
    boolean canAuthenticate(Context context, FingerprintCallback callback);

    /**
     * 初始化并调起指纹验证
     *
     * @param context context
     * @param verificationDialogStyleBean verificationDialogStyleBean
     * @param callback callback
     */
    void authenticate(Activity context, VerificationDialogStyleBean verificationDialogStyleBean, FingerprintCallback callback);

}
