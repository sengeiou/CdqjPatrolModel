package com.cdqj.cdqjpatrolandroid.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.blankj.utilcode.util.ObjectUtils;
import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;
import com.cdqj.cdqjpatrolandroid.utils.ToastBuilder;

/**
 * Created by lyf on 2017/9/1.
 * 手机网络状态实时监听
 * @author lyf
 */
public class NetWorkStateReceiver extends BroadcastReceiver {

    @SuppressLint({"MissingPermission", "UnsafeProtectedBroadcastReceiver"})
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("网络状态发生变化");
        //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                haveNetWork();
            } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                haveNetWork();
            } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                haveNetWork();
            } else {
                noHaveNetWork();
                ToastBuilder.showShortWarning("WIFI已断开,移动数据已断开");
            }
        //API大于23时使用下面的方式进行网络监听
        } else {
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取所有网络连接的信息
            assert connMgr != null;
            Network[] networks = connMgr.getAllNetworks();
            //用于存放网络连接信息
            StringBuilder sb = new StringBuilder();
            //通过循环将网络信息逐个取出来
            if (networks.length > 0) {
                for (int i = 0; i < networks.length; i++) {
                    //获取ConnectivityManager对象对应的NetworkInfo对象
                    NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                    String isTure;
                    if (networkInfo != null && networkInfo.isConnected()) {
                        isTure = "连接";
                    } else {
                        isTure = "断开";
                    }
                    if (ObjectUtils.isNotEmpty(networkInfo)) {
                        sb.append(networkInfo.getTypeName() + " 状态： " + isTure);
                    }
                }
                haveNetWork();
            } else {
                noHaveNetWork();
                sb.append("当前未发现网络连接");
            }
            ToastBuilder.showShort(sb.toString());
        }
    }

    /**
     * 网络存在
     */
    private void haveNetWork(){
        PreferencesUtil.putBoolean(Constant.NET_STATE,true);
    }

    /**
     * 网络断开
     */
    public void noHaveNetWork(){
        PreferencesUtil.putBoolean(Constant.NET_STATE,false);
    }


    /**
     * 检查当前网络是否可用
     *
     * @param activity activity
     * @return boolean
     */
    public static boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            @SuppressLint("MissingPermission") NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态==="
                            + networkInfo[i].getState());
                    System.out.println(i + "===类型==="
                            + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
