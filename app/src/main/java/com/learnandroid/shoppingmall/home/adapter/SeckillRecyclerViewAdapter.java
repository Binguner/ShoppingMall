package com.learnandroid.shoppingmall.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.learnandroid.shoppingmall.R;
import com.learnandroid.shoppingmall.home.bean.ResultBeanData;
import com.learnandroid.shoppingmall.utils.Constants;

/**
 * com.learnandroid.shoppingmall.home.adapter
 * Author: binguner
 * Date: 2020-03-02 21:30
 */
public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter<SeckillRecyclerViewAdapter.SeckItemViewHolder> {

    private Context context;
    private ResultBeanData resultBeanData;
    private OnSeckillRecyclerView onSeckillRecyclerView;

    public SeckillRecyclerViewAdapter(Context context, ResultBeanData resultBeanData) {
        this.context = context;
        this.resultBeanData = resultBeanData;
    }


    @NonNull
    @Override
    public SeckItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SeckItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_seckill, null),context);
    }

    @Override
    public void onBindViewHolder(@NonNull SeckItemViewHolder seckItemViewHolder, int i) {
        seckItemViewHolder.setData(i);
    }


    @Override
    public int getItemCount() {
        return resultBeanData.getResult().getSeckill_info().getList().size();
    }

    class SeckItemViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private ImageView seckill_item_iv_itempic;
        private TextView seckill_item_tv_nowprice;
        private TextView seckill_item_tv_oldprice;
        private ConstraintLayout seckill_item_cons_root;

        public SeckItemViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            seckill_item_iv_itempic = itemView.findViewById(R.id.seckill_item_iv_itempic);
            seckill_item_tv_nowprice = itemView.findViewById(R.id.seckill_item_tv_nowprice);
            seckill_item_tv_oldprice = itemView.findViewById(R.id.seckill_item_tv_oldprice);
            seckill_item_cons_root = itemView.findViewById(R.id.seckill_item_cons_root);
            this.mContext = context;
        }


        public void setData(final int position) {
            Glide.with(mContext).load(Constants.BASE_URL_IMAGE + resultBeanData.getResult().getSeckill_info().getList().get(position).getFigure()).into(seckill_item_iv_itempic);
            seckill_item_tv_nowprice.setText("¥ " + resultBeanData.getResult().getSeckill_info().getList().get(position).getCover_price());
            seckill_item_tv_oldprice.setText("¥ " + resultBeanData.getResult().getSeckill_info().getList().get(position).getOrigin_price());
            seckill_item_cons_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSeckillRecyclerView != null) {
                        onSeckillRecyclerView.onClick(position);
                    }
                }
            });
        }
    }

    public interface OnSeckillRecyclerView {
        void onClick(int positon);
    }


    public void setOnSeckillRecyclerView(OnSeckillRecyclerView onSeckillRecyclerViewt) {
        this.onSeckillRecyclerView = onSeckillRecyclerViewt;
    }

}
