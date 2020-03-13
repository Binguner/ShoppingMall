package com.learnandroid.shoppingmall.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * com.learnandroid.shoppingmall.utils
 * Author: binguner
 * Date: 2020-03-06 14:35
 */
public class CacheUtils {

    private static String SP_NAME = "MyMall";


    public static String getString(Context context, String key) {
        String resule = "";
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        resule = sharedPreferences.getString(key, "");
        return resule;
    }

    public static void putSting(Context context, String key,String value) {
        if (!TextUtils.isEmpty(value)) {
            SharedPreferences.Editor editor = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
            editor.putString(key, value);
            editor.commit();
        }
    }
}
