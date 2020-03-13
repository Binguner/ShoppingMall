package com.learnandroid.shoppingmall.user.fragment;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.learnandroid.shoppingmall.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends BaseFragment {

    private static final String TAG = UserFragment.class.getSimpleName();
    private TextView textView;

    public UserFragment() {

    }

    @Override
    public View initView() {
        Log.d(TAG, "个人中心视图被初始化了");
        textView = new TextView(mContext);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }


    @Override
    public void initData() {
        super.initData();
        Log.d(TAG, "个人中心页面数据被初始化了");
        textView.setText("个人中心");
    }
}
