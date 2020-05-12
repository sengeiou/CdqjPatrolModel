package com.cdqj.cdqjpatrolandroid.utils;

import android.annotation.SuppressLint;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SDCardUtils;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by lyf on 2018/8/10 10:12
 *
 * @author lyf
 * desc：常量类
 */
@SuppressLint("SimpleDateFormat")
public class Constant {

    /**
     * 包名
     */
    public static final String ANDROID_CHANNEL_ID = AppUtils.getAppPackageName();

    /**
     * APP名称
     */
    public static final String ANDROID_CHANNEL_NAME = AppUtils.getAppName();

    /**
     * 重新安装了APP的标识
     */
    public static final String INSTALL_APP = "install_app";

    /**
     * 请求地址
     * http://121.30.230.6:8090/
     */
    public static final String REQUEST_ADDRESS = "request_address";
//     大同正式环境
//    public static final String REQUEST_ADDRESS_DEFAULT = "http://111.53.11.42:8090/";
    // 泸州正式环境
//    public static final String REQUEST_ADDRESS_DEFAULT = "http://218.89.135.107:9090/";
    /*
     * 绵阳巡线系统：
     * 内网：http://192.168.100.176:8080
     * 外网：http://110.186.74.48:9999
     */
//    public static final String REQUEST_ADDRESS_DEFAULT = "http://110.186.74.48:9999/";
//    public static final String REQUEST_ADDRESS_DEFAULT = "http://10.121.54.41:8095/";//测试
    // 唐昌正式环境 172.16.126.21  http://portal.cdgas.com/
//    public static final String REQUEST_ADDRESS_DEFAULT = "http://172.16.126.21/";
    // 通惠正式环境 http://58.18.36.34:9080/
    public static final String REQUEST_ADDRESS_DEFAULT = "http://58.18.36.34:9080/";

    /**
     * 文件服务器地址
     */
    public static final String FILE_SERVICE_PATH = "file_service_path";
//     大同正式环境
//    public static final String FILE_SERVICE_PATH_DEFAULT = "http://111.53.11.42:8090/";
    // 泸州正式环境
//    public static final String FILE_SERVICE_PATH_DEFAULT = "http://218.89.135.107:9090/";
    // 绵阳正式环境
//    public static final String FILE_SERVICE_PATH_DEFAULT = "http://110.186.74.48:10000/";
//    public static final String FILE_SERVICE_PATH_DEFAULT = "http://10.121.54.41:8095/";//测试
    // 唐昌正式环境 172.16.126.17  http://EMCportalftp.cdgas.com/
//    public static final String FILE_SERVICE_PATH_DEFAULT = "http://172.16.126.17/";
    // 通惠正式环境 http://58.18.36.34:9080/
    public static final String FILE_SERVICE_PATH_DEFAULT = "http://58.18.36.34:9080/";

    // 唐昌
    public static String appCompany = "";
    /*坐标转换烽火地址*/
    //测试
//    public static String FH_INTERNET_SERVER_ADDRESS_TEST = "http://172.16.10.10:8001/";
    //正式
    public static String FH_INTERNET_SERVER_ADDRESS_TEST = "http://172.16.126.63:8001/";

    /**
     * GIS服务器
     */
//    public static final String GIS_ADDRESS_DEFAULT = "http://111.53.11.42:8090/";
    // 绵阳GIS服务器地址
    public static final String GIS_ADDRESS_DEFAULT = "http://110.186.74.48:6080/";
    // 唐昌GIS服务器地址
//    public static final String GIS_ADDRESS_DEFAULT = "http://172.16.126.94:6080/";
    /**
     * 百度配置
     */
//    public static final String BAIDU_AK = "9BVy27x1lME8PFwbWYZWcsEcbQZs26CV";   //甘
    public static final String BAIDU_AK = "9BVy27x1lME8PFwbWYZWcsEcbQZs26CV";  //刘
//    public static final String BAIDU_AK = "Ezdafwl8Fgkgc3YO4lCeD9umEGkIcNl7";  //林
    //甘 测试
//    public static final String BAIDU_SHA1 = "1E:67:65:70:53:2E:B7:C5:F0:6E:67:23:BC:F8:CD:58:1C:D2:DC:30";
    //林 测试
//    public static final String BAIDU_SHA1 = "34:A4:35:3C:8E:EE:E4:7F:51:7B:BB:3E:D9:34:13:AA:EA:42:A5:9E";
    //刘 测试
//    public static final String BAIDU_SHA1 = "F3:1F:F6:C5:58:47:E9:5A:EE:6B:2C:4B:BC:78:B2:EC:D2:94:FF:F1";
    //正式
    public static final String BAIDU_SHA1 = "40:51:7A:63:44:D6:A2:B3:42:CD:28:0B:28:88:2F:71:02:7E:05:EA";

    /**
     * 用户名
     */
    public static final String USER_NAME = "user_name";

    /**
     * 用户ID
     */
    public static final String USER_ID = "user_id";

    /**
     * 域ID
     */
    public static final String DOMAIN_ID = "domain_id";
    /**
     * 泸州ID 1
     * 1	3			2019/6/17 10:09:02
     * 2	1	SYSTEM	系统管理	2018/5/23 11:03:02
     * 3	2	泸州华润兴泸燃气	燃气公司	2018/12/13 16:57:27
     * 4	6681	合江星焰燃气		2019/6/17 10:09:09
     * <p>
     * 其他
     * 大同，绵阳等
     */
    public static final String DOMAIN_NAME_ID = "domain_name_id";

    /**
     * 版本更新通知栏标识
     */
    public static final int VERSION_NOTIFICTION_FLAG = 9;

    /**
     * 密码
     */
    public static final String PASS_WORD = "pass_word";
    /**
     * 程序需要使用的密码
     */
    public static final String PASS_WORD_HIDE = "pass_word_hide";
    /**
     * 指纹登录
     */
    public static final String FINGERPRINT = "fingerprint";

    /**
     * 指纹登录不提示
     */
    public static final String FINGERPRINT_ESC = "fingerprint_esc";

    /**
     * 真实姓名
     */
    public static final String TRUE_NAME = "true_name";

    /**
     * 是否有网络
     */
    public static final String NET_STATE = "net_state";

    /**
     * token
     */
    public static final String TOKEN = "token";
    /**
     * appVersion
     */
    public static final String APP_VERSION = "AppVersion";
    /**
     * 弹窗间隔时间
     */
    public static final int DIALOG_TIME = 350;


    /**
     * 图片选择最大数量
     */
    public static final int PICTURE_MAX_COUNT = 9;

    /**
     * APP系统文件夹
     */
    public static final String APP_DIR = "cdqj";
    /**
     * 字典表数据库版本
     */
    public static final int SYSDIC_VERSION = 13;
    /**
     * 字典表数据库版本
     */
    public static final String SYSDIC_VERSION_PREFENCE = "sysdicversion";
    /**
     * 隐患类型数据库版本
     */
    public static final String HDPATROL_VERSION_PREFENCE = "hdpatrolversion";
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
     * 文件是否允许选择已有文件/只能现场拍摄录制
     */
    public static final boolean FILE_MODEL = true;

    /**
     * 坐标保存数据库执行间隔
     */
    public static final Long SAVE_POINT_TIME = 10 * 1000L;

    /**
     * 终端信息保存数据库执行间隔
     */
    public static final Long SAVE_TERMINAL_INFO_TIME = 2 * 60 * 60 * 1000L;

    /**
     * 坐标上传执行间隔
     */
    public static final Long UPDATE_POINT_TIME = 3 * 60 * 1000L;

    /**
     * 上班提示
     */
    public static final Long DO_WORK_LOG_TIME = 10 * 60 * 1000L;

    /**
     * 坐标扫描数据库间隔
     */
    public static final Long DEL_POINT_SUBMIT_TIME = 2 * 60 * 60 * 1000L;

    /**
     * 计算单位/天
     */
    public static final Long ONE_DAY = 24 * 60 * 60 * 1000L;

    /**
     * 坐标数据库数据删除间隔
     */
    public static final Long DEL_POINT_TIME = 5 * 24 * 60 * 60 * 1000L;

    /**
     * 最大速度40米/秒--->144KM/H
     */
    public static final double MAX_SPEED = 40;

    /**
     * 最大采集时间差（暂时一分钟）
     */
    public static final int MAX_TIME = 60 * 1000;

    /**
     * 最大距离 3KM
     */
    public static final int MAX_DISTANCE = 3 * 1000;

    /**
     * 坐标X
     */
    public static final String LOCATION_LATITUDE = "location_latitude";

    /**
     * 坐标Y
     */
    public static final String LOCATION_LONGITUDE = "location_longitude";

    /**
     * 地址信息
     */
    public static final String LOCATION_ADDRESS = "location_address";

    /**
     * 卫星数量
     */
    public static final String LOCATION_SATELLITE_NUMBER = "location_satellite_number";

    /**
     * 方向
     */
    public static final String LOCATION_DIRECTION = "location_direction";

    /**
     * 速度
     */
    public static final String LOCATION_SPEED = "location_speed";

    /**
     * 运营商
     */
    public static final String LOCATION_OPERATOR_NAME = "location_operator_name";

    /**
     * 手机信号
     */
    public static final String PHONE_SINGLE = "phone_single";

    /**
     * 信号强弱标识
     */
    public static final String LOCATION_SATELLITE_NUMBER_FLAG = "location_satellite_number_flag";

    /**
     * 电池电量
     */
    public static final String BATTERY_LEVEL = "battery_level";

    /**
     * 拍照标识
     */
    public static final int REQUEST_CODE_IMAGE_CAPTURE = 500;

    /**
     * 拍照标识
     */
    public static final int REQUEST_CODE_IMAGE_CHECK = 501;

    /**
     * 图片质量压缩的最大大小/byte
     */
    public static final long IMAGE_MAX_SIZE = 60 * 8 * 1024L;

    /**
     * 图片压缩的采样率
     */
    public static final int IMAGE_MAX_SAMPLE_SIZE = 4;

    /**
     * 图片大小压缩的宽高
     */
    public static final float IMAGE_WHITE = 800, IMAGE_HEIGHT = 1000;

    /**
     * 文件类型标识-图片
     */
    public static final int IMG_TYPE = 1;

    /**
     * 文件类型标识-音频
     */
    public static final int AUDIO_TYPE = 2;

    /**
     * 文件类型标识-视频
     */
    public static final int VIDEO_TYPE = 3;

    /**
     * 天地图key
     */
    public static String TDT_KEY = "dba026517c19fb0017e1bd30cd100473";

    /**
     * reference 坐标系参数 根据各个项目
     * 4326 -WGPS84坐标系
     * 4490 -2000坐标系
     */
    public static int SRID_2000 = 4490;

    /**
     * 时间格式
     */
    public static final SimpleDateFormat DATE_FORMAT_YMDHM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat DATE_FORMAT_YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_YMD = new SimpleDateFormat("yyyy-MM-dd");
}
