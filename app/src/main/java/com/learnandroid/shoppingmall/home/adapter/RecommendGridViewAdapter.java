package com.learnandroid.shoppingmall.home.adapter;

import android.content.Context;
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
 * Date: 2020-03-03 00:28
 */
public class RecommendGridViewAdapter extends BaseAdapter {

    private Context context;
    private ResultBeanData resultBeanData;

    public RecommendGridViewAdapter(Context context, ResultBeanData resultBeanData) {
        this.context = context;
        this.resultBeanData = resultBeanData;
    }

    @Override
    public int getCount() {
        return resultBeanData.getResult().getRecommend_info().size();
    }

    @Override
    public Object getItem(int position) {
        return resultBeanData.getResult().getRecommend_info().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecommendGridViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recommend_grid_view, null);
            viewHolder = new RecommendGridViewHolder(convertView, context);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RecommendGridViewHolder) convertView.getTag();
        }
        viewHolder.recom_grid_item_tv_name.setText(resultBeanData.getResult().getRecommend_info().get(position).getName());
        viewHolder.recom_grid_item_tv_price.setText("¥ " + resultBeanData.getResult().getRecommend_info().get(position).getCover_price());
        Glide.with(context)
                .load(Constants.BASE_URL_IMAGE + resultBeanData.getResult().getRecommend_info().get(position).getFigure())
                .into(viewHolder.recom_grid_item_iv_pic);

        return convertView;
    }

    class RecommendGridViewHolder {
        private ImageView recom_grid_item_iv_pic;
        private TextView recom_grid_item_tv_name;
        private TextView recom_grid_item_tv_price;
        private Context context;

        public RecommendGridViewHolder(View view,Context context) {
            recom_grid_item_iv_pic = view.findViewById(R.id.recom_grid_item_iv_pic);
            recom_grid_item_tv_name = view.findViewById(R.id.recom_grid_item_tv_name);
            recom_grid_item_tv_price = view.findViewById(R.id.recom_grid_item_tv_price);
            this.context = context;
        }
    }

}
