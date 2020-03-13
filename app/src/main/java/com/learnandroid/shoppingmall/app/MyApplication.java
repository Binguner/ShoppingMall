package com.learnandroid.shoppingmall.app;

import android.app.Application;
import android.content.Context;

/**
 * com.learnandroid.shoppingmall.app
 * Author: binguner
 * Date: 2020-03-05 22:33
 */
public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
