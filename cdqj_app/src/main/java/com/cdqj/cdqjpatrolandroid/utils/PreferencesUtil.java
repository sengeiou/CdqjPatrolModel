package com.cdqj.cdqjpatrolandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.cdqj.cdqjpatrolandroid.base.App;

/**
 * @author
 * @version 1.0.0
 * @desc 数据存储相关类
 * @date 2015年5月11日 下午2:43:18
 */
public class PreferencesUtil {

    public static SharedPreferences getSharedPreferences() {
        return App.getInstance()
                .getSharedPreferences(Constant.APP_DIR, Context.MODE_PRIVATE);
    }

    public static String getString(String key) {
        return getSharedPreferences().getString(key, "");
    }

    public static String getString(String key, String deStr) {
        return getSharedPreferences().getString(key, deStr);
    }

    public static Boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    public static Boolean getBoolean(String key, boolean b) {
        return getSharedPreferences().getBoolean(key, b);
    }

    public static void putString(String key, String value) {
        getSharedPreferences().edit().putString(key, value).apply();
    }

    public static void remove(String key) {
        getSharedPreferences().edit().remove(key).apply();
    }

    public static void putBoolean(String key, Boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).apply();
    }

    public static int getInt(String key) {
        return getSharedPreferences().getInt(key, 0);
    }

    public static int getInt(String key, int defaultValue) {
        return getSharedPreferences().getInt(key, defaultValue);
    }

    public static void putInt(String key, int value) {
        getSharedPreferences().edit().putInt(key, value).apply();
    }

    public static Long getLong(String key) {
        return getSharedPreferences().getLong(key, 0);
    }

    public static void putLong(String key, Long value) {
        getSharedPreferences().edit().putLong(key, value).apply();
    }
}
