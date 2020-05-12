package com.cdqj.cdqjpatrolandroid.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.Utils;
import com.cdqj.cdqjpatrolandroid.comstomview.RoundHeader;
import com.cdqj.cdqjpatrolandroid.image.show.ImageDetailShowLoader;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.R;
import com.cdqj.cdqjpatrolandroid.http.OkLogUtil;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.orhanobut.logger.BuildConfig;
import com.previewlibrary.ZoomMediaLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;


/**
 * Created by lyf on 2018/4/25 16:20
 *
 * @author lyf
 * desc：
 */
public class App extends Application {

    private static App mInstance;
    /*
     * 设置SmartRefreshLayout全局主题
     * static 代码段可以防止内存泄露
     */
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            //全局设置主题颜色
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
            //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            return new RoundHeader(context);
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20);
        });
        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
            //是否在刷新的时候禁止列表的操作
            layout.setDisableContentWhenRefresh(true);
            //是否在加载的时候禁止列表的操作
            layout.setDisableContentWhenLoading(true);
            //是否启用列表惯性滑动到底部时自动加载更多
            layout.setEnableAutoLoadMore(false);
            layout.setEnableOverScrollBounce(false);
        });
    }

    public static App getInstance() {
        if (mInstance == null) {
            synchronized (App.class) {
                mInstance = new App();
            }
        }
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ZoomMediaLoader.getInstance().init(new ImageDetailShowLoader());
        Utils.init(mInstance);
        // 初始化Looger工具
        OkLogUtil.init(BuildConfig.DEBUG);
        // 初始化log
        initLog();
        initRealm();
        CrashUtils.init(Constant.APP_PATH + File.separator + "loge");
//            CrashUtils.init((crashInfo, e) -> {
//                String mes = e.getMessage();
//                if (mes != null && mes.contains( "EGL_BAD_CONFIG")) {
//                    LogUtil.e("该机型不支持Arcgis地图");
//                    ToastBuilder.showLongError("该机型不支持Arcgis地图");
//                } else {
//
//                }
//            });
    }

    @SuppressLint("SimpleDateFormat")
    private void initLog() {
        if (PreferencesUtil.getBoolean(Constant.INSTALL_APP)) {
            // 版本更新时删除历史日志
            FileUtils.deleteAllInDir(Constant.APP_PATH + File.separator + "Log");
            PreferencesUtil.putBoolean(Constant.INSTALL_APP, false);
        }
        LogUtils.getConfig()
                .setGlobalTag(Constant.APP_DIR)
                .setDir(Constant.APP_PATH + File.separator + "Log")
                .setLog2FileSwitch(true)
                .setSaveDays(5)
                .setFilePrefix(AppUtils.getAppName() + TimeUtils.date2String(new Date(), new SimpleDateFormat("yyyy-MM-dd")));
    }

    private void initRealm() {
        Realm.init(this);
        RealmMigration migration = (realm, oldVersion, newVersion) -> {
            RealmSchema schema = realm.getSchema();
            if (oldVersion == Constant.SYSDIC_VERSION - 1) {
               /* schema.get("DicCacheDaoData")
                        .renameField("test", "testX");*/
              /*  schema.get("DicCacheDaoData")
                        .addField("text", String.class)
                        .addField("attributes", String.class)
                        .addField("selected", boolean.class);*/
                oldVersion++;
            }
        };
        RealmConfiguration config = new RealmConfiguration.Builder()
                //文件名
                .name(Constant.APP_DIR + ".realm")
                //版本号
                .schemaVersion(Constant.SYSDIC_VERSION)
                // 测试用（数据库有更新自动删除）
                .deleteRealmIfMigrationNeeded()
                .migration(migration)
                .build();
        Realm.setDefaultConfiguration(config);
    }

    /***
     * 遍历退出
     */
    public void exit() {
        try {
            ActivityUtils.finishAllActivities();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new Handler().postDelayed(() -> android.os.Process.killProcess(android.os.Process.myPid()), 200);
        }
    }
}
