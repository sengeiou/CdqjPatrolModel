package com.cdqj.cdqjpatrolandroid.utils;

import android.app.Activity;
import android.os.Handler;

import com.blankj.utilcode.util.AppUtils;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmDialogListener;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.ConfirmSelectDialog;
import com.cdqj.cdqjpatrolandroid.comstomview.dialogplus.NavSelectDialog;
import com.cdqj.cdqjpatrolandroid.gis.util.CoordinateUtils;
import com.cdqj.cdqjpatrolandroid.gis.util.NavUtil;

/**
 * Created by lyf on 2018/9/17 11:36
 *
 * @author lyf
 * desc：导航使用工具类
 */
public class NavigationUtil {


    /**
     * 导航弹窗
     */
    public static void showNavWindow(double x, double y, Activity activity) {
        // 成都 104.066513,30.572262 navX = 30.572262, navY = 104.066513
        if (x == 30.572262 && y == 104.066513) {
            ToastBuilder.showShortWarning("导航坐标获取失败");
            return;
        }

        new NavSelectDialog(activity)
                .setOnItemClick((dialog, item, view, position) -> {
                    switch (position) {
                        case 0:
                            // 百度 是否安装
                            if (AppUtils.isAppInstalled("com.baidu.BaiduMap")) {
                                double [] point = CoordinateUtils.wgs84ToBd09(y, x);
//                                NavUtil.openBaiduNavi(point[1], point[0], activity, "", "DIS");
                                NavUtil.openBaiduDirection(point[1], point[0], activity, "");
                            } else {
                                new Handler().postDelayed(() -> new ConfirmSelectDialog(activity)
                                        .setContentStr("您当前未安装百度地图，是否下载？")
                                        .setListener(new ConfirmDialogListener() {
                                            @Override
                                            public void onYesClick() {
                                                NavUtil.gotoMarket("com.baidu.BaiduMap", activity);
                                            }

                                            @Override
                                            public void onNoClick() {

                                            }
                                        }).initView().show(), Constant.DIALOG_TIME);
                            }
                            break;
                        case 1:
                            // 高德 是否安装
                            if (AppUtils.isAppInstalled("com.autonavi.minimap")) {
                                double [] point = CoordinateUtils.wgs84ToGcj02(y, x);
//                                NavUtil.openGaoDeNavi(AppUtils.getAppPackageName(),
//                                        point[1], point[0], activity, 0, 0);
                                NavUtil.openGaoDeDirection(point[1], point[0], "", activity);
                            } else {
                                new Handler().postDelayed(() -> new ConfirmSelectDialog(activity)
                                        .setContentStr("您当前未安装高德地图，是否下载？")
                                        .setListener(new ConfirmDialogListener() {
                                            @Override
                                            public void onYesClick() {
                                                NavUtil.gotoMarket("com.autonavi.minimap", activity);
                                            }

                                            @Override
                                            public void onNoClick() {

                                            }
                                        }).initView().show(), Constant.DIALOG_TIME);
                            }
                            break;
                        default:break;
                    }
                })
                .initDialog()
                .show();
    }
}
