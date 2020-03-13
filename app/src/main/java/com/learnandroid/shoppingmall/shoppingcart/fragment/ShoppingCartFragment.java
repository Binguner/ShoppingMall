package com.learnandroid.shoppingmall.shoppingcart.fragment;


import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.learnandroid.shoppingmall.R;
import com.learnandroid.shoppingmall.base.BaseFragment;
import com.learnandroid.shoppingmall.home.bean.GoodsBean;
import com.learnandroid.shoppingmall.shoppingcart.adapter.ShopCartAdapter;
import com.learnandroid.shoppingmall.shoppingcart.utils.CartProvider;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingCartFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = ShoppingCartFragment.class.getSimpleName();
    private TextView textView;

    public ShoppingCartFragment() {

    }

    private ConstraintLayout llShopcartToolbar;
    private TextView tvShopcartEdit;
    private View shadowShopcartToolbar;
    private RecyclerView rvShopcart;
    private LinearLayout llCheckAll;
    private CheckBox checkboxAll;
    private TextView tvShopcartTotal;
    private Button btnCheckOut;
    private LinearLayout llDelete;
    private CheckBox cbAll;
    private Button btnDelete;
    private Button btnCollection;
    private ConstraintLayout ll_empty_shopcart;
    private TextView tv_empty_cart_tobuy;

    private ShopCartAdapter adapter;

    private static final int ACTION_EDIT = 0;
    private static final int ACTION_COMPLETE = 1;


    private void findViews(View view) {
        llShopcartToolbar = view.findViewById(R.id.ll_shopcart_toolbar);
        tvShopcartEdit = view.findViewById(R.id.tv_shopcart_edit);
        shadowShopcartToolbar = view.findViewById(R.id.shadow_shopcart_toolbar);
        rvShopcart = view.findViewById(R.id.rv_shopcart);
        llCheckAll = view.findViewById(R.id.ll_check_all);
        checkboxAll = view.findViewById(R.id.checkbox_all);
        tvShopcartTotal = view.findViewById(R.id.tv_shopcart_total);
        btnCheckOut = view.findViewById(R.id.btn_check_out);
        llDelete = view.findViewById(R.id.ll_delete);
        cbAll = view.findViewById(R.id.cb_all);
        btnDelete = view.findViewById(R.id.btn_delete);
        btnCollection = view.findViewById(R.id.btn_collection);
        ll_empty_shopcart = view.findViewById(R.id.ll_empty_shopcart);
        tv_empty_cart_tobuy = view.findViewById(R.id.tv_empty_cart_tobuy);

        btnCheckOut.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        Log.d(TAG, "findViews: setListener for btnDelete");
        btnCollection.setOnClickListener(this);
        tvShopcartEdit.setOnClickListener(this);



    }

    /**
     * 进入编辑状态
     */
    private void showDelate() {
        tvShopcartEdit.setText("完成");
        tvShopcartEdit.setTag(ACTION_EDIT);

        llDelete.setVisibility(View.VISIBLE);
        llCheckAll.setVisibility(View.GONE);
    }

    /**
     * 进入完成状态
     */
    private void hideDelate() {
        tvShopcartEdit.setText("编辑");
        tvShopcartEdit.setTag(ACTION_COMPLETE);

        llCheckAll.setVisibility(View.VISIBLE);
        llDelete.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check_out:
                break;
            case R.id.btn_delete:
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: ");
                        adapter.delateData();
                        adapter.showTotalPrice();
                        checkData();
                    }
                });
                break;
            case R.id.btn_collection:
                break;
            case R.id.checkbox_all:
                checkboxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    }
                });
                break;
            case R.id.tv_shopcart_edit:
                tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tag = (int) tvShopcartEdit.getTag();
                        if (tag == ACTION_COMPLETE) {
                            showDelate();
                        } else {
                            hideDelate();
                        }
                    }
                });
                break;
        }
    }

    private void checkData() {
        if (adapter != null && adapter.getItemCount() > 0) {
            ll_empty_shopcart.setVisibility(View.GONE);
            tvShopcartEdit.setVisibility(View.VISIBLE);
            llDelete.setVisibility(View.VISIBLE);
        } else {
            ll_empty_shopcart.setVisibility(View.VISIBLE);
            tvShopcartEdit.setVisibility(View.GONE);
            llDelete.setVisibility(View.GONE);
        }
    }


    @Override
    public View initView() {
        Log.d(TAG, "购物车视图被初始化了");
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_shoppingcart, null);
        findViews(view);
        return view;
    }


    @Override
    public void initData() {
        super.initData();
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        Log.d(TAG, "购物车页面数据被初始化了");
    }

    @Override
    public void onResume() {
        super.onResume();
        showData();
    }

    private void showData() {
        List<GoodsBean> list = CartProvider.getInstance().getAllData();
        if (list != null && list.size() > 0) {
            tvShopcartEdit.setVisibility(View.VISIBLE);
            adapter = new ShopCartAdapter(mContext, list,tvShopcartTotal,checkboxAll);
            rvShopcart.setLayoutManager(new LinearLayoutManager(mContext));
            rvShopcart.setAdapter(adapter);
            ll_empty_shopcart.setVisibility(View.GONE);
            llCheckAll.setVisibility(View.VISIBLE);
            llDelete.setVisibility(View.GONE);
        } else {
            tvShopcartEdit.setVisibility(View.INVISIBLE);
            ll_empty_shopcart.setVisibility(View.VISIBLE);
            llCheckAll.setVisibility(View.GONE);
            llDelete.setVisibility(View.GONE);
        }
    }
}
