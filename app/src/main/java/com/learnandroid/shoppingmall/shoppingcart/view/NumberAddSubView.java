package com.learnandroid.shoppingmall.shoppingcart.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.learnandroid.shoppingmall.R;

/**
 * com.learnandroid.shoppingmall.shoppingcart.view
 * Author: binguner
 * Date: 2020-03-07 11:29
 */
public class NumberAddSubView extends LinearLayout implements View.OnClickListener {

    private ImageView btn_sub;
    private TextView tv_count;
    private ImageView btn_add;
    private int minValue = 1;

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    private int maxValue = 10;

    int number = 1;
    private static final String TAG = NumberAddSubView.class.getSimpleName();

    public NumberAddSubView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.number_add_sub_layout, this);
        btn_sub = findViewById(R.id.btn_sub);
        tv_count = findViewById(R.id.tv_count);
        btn_add = findViewById(R.id.btn_add);

        btn_add.setOnClickListener(this);
        btn_sub.setOnClickListener(this);
        tv_count.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                if (number < maxValue) {
                    number++;
                    setNumber(number);
                }
                break;
            case R.id.btn_sub:
                if (number > minValue) {
                    number--;
                    setNumber(number);
                }
                break;
            case R.id.tv_count:

                break;
        }
    }

    public void setNumber(int number) {
        this.number = number;
        tv_count.setText(String.valueOf(number));
        if (onNumberChangedListener != null) {
            onNumberChangedListener.onChange(number);
        }
    }


    public interface OnNumberChangedListener {
        void onChange(int number);
    }

    private OnNumberChangedListener onNumberChangedListener;

    public void setOnNumberChangedListener(OnNumberChangedListener onNumberChangedListener) {
        this.onNumberChangedListener = onNumberChangedListener;
    }
}
