package com.cdqj.cdqjpatrolandroid.utils;

import com.blankj.utilcode.util.StringUtils;

import java.util.Arrays;

public class StringUrlUtil {

    public static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    public static boolean isImgUrl(String url) {
        if (url.endsWith("png") || url.endsWith("PNG") || url.endsWith("JPG") || url.endsWith("jpg")) {
            return true;
        }
        return false;
    }

    public static String transHttpUrl(String url) {
        if (url.startsWith("http:") || url.startsWith("https:")) {
            return url;
        } else {
            return PreferencesUtil.getString(Constant.FILE_SERVICE_PATH) + url;
        }
    }

    public static String transHttpUrlAndOnlyOne(String url) {
        String path = "";
        if (!StringUtils.isTrimEmpty(url)) {
            if (url.contains(",")) {
                path = url.split(",")[0];
            } else {
                path = url;
            }
        }
        if (path.startsWith("http:") || path.startsWith("https:")) {
            return path;
        } else {
            return PreferencesUtil.getString(Constant.FILE_SERVICE_PATH) + path;
        }
    }
}
