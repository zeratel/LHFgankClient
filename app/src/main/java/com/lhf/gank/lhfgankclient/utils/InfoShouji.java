package com.lhf.gank.lhfgankclient.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;


/**
 *  获取手机信息
 */
public class InfoShouji {
	
	/**
	 * 获取手机imei
	 * 
	 * @return
	 */
	public static String getImei(Context activity) {
		return ((TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}
	
	/**
	 * 获取手机imsi
	 * 
	 * @return
	 */
	public static String getImsi(Context activity) {
		return ((TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
	}
	
	/**
	 * 获取手机号码
	 * 
	 * @return
	 */
	public static String getPhoneNumber(Context activity) {
		String num = ((TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
		if(num == null)
			num = "";
		else if(num.startsWith("+86"))
			num = num.substring("+86".length());
		return num;
	}
	
	/**
	 * 获取手机型号
	 * 
	 * @return
	 */
	public static String getShoujixinghao() {
		return android.os.Build.DEVICE;
	}
	
	/**
	 * 获取Android版本号
	 * 
	 * @return
	 */
	public static String getAndroidVersion() {
		return android.os.Build.VERSION.RELEASE;
	}
	
	/**
	 * 获取短信中心号码，收件箱中的短信中心
	 * 
	 * @return
	 */
//	public static String getSMSC(Activity activity) {
//		SmsUtil sms=SmsUtil.getInstance(activity);
//        String centerNumber=sms.getSmsCenter();
//		return centerNumber;
//	}
	/**
	 * 获取手机MAC地址：
	 * 
	 * @param context
	 * @return
	 */
	public static String getMacAddress(Context context) {
		String result = "";
		try {
			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			result = wifiInfo.getMacAddress();
//			WifiInfo info = (null == wifiManager ? null : wifiManager.getConnectionInfo());
//			if (null != info) {
//			   result = info.getMacAddress();
//			   result += "   ip = "+Integer.toString(info.getIpAddress());
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	 /**
     * 返回移动终端--插入的手机卡状态
     * 
     * SIM_STATE_UNKNOWN = 0 SIM卡未知
     * SIM_STATE_ABSENT = 1 SIM卡未找到
     * SIM_STATE_PIN_REQUIRED = 2 SIM卡PIN被锁定，需要User PIN解锁
     * SIM_STATE_PUK_REQUIRED = 3 SIM卡PUK被锁定，需要User PUK解锁
     * SIM_STATE_NETWORK_LOCKED = 4 SIM卡网络被锁定，需要Network PIN解锁
     * SIM_STATE_READY = 5 SIM卡可用
     */
	public static int getPhoneCardState(Context context){
		return getManager(context).getSimState();
	}
	
	public static TelephonyManager getManager(Context context){
		return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
	}
	
	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			String version = info.versionName;
			return  version;
		} catch (Exception e) {
			e.printStackTrace();
			return  "1.1";
		}
	}
	
}
