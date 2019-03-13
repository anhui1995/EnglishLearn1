package xin.xiaoa.englishlearn.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtils {

    /**
     * 普通字段存放地址
     */
    private static String EnglishLearn = "englishlearn";

    @SuppressWarnings("unused")
    public static String getSharePreStr(Context mContext, String field) {
        SharedPreferences sp = mContext.getSharedPreferences(EnglishLearn, 0);
        String s;//如果该字段没对应值，则取出字符串0
        s = sp.getString(field, "");
        return s;
    }

    //取出whichSp中field字段对应的int类型的值
    @SuppressWarnings("unused")
    public static int getSharePreInt(Context mContext, String field) {
        SharedPreferences sp = mContext.getSharedPreferences(EnglishLearn, 0);
        int i;//如果该字段没对应值，则取出0
        i = sp.getInt(field, 0);
        return i;
    }

    //取出whichSp中field字段对应的boolean类型的值
    @SuppressWarnings("unused")
    public static boolean getSharePreBoolean(Context mContext, String field) {
        SharedPreferences sp = mContext.getSharedPreferences(EnglishLearn, 0);
        boolean i;//如果该字段没对应值，则取出0
        i = sp.getBoolean(field, false);
        return i;
    }

    //保存string类型的value到whichSp中的field字段
    @SuppressLint("ApplySharedPref")
    @SuppressWarnings("unused")
    public static void putSharePre(Context mContext, String field, String value) {
        SharedPreferences sp = mContext.getSharedPreferences(EnglishLearn, 0);
        sp.edit().putString(field, value).commit();
    }

    //保存int类型的value到whichSp中的field字段
    @SuppressLint("ApplySharedPref")
    @SuppressWarnings("unused")
    public static void putSharePre(Context mContext, String field, int value) {
        SharedPreferences sp = mContext.getSharedPreferences(EnglishLearn, 0);
        sp.edit().putInt(field, value).commit();
    }

    //保存boolean类型的value到whichSp中的field字段
    @SuppressWarnings("unused")
    @SuppressLint("ApplySharedPref")
    public static void putSharePre(Context mContext, String field, Boolean value) {
        SharedPreferences sp = mContext.getSharedPreferences(EnglishLearn, 0);
        sp.edit().putBoolean(field, value).commit();
    }

    //清空保存的数据
    @SuppressWarnings("unused")
    @SuppressLint("ApplySharedPref")
    public static void clearSharePre(Context mContext) {
        try {
            SharedPreferences sp = mContext.getSharedPreferences(EnglishLearn, 0);
            sp.edit().clear().commit();
        } catch (Exception e) {
            System.out.println("clearSharePre问题");
        }
    }


}
