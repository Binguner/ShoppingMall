package com.learnandroid.shoppingmall.home.bean;

import java.io.Serializable;

/**
 * com.learnandroid.shoppingmall.home.bean
 * Author: binguner
 * Date: 2020-03-04 16:18
 */
public class GoodsBean implements Serializable {

    private String name;
    private String cover_price;
    private String figure;
    private String product_id;
    private int number = 1;


    /**
     * 是否处于编辑状态
     */
    private boolean isEditing;
    /**
     * 是否被选中
     */
    private boolean isChildSelected;

    public boolean isEditing() {
        return isEditing;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
    }

    public boolean isChildSelected() {
        return isChildSelected;
    }

    public void setChildSelected(boolean childSelected) {
        isChildSelected = childSelected;
    }

    public GoodsBean() {
    }

    public GoodsBean(String name, String cover_price, String figure, String product_id) {
        this.name = name;
        this.cover_price = cover_price;
        this.figure = figure;
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "name='" + name + '\'' +
                ", cover_price='" + cover_price + '\'' +
                ", figure='" + figure + '\'' +
                ", product_id='" + product_id + '\'' +
                ", number=" + number +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover_price() {
        return cover_price;
    }

    public void setCover_price(String cover_price) {
        this.cover_price = cover_price;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
