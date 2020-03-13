package com.learnandroid.shoppingmall.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.learnandroid.shoppingmall.app.MyApplication;
import com.learnandroid.shoppingmall.home.bean.GoodsBean;
import com.learnandroid.shoppingmall.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * com.learnandroid.shoppingmall.shoppingcart.utils
 * Author: binguner
 * Date: 2020-03-04 21:56
 */
public class CartProvider {

    private static final String TAG = CartProvider.class.getSimpleName();
    private Context context;

    private SparseArray<GoodsBean> datas;
    public static final String JSON_CART = "json_cart";
    private static CartProvider cartProvider;

    private CartProvider(Context context) {
        this.context = context;
        datas = new SparseArray<>(100);
        listToSparse();
    }

    public static CartProvider getInstance() {
        if (cartProvider == null) {
            cartProvider = new CartProvider(MyApplication.getContext());
        }
        return cartProvider;
    }


    /**
     * SparseArray to List
     * @return List<GoodsBean>
     */
    public List<GoodsBean> parsesToList() {
        List<GoodsBean> list = new ArrayList<>();
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                list.add(datas.valueAt(i));
            }
        }
        return list;
    }

    /**
     * 在构造方法中初始化 SparseArray datas
     */
    private void listToSparse() {
        List<GoodsBean> carts = getAllData();
        if (carts != null && carts.size() > 0) {
            for (GoodsBean goodsBean : carts) {
                datas.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
            }
        }
    }

    public List<GoodsBean> getAllData() {
        return getDataFromLocal();
    }

    /**
     * 本地获取 json 数据，通过 Gson 转换成 List 数组
     * @return
     */
    private List<GoodsBean> getDataFromLocal() {
        List<GoodsBean> datas = new ArrayList<>();
        String json = CacheUtils.getString(context, JSON_CART);
        if (!TextUtils.isEmpty(json)) {
            datas = new Gson().fromJson(json, new TypeToken<List<GoodsBean>>() {
            }.getType());
        }
        return datas;
    }


    public void addData(GoodsBean goodsBean) {
        GoodsBean goodsBean1 = datas.get(Integer.parseInt(goodsBean.getProduct_id()));
        if (goodsBean1 == null) {
            goodsBean1 = goodsBean;
            goodsBean1.setNumber(1);
        } else {
            goodsBean1.setNumber(goodsBean1.getNumber() + 1);
        }
        datas.put(Integer.parseInt(goodsBean1.getProduct_id()), goodsBean1);
        commit();
    }

    /**
     * 存储数据到 sharedperferences 中
     */
    private void commit() {
        List<GoodsBean> goodsBeans = parsesToList();
        String json = new Gson().toJson(goodsBeans);
        CacheUtils.putSting(context,JSON_CART,json);
    }

    public void deleteData(GoodsBean goodsBean) {
        if (goodsBean != null) {
            datas.delete(Integer.parseInt(goodsBean.getProduct_id()));
            commit();
        }
    }

    public void updateData(GoodsBean goodsBean) {
        if (goodsBean != null) {
            datas.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
            commit();
        }
    }

    /**
     * 根据 key 查找书籍
     * @return
     */
    public GoodsBean findData(GoodsBean bean) {
        if (bean != null) {
            GoodsBean goodsBean = datas.get(Integer.parseInt(bean.getProduct_id()));
            return goodsBean;
        }
        return null;
    }
}