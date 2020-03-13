package com.learnandroid.shoppingmall.home.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.learnandroid.shoppingmall.R;
import com.learnandroid.shoppingmall.base.BaseFragment;
import com.learnandroid.shoppingmall.home.adapter.HomeRecycleAdapter;
import com.learnandroid.shoppingmall.home.bean.ResultBeanData;
import com.learnandroid.shoppingmall.user.MessageCenterActivity;
import com.learnandroid.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView rv_home;
    private HomeRecycleAdapter homeRecycleAdapter;
    private ResultBeanData resultBeanData;
    private ImageButton ib_top;
    private TextView tv_search_home;
    private TextView tv_message_home;

    public HomeFragment() {

    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_home, null);
        rv_home = view.findViewById(R.id.rv_home);
        ib_top = view.findViewById(R.id.ib_top);
        tv_search_home = view.findViewById(R.id.tv_search_home);
        tv_message_home = view.findViewById(R.id.tv_message_home);
        return view;
    }

    private void initListener() {
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv_home.scrollToPosition(0);
            }
        });
        tv_search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "搜索", Toast.LENGTH_LONG).show();
            }
        });
        tv_message_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageCenterActivity.class);
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    private void getDataFromNet() {
        /*OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Constants.HOME_URL).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response != null) {
                    processData(response.body().string());
                    homeRecycleAdapter = new HomeRecycleAdapter(mContext, resultBeanData);
                    rv_home.setAdapter(homeRecycleAdapter);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
                    gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int i) {
                            if (i <= 3) {
                                ib_top.setVisibility(View.GONE);
                            } else {
                                ib_top.setVisibility(View.VISIBLE);
                            }
                            return 1;
                        }
                    });
                    rv_home.setLayoutManager(gridLayoutManager);
                    initListener();
                }
            }
        });*/
        OkHttpUtils.get().url(Constants.HOME_URL).id(100).build().execute(new MyStringCallBack());
    }

    public class MyStringCallBack extends StringCallback {

        @Override
        public void onError(okhttp3.Call call, Exception e, int id) {
            Log.d(TAG, "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 100:
                    if (response != null) {
                        processData(response);
                        homeRecycleAdapter = new HomeRecycleAdapter(mContext, resultBeanData);
//                        Log.d(TAG, "onResponse: setAdapter");
                        rv_home.setAdapter(homeRecycleAdapter);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
                        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int i) {
                                if (i <= 3) {
                                    ib_top.setVisibility(View.GONE);
                                } else {
                                    ib_top.setVisibility(View.VISIBLE);
                                }
                                return 1;
                            }
                        });
                        rv_home.setLayoutManager(gridLayoutManager);
                        initListener();
                    }
                    break;
            }
        }
    }

    /**
     * 处理数据，封装成 JavaBean
     * @param response
     */
    private void processData(String response) {
        if (!TextUtils.isEmpty(response)) {
//            Log.d(TAG, "processData: " + response);
            JSONObject jsonObject = JSON.parseObject(response);
            String code = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");
            String result = jsonObject.getString("result");

            JSONObject resultBean = JSON.parseObject(result);
            String banner_info = resultBean.getString("banner_info");
            String act_info = resultBean.getString("act_info");
            String channel_info = resultBean.getString("channel_info");
            String hot_info = resultBean.getString("hot_info");
            String recommend_info = resultBean.getString("recommend_info");
            String seckill_info = resultBean.getString("seckill_info");

            resultBeanData = new ResultBeanData();
            resultBeanData.setResult(new ResultBeanData.ResultBean());

            List<ResultBeanData.ResultBean.BannerInfoBean> bannerInfoBeans = JSON.parseArray(banner_info, ResultBeanData.ResultBean.BannerInfoBean.class);
            List<ResultBeanData.ResultBean.ActInfoBean> actInfoBeans = JSON.parseArray(act_info, ResultBeanData.ResultBean.ActInfoBean.class);
            List<ResultBeanData.ResultBean.ChannelInfoBean> channelInfoBeans = JSON.parseArray(channel_info, ResultBeanData.ResultBean.ChannelInfoBean.class);
            List<ResultBeanData.ResultBean.HotInfoBean> hotInfoBeans = JSON.parseArray(hot_info, ResultBeanData.ResultBean.HotInfoBean.class);
            List<ResultBeanData.ResultBean.RecommendInfoBean> recommendInfoBeans = JSON.parseArray(recommend_info, ResultBeanData.ResultBean.RecommendInfoBean.class);
//            List<ResultBeanData.ResultBean.SeckillInfoBean> seckillInfoBeans = JSON.parseArray(seckill_info, ResultBeanData.ResultBean.SeckillInfoBean.class);
            ResultBeanData.ResultBean.SeckillInfoBean seckillInfoBeans = JSON.parseObject(seckill_info, ResultBeanData.ResultBean.SeckillInfoBean.class);

            resultBeanData.getResult().setBanner_info(bannerInfoBeans);
            resultBeanData.getResult().setAct_info(actInfoBeans);
            resultBeanData.getResult().setChannel_info(channelInfoBeans);
            resultBeanData.getResult().setHot_info(hotInfoBeans);
            resultBeanData.getResult().setRecommend_info(recommendInfoBeans);
            resultBeanData.getResult().setSeckill_info(seckillInfoBeans);
        }
    }

}
