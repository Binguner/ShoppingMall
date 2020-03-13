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

import org.w3c.dom.Text;

/**
 * com.learnandroid.shoppingmall.home.adapter
 * Author: binguner
 * Date: 2020-03-01 20:52
 */
public class ChannelAdapter extends BaseAdapter {

    private Context mContext;
    private ResultBeanData resultBean;

    public ChannelAdapter(Context context, ResultBeanData resultBean) {
        this.mContext = context;
        this.resultBean = resultBean;
    }

    @Override
    public int getCount() {
        return resultBean.getResult().getChannel_info() == null ? 0 : resultBean.getResult().getChannel_info().size();
    }

    @Override
    public Object getItem(int position) {
        return resultBean.getResult().getChannel_info().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_channel, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_channel.setText(resultBean.getResult().getChannel_info()
                .get(position).getChannel_name());
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + resultBean.getResult()
                .getChannel_info().get(position).getImage()).into(viewHolder.iv_channel);
        return convertView;
    }

    class ViewHolder {

        private ImageView iv_channel;
        private TextView tv_channel;

        public ViewHolder(View view) {
            iv_channel = view.findViewById(R.id.iv_channel);
            tv_channel = view.findViewById(R.id.tv_channel);
        }

    }

}
