package com.example.a00room_practice.Miscellaneous;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;


public class SharedPreferencesClass {
    SharedPreferences prefs;
    Context context;

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    public static void insertDataString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void insertDataInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void insertDataLong(Context context, String key, long value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static void insertStringSet(Context context, String key, Set<String> value) {
        getPrefs(context).edit().putStringSet(key, value).commit();
    }

    public static String retriveDataString(Context context, String key, String defValue) {
        return getPrefs(context).getString(key, defValue);
    }

    public static int retriveDataInt(Context context, String key, int defValue) {
        return getPrefs(context).getInt(key, defValue);
    }

    public static long retriveDataLong(Context context, String key, long defValue) {
        return getPrefs(context).getLong(key,defValue);
    }

    public static Set<String> getStringSet(Context context, String key) {
        return getPrefs(context).getStringSet(key, null);
    }

    public static void deleteData(Context context, String key) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.remove(key);
        editor.commit();
    }

    public static Boolean retriveDataBoolean(Context context, String key, Boolean defValue) {
        return getPrefs(context).getBoolean(key, defValue);
    }

    public static void insertBoolean(Context context, String key, Boolean value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}


