package com.cdqj.cdqjpatrolandroid.fingerprint;

import android.os.Build;

/**
 * 版权：成都千嘉科技公司所有
 *
 * @author lyf
 * 创建日期： 2020/4/18
 * 创建时间： 11:43
 * 描述：版本判断
 */
public class AndrVersionUtil {
    /**
     * 高于Android P（9.0）
     *
     * @return boolean
     */
    public static boolean isAboveAndrP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    /**
     * 高于Android M（6.0）
     *
     * @return boolean
     */
    public static boolean isAboveAndrM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
