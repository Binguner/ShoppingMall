package com.learnandroid.shoppingmall.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.learnandroid.shoppingmall.R;
import com.learnandroid.shoppingmall.home.bean.GoodsBean;
import com.learnandroid.shoppingmall.shoppingcart.utils.CartProvider;
import com.learnandroid.shoppingmall.utils.Constants;

import java.util.List;

public class GoodsInfoActivity extends Activity implements View.OnClickListener {


    private ImageButton ibGoodInfoBack;
    private ImageButton ibGoodInfoMore;
    private ImageView ivGoodInfoImage;
    private TextView tvGoodInfoName;
    private TextView tvGoodInfoDesc;
    private TextView tvGoodInfoPrice;
    private TextView tvGoodInfoStore;
    private TextView tvGoodInfoStyle;
    private WebView wbGoodInfoMore;
    private TextView tvGoodInfoCallcenter;
    private TextView tvGoodInfoCollection;
    private TextView tvGoodInfoCart;
    private Button btnGoodInfoAddcart;
    private TextView tvMoreShare;
    private TextView tvMoreSearch;
    private TextView tvMoreHome;
    private LinearLayout ll_root;
    private Button btn_more;


    private CartProvider cartProvider;
    // private Boolean isFirst = true;

    /* //模拟商家的数组
     private String[] sellers = new String[]{"尚硅谷", "画影工作室", "Wacom"};
     private List<GoodsList> goodsLists;
    private GoodsList goodsList;*/
    private List<GoodsBean> goodsBeans;
    private GoodsBean goods_bean;
    private String TAG = GoodsInfoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        findViews();

        Intent intent = getIntent();
        goods_bean = (GoodsBean) intent.getSerializableExtra("goods_bean");
        if (goods_bean != null) {
            // 本地获取存储的数据
            setDataFormView(goods_bean);
        }
    }

    private void setDataFormView(GoodsBean goodsBean) {
        String name = goodsBean.getName();
        String cover_price = goodsBean.getCover_price();
        String figure = goodsBean.getFigure();
        String product_id = goodsBean.getProduct_id();

        Glide.with(this).load(Constants.BASE_URL_IMAGE + figure).into(ivGoodInfoImage);
        if (name != null) {
            tvGoodInfoName.setText(name);
        }
        if (cover_price != null) {
            tvGoodInfoPrice.setText("￥" + cover_price);
        }
        setWebView(product_id);
    }

    private void setWebView(String product_id) {
        if (product_id != null) {
            wbGoodInfoMore.loadUrl("http://www.atguigu.com");

            wbGoodInfoMore.setWebViewClient(new WebViewClient());
            WebSettings settings = wbGoodInfoMore.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setUseWideViewPort(true);
            wbGoodInfoMore.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }

    private void findViews() {
        ibGoodInfoBack = findViewById(R.id.goods_info_btn_back);
        ibGoodInfoMore = findViewById(R.id.goods_info_btn_more);
        ivGoodInfoImage = findViewById(R.id.iv_good_info_image);
        tvGoodInfoName = findViewById(R.id.tv_good_info_name);
        tvGoodInfoPrice = findViewById(R.id.tv_good_info_price);
        btnGoodInfoAddcart = findViewById(R.id.btn_good_info_addcart);
        tvGoodInfoCallcenter = findViewById(R.id.tv_good_info_callcenter);
        tvGoodInfoCollection = findViewById(R.id.tv_good_info_collection);
        tvGoodInfoCart = findViewById(R.id.tv_good_info_cart);
        wbGoodInfoMore= findViewById(R.id.wb_good_info_more);

        ibGoodInfoBack.setOnClickListener(this);
        ibGoodInfoMore.setOnClickListener(this);
        btnGoodInfoAddcart.setOnClickListener(this);
        tvGoodInfoCallcenter.setOnClickListener(this);
        tvGoodInfoCollection.setOnClickListener(this);
        tvGoodInfoCart.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == ibGoodInfoBack) {
            finish();
        } else if (v == ibGoodInfoMore) {
            Toast.makeText(this, "more", Toast.LENGTH_SHORT).show();
        } else if (v == btnGoodInfoAddcart) {
            CartProvider.getInstance().addData(goods_bean);
            Toast.makeText(this, "add to cart", Toast.LENGTH_SHORT).show();
        } else if (v == tvGoodInfoCallcenter) {
            Toast.makeText(this, "联系客服", Toast.LENGTH_SHORT).show();
        } else if (v == tvGoodInfoCollection) {
            Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
        } else if (v == tvGoodInfoCart) {
            Toast.makeText(this, "加入购物车", Toast.LENGTH_SHORT).show();
        }
    }
}
