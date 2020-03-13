package com.learnandroid.shoppingmall.shoppingcart.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.learnandroid.shoppingmall.R;
import com.learnandroid.shoppingmall.home.bean.GoodsBean;
import com.learnandroid.shoppingmall.shoppingcart.utils.CartProvider;
import com.learnandroid.shoppingmall.shoppingcart.view.NumberAddSubView;
import com.learnandroid.shoppingmall.utils.Constants;

import java.util.Iterator;
import java.util.List;

/**
 * com.learnandroid.shoppingmall.shoppingcart.adapter
 * Author: binguner
 * Date: 2020-03-09 13:57
 */
public class ShopCartAdapter extends RecyclerView.Adapter<ShopCartAdapter.ViewHolder> {

    private Context mContext;
    private List<GoodsBean> datas;
    private CartProvider cartProvider;
    private TextView tvShopcartTotal;
    private CheckBox checkboxAll;

    public ShopCartAdapter(Context mContext, List<GoodsBean> datas, TextView tvShopcartTotal, CheckBox checkboxAll) {
        cartProvider = CartProvider.getInstance();
        this.mContext = mContext;
        this.datas = datas;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        showTotalPrice();
//        checkAll_none();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_shop_cart, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setData(datas.get(i));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void checkAll() {
        if (datas != null && datas.size() > 0) {

        }
    }



    /**
     * 用迭代器删除数据
     */
    public void delateData() {
        if (datas != null && datas.size() > 0) {
            for (Iterator iterator = datas.iterator(); iterator.hasNext(); ) {
                GoodsBean bean = (GoodsBean) iterator.next();
                if (bean.isChildSelected()) {
                    int positon = datas.indexOf(bean);
                    cartProvider.deleteData(bean);
                    // 从数据集合中删除此数据
                    iterator.remove();
                    // 刷新数据
                    notifyItemRemoved(positon);
                }
            }
        }
    }

    /**
     * 编辑状态点击全选的事件
     * 设置每一个 item 是否选择的状态为 checked
     * 将状态保存到 goodsBean 中
     * @param checked
     */
    public void checkAll_none(boolean checked) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                goodsBean.setChildSelected(checked);
//                cartProvider.updateData(goodsBean);
                checkboxAll.setChecked(checked);
                notifyItemChanged(i);
            }
        } else {
            checkboxAll.setChecked(false);
        }
    }

    /**
     * 刷新界面上的总价
     */
    public void showTotalPrice() {
        tvShopcartTotal.setText("¥" + getTotalPrice());
    }

    /**
     * 计算当前购物车 datas 内所有商品的总价格
     * @return 当前购物车内所有商品的总价格
     */
    private double getTotalPrice() {
        double total = 0;
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                total += Double.parseDouble(goodsBean.getNumber() + "") * Double.parseDouble(goodsBean.getCover_price());
            }
        }
        return total;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cb_gov;
        private ImageView iv_gov;
        private TextView tv_desc_gov;
        private TextView tv_price_gov;
        private NumberAddSubView numberAddSubView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cb_gov = itemView.findViewById(R.id.cb_gov);
            iv_gov = itemView.findViewById(R.id.iv_gov);
            tv_desc_gov = itemView.findViewById(R.id.tv_desc_gov);
            tv_price_gov = itemView.findViewById(R.id.tv_price_gov);
            numberAddSubView = itemView.findViewById(R.id.numberAddSubView);

            checkboxAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkAll_none(isChecked);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }


        /**
         * 设置 GoodsBead 的数据
         * 给 NumberAddSubView 的点击事件
         *  +、- 修改内存的数据
         * @param goodsBean
         */
        private void setData(final GoodsBean goodsBean) {
            Glide.with(mContext).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(iv_gov);
            tv_desc_gov.setText(goodsBean.getName());
            cb_gov.setChecked(goodsBean.isChildSelected());
            tv_price_gov.setText("¥ " + goodsBean.getCover_price());
            numberAddSubView.setNumber(goodsBean.getNumber());
            numberAddSubView.setOnNumberChangedListener(new NumberAddSubView.OnNumberChangedListener() {
                @Override
                public void onChange(int number) {
                    goodsBean.setNumber(number);
                    cartProvider.updateData(goodsBean);
                    showTotalPrice();
                }
            });
            cb_gov.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    goodsBean.setChildSelected(isChecked);
                    cartProvider.updateData(goodsBean);
                }
            });
        }
    }

}
