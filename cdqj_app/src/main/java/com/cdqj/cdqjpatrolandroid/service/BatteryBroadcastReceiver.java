package com.cdqj.cdqjpatrolandroid.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cdqj.cdqjpatrolandroid.utils.Constant;
import com.cdqj.cdqjpatrolandroid.utils.PreferencesUtil;

/**
 * 电池监听广播
 * @author lyf
 */
public class BatteryBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		String action = intent.getAction();
		if (action.equals(Intent.ACTION_BATTERY_CHANGED) || action.equals(Intent.ACTION_BATTERY_LOW)
				|| action.equals(Intent.ACTION_BATTERY_OKAY)) {

			// 电池状态，返回是一个数字
			// BatteryManager.BATTERY_STATUS_CHARGING 表示是充电状态
			// BatteryManager.BATTERY_STATUS_DISCHARGING 放电中
			// BatteryManager.BATTERY_STATUS_NOT_CHARGING 未充电
			// BatteryManager.BATTERY_STATUS_FULL 电池满
			// 电池状态
			int status = intent.getIntExtra("status", 0);

			// 电池健康情况，返回也是一个数字
			// BatteryManager.BATTERY_HEALTH_GOOD 良好
			// BatteryManager.BATTERY_HEALTH_OVERHEAT 过热
			// BatteryManager.BATTERY_HEALTH_DEAD 没电
			// BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE 过电压
			// BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE 未知错误
			// 电池健康情况
			int health = intent.getIntExtra("health", 0);

			// 电池最大容量
			int scale = intent.getIntExtra("scale", 0);
			// 电池的电压
			int nVoltage = intent.getIntExtra("voltage", 0);
			// 电池的电量，数字
			int level = intent.getIntExtra("level", 0);
			// 电池的温度
			int temperature = intent.getIntExtra("temperature", 0);

			if (nVoltage != 0) {
				PreferencesUtil.putInt(Constant.BATTERY_LEVEL, level);
				// LogUtils.showDebug("电池电量：" + level + "%");

				//LogUtils.showDebug("voltage = " + nVoltage + " level = " + level + " temperature = " + temperature + "...success");
			} else {
				//LogUtils.showDebug("voltage = " + nVoltage + "...failed");
			}
		}

	}
}
