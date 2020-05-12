package com.cdqj.cdqjpatrolandroid.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.blankj.utilcode.util.NetworkUtils;
import com.cdqj.cdqjpatrolandroid.event.NetworkChangeEvent;

import org.greenrobot.eventbus.EventBus;

public class NetworkConnectChangedReceiver extends BroadcastReceiver {

   private static final String TAG = "NetworkConnectChanged";
   @Override
   public void onReceive(Context context, Intent intent) {
       //**判断当前的网络连接状态是否可用*/
       boolean isConnected = NetworkUtils.isConnected();
       Log.d(TAG, "onReceive: 当前网络 " + isConnected);
       EventBus.getDefault().post(new NetworkChangeEvent(isConnected));
   }
}