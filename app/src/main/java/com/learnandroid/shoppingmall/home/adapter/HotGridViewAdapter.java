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
 * Date: 2020-03-04 10:27
 */
public class HotGridViewAdapter extends BaseAdapter {

    private Context context;
    private ResultBeanData resultBeanData;

    public HotGridViewAdapter(Context context, ResultBeanData resultBeanData) {
        this.context = context;
        this.resultBeanData = resultBeanData;
    }

    @Override
    public int getCount() {
        return resultBeanData.getResult().getHot_info().size();
    }

    @Override
    public Object getItem(int position) {
        return resultBeanData.getResult().getHot_info().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hot_grid_view, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.hot_item_tv_name.setText(resultBeanData.getResult().getHot_info().get(position).getName());
        viewHolder.hot_item_tv_price.setText("¥ " + resultBeanData.getResult().getHot_info().get(position).getCover_price());
        Glide.with(context)
                .load(Constants.BASE_URL_IMAGE + resultBeanData.getResult().getHot_info().get(position).getFigure())
                .into(viewHolder.hot_item_iv_icon);
        return convertView;
    }

    class ViewHolder {
        private ImageView hot_item_iv_icon;
        private TextView hot_item_tv_name;
        private TextView hot_item_tv_price;

        public ViewHolder(View view) {
            hot_item_iv_icon = view.findViewById(R.id.hot_item_iv_icon);
            hot_item_tv_name = view.findViewById(R.id.hot_item_tv_name);
            hot_item_tv_price = view.findViewById(R.id.hot_item_tv_price);
        }
    }

}
