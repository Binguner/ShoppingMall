package com.learnandroid.shoppingmall.community.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learnandroid.shoppingmall.R;
import com.learnandroid.shoppingmall.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends BaseFragment {

    private static final String TAG = CommunityFragment.class.getSimpleName();
    private TextView textView;
    public CommunityFragment() {
    }




    @Override
    public View initView() {
        Log.d(TAG, "发现页面被初始化了");
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        Log.d(TAG, "发现页面数据被初始化了");
        textView.setText("发现");

    }
}
