package com.cdqj.cdqjpatrolandroid.utils;

import com.blankj.utilcode.util.SDCardUtils;

import java.io.File;

/**
 * 版权：成都千嘉科技公司所有
 *
 * @author lyf
 * 创建日期： 2019/8/16
 * 创建时间： 10:07
 * 描述：
 */
public class BasicConstant {

    /**
     * APP系统文件夹
     */
    public static final String APP_DIR = "cdqj";

    /**
     * APP系统文件夹地址
     */
    public static final String APP_PATH = SDCardUtils.getSDCardPathByEnvironment() + File.separator + APP_DIR;

    /**
     * 存放拍照图片的文件夹
     */
    public static final String APP_IMAGE_PATH = APP_PATH + File.separator + "image";

    /**
     * 图片选择最小数量
     */
    public static final int PICTURE_MIN_COUNT = 1;

    /**
     * 图片选择最大数量
     */
    public static final int PICTURE_MAX_COUNT = 9;

    /**
     * 文件是否允许选择已有文件/只能现场拍摄录制
     */
    public static final boolean FILE_MODEL = true;
}
