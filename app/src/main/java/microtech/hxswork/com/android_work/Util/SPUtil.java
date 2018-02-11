package microtech.hxswork.com.android_work.Util;

import android.content.SharedPreferences;

import microtech.hxswork.com.android_work.MyApplication;


/**
 * SharedPreferences 封装类
 * 
 * @author zhaoyi
 * 
 */
public class SPUtil {
	/**
	 * 保存在手机里面的文件名
	 */
	public static final String SPNAME = "config";

	public static final String LOCALIP = "localip";

	//用户登录手机号
	public static final String PHONE = "loginphone";
	//用户登录密码
	public static final String LOGIN_PSD = "loginpsd";
	//用户密码保存标记
	public static final String SAVEFLAG = "saveflag";

	private static SharedPreferences sp;

	public static void saveBoolean(String key, boolean bool) {
		if (sp == null)
			//sp = MyApplication.appContext.getSharedPreferences(SPNAME, 0);

		sp.edit().putBoolean(key, bool).commit();
	}
	public static boolean getBoolean(String key, boolean defValue) {
		if (sp == null)
			sp = MyApplication.appContext.getSharedPreferences(SPNAME, 0);

		return sp.getBoolean(key, defValue);
	}

	public static void saveString(String key, String defValue) {
		if (sp == null)
			sp = MyApplication.appContext.getSharedPreferences(SPNAME, 0);

		sp.edit().putString(key, defValue).commit();
	}
	public static String getString(String key, String defValue) {

		if (sp == null) {
			sp = MyApplication.appContext.getSharedPreferences(SPNAME, 0);
		}

		return sp.getString(key, defValue);
	}

	public static void saveInt(String key, int defValue) {
		if (sp == null)
			sp = MyApplication.appContext.getSharedPreferences(SPNAME, 0);

		sp.edit().putInt(key, defValue).commit();
	}
	public static int getInt(String key, int defValue) {

		if (sp == null) {
			sp = MyApplication.appContext.getSharedPreferences(SPNAME, 0);
		}

		return sp.getInt(key, defValue);
	}
}
