package com.lhf.gank.lhfgankclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * com.lhf.gank.lhfgankclient.utils
 * Created by zeratel3000
 * on 2015 09 15/9/17 10 09
 * description PreferencesSaver保存
 */
public class PreferencesSaver {

    public static final String PREFERENCES_FILE = "com.lhf.gankclient";

    public static void setStringAttr(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (value == null) {
            edit.remove(key);
        } else {
            edit.putString(key, value);
        }
        edit.commit();
    }

    public static String getStringAttr(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        return sp.getString(key, null);
    }

    public static void removeStringAttr(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        String value = getStringAttr(context, key);
        if (value != null) {
            edit.remove(key);
        }
        edit.commit();
    }

    public static void setBooleanAttr(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static boolean getBooleanAttr(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);

    }
    /**
     *
     * <p>Author: LHF</p>
     * <p> @date 2014年10月15日 下午3:59:35 </p>
     * <p>Description: 默认为true的</p>
     * @param context
     * @param key
     * @return
     */
    public static boolean getBooleanAttrDefaultTrue(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, true);
    }

    public static void setIntAttr(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static int getIntAttr(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        return sp.getInt(key, -1);
    }

    public static void setLongAttr(Context context, String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    public static long getLongAttr(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        return sp.getLong(key, -1);
    }

    /**
     * 以 item,item,item,的形式存储 ssq,ahk3,lhj, 双色球，快3，老虎机，
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static void putStrList(Context context, String key,
                                  ArrayList<String> value) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.size(); i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(value.get(i));

        }

        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, sb.toString());
        edit.commit();
    }

    public static ArrayList<String> getStrList(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES_FILE,
                Context.MODE_PRIVATE);
        String value = sp.getString(key, "");

        String[] strs = null;
        if (value.equals("")) {
            strs = new String[0];
        } else {
            strs = value.split(",");
        }

        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < strs.length; i++) {
            list.add(strs[i]);
        }

        return list;
    }
}
