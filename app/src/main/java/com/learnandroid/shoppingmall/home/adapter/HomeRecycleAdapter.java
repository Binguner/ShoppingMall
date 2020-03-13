package com.learnandroid.shoppingmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.learnandroid.shoppingmall.R;
import com.learnandroid.shoppingmall.app.GoodsInfoActivity;
import com.learnandroid.shoppingmall.home.activity.GoodsListActivity;
import com.learnandroid.shoppingmall.home.bean.GoodsBean;
import com.learnandroid.shoppingmall.home.bean.ResultBeanData;
import com.learnandroid.shoppingmall.utils.Constants;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.magicviewpager.transformer.AlphaPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * com.learnandroid.shoppingmall.home.adapter
 * Author: binguner
 * Date: 2020-02-28 14:15
 */
public class HomeRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int BANNER = 0;
    public static final int CHANNEL = 1;
    public static final int ACT = 2;
    public static final int SECKILL = 3;
    public static final int RECOMMEND = 4;
    public static final int HOT = 5;

    public static final String GOODS_BEAN = "goods_bean";

    private static final String TAG = HomeRecycleAdapter.class.getSimpleName();

    public int currentType = BANNER;

    private ResultBeanData resultBeanData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private SimpleDateFormat simpleDateFormat;

    public HomeRecycleAdapter(Context mContext, ResultBeanData resultBeanData) {
        this.mContext = mContext;
        this.resultBeanData = resultBeanData;
        mLayoutInflater = LayoutInflater.from(mContext);
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == BANNER) {
            return new BannerViewHolder(mLayoutInflater.inflate(R.layout.banner_viewpager, null), mContext, resultBeanData);
        } else if (i == CHANNEL) {
            return new ChannelViewHolder(mLayoutInflater.inflate(R.layout.channel_item, null), mContext, resultBeanData.getResult());
        } else if (i == ACT) {
            return new ActViewHolder(mLayoutInflater.inflate(R.layout.act_item, null), mContext);
        } else if (i == SECKILL) {
            return new SeckillViewHolder(mLayoutInflater.inflate(R.layout.seckill_item, null), mContext);
        } else if (i == RECOMMEND) {
            return new RecommendViewHolder(mLayoutInflater.inflate(R.layout.recommend_item, null), mContext);
        } else if (i == HOT) {
            return new HotViweHolder(mLayoutInflater.inflate(R.layout.hot_item, null), mContext);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) viewHolder;
            bannerViewHolder.setData(resultBeanData.getResult().getBanner_info());
        } else if (getItemViewType(i) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) viewHolder;
            channelViewHolder.setData(resultBeanData.getResult());
        } else if (getItemViewType(i) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) viewHolder;
            actViewHolder.setData(resultBeanData);
        } else if (getItemViewType(i) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) viewHolder;
            seckillViewHolder.setData(resultBeanData);
        } else if (getItemViewType(i) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) viewHolder;
            recommendViewHolder.setData();
        } else if (getItemViewType(i) == HOT) {
            HotViweHolder hotViweHolder = (HotViweHolder) viewHolder;
            hotViweHolder.setData(resultBeanData);
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }


    private int leftTime;
    private boolean isFirstLoad = true;
    private TextView seckill_tv_time;

    private Handler timeHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                leftTime = leftTime - 1000;
                seckill_tv_time.setText(simpleDateFormat.format(new Date(leftTime)));
                timeHander.removeMessages(0);
                timeHander.sendEmptyMessageDelayed(0, 1000);
                if (leftTime == 0) {
                    timeHander.removeMessages(0);
                }
            }
        }
    };

    private class HotViweHolder extends RecyclerView.ViewHolder {

        private Context context;
        private TextView hot_tv_showmore;
        private GridView hot_gv_content;
        private HotGridViewAdapter adapter;

        public HotViweHolder(View view, Context mContext) {
            super(view);
            this.context = mContext;
            hot_tv_showmore = view.findViewById(R.id.hot_tv_showmore);
            hot_gv_content = view.findViewById(R.id.hot_gv_content);
            hot_tv_showmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Show more", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setData(final ResultBeanData resultBeanData) {
            adapter = new HotGridViewAdapter(context, resultBeanData);
            hot_gv_content.setAdapter(adapter);
            hot_gv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name = resultBeanData.getResult().getHot_info().get(position).getName();
                    String cover_price = resultBeanData.getResult().getHot_info().get(position).getCover_price();
                    String figure = resultBeanData.getResult().getHot_info().get(position).getFigure();
                    String product_id = resultBeanData.getResult().getHot_info().get(position).getProduct_id();

                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                    Intent intent = new Intent(context, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    context.startActivity(intent);

                }
            });
        }

    }

    private class RecommendViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private TextView recommend_iv_showmore;
        private GridView recommend_gv_items;

        public RecommendViewHolder(View itemView, Context mContext) {
            super(itemView);
            this.context = mContext;
            recommend_iv_showmore = itemView.findViewById(R.id.recommend_iv_showmore);
            recommend_gv_items = itemView.findViewById(R.id.recommend_gv_items);
            recommend_iv_showmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Show more", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setData() {
            RecommendGridViewAdapter adapter = new RecommendGridViewAdapter(context, resultBeanData);
            recommend_gv_items.setAdapter(adapter);
            recommend_gv_items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String cover_price = resultBeanData.getResult().getRecommend_info().get(position).getCover_price();
                    String name = resultBeanData.getResult().getRecommend_info().get(position).getName();
                    String figure = resultBeanData.getResult().getRecommend_info().get(position).getFigure();
                    String product_id = resultBeanData.getResult().getRecommend_info().get(position).getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
//
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    mContext.startActivity(intent);
                }
            });

        }
    }

    private class SeckillViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private TextView seckill_tv_showmore;
        private RecyclerView seckill_rv;

        private SeckillRecyclerViewAdapter seckillRecyclerViewAdapter;

        public SeckillViewHolder(View view, Context mContext) {
            super(view);
            this.context = mContext;
            seckill_tv_time = view.findViewById(R.id.seckill_tv_time);
            seckill_tv_showmore = view.findViewById(R.id.seckill_tv_showmore);
            seckill_rv = view.findViewById(R.id.seckill_rv);
            seckill_tv_showmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Show more", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void setData(final ResultBeanData resultBeanData) {
            if (isFirstLoad) {
                leftTime = (int) (Integer.parseInt(resultBeanData.getResult().getSeckill_info().getEnd_time())
                        - Integer.parseInt(resultBeanData.getResult().getSeckill_info().getStart_time()));
                isFirstLoad = false;
            }
            seckillRecyclerViewAdapter = new SeckillRecyclerViewAdapter(context, resultBeanData);
            seckill_rv.setAdapter(seckillRecyclerViewAdapter);
            seckill_rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            timeHander.sendEmptyMessageDelayed(0, 1000);
            seckillRecyclerViewAdapter.setOnSeckillRecyclerView(new SeckillRecyclerViewAdapter.OnSeckillRecyclerView() {
                @Override
                public void onClick(int positon) {
                    ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean = resultBeanData.getResult().getSeckill_info().getList().get(positon);
                    String name = listBean.getName();
                    String cover_price = listBean.getCover_price();
                    String figure = listBean.getFigure();
                    String product_id = listBean.getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);

                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    mContext.startActivity(intent);
                }
            });
        }

    }

    private class ActViewHolder extends RecyclerView.ViewHolder {

        private Context context;

        private ViewPager act_viewpager;

        public ActViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            act_viewpager = itemView.findViewById(R.id.act_viewpager);
        }

        private void setData(final ResultBeanData resultBeanData) {
            act_viewpager.setOffscreenPageLimit(3);
            act_viewpager.setPageMargin(20);
            act_viewpager.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));
            act_viewpager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return resultBeanData.getResult().getAct_info().size();
                }

                @Override
                public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                    return view == o;
                }

                @NonNull
                @Override
                public Object instantiateItem(@NonNull ViewGroup container, final int position) {
                    ImageView imageView = new ImageView(context);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext, "position is " + position, Toast.LENGTH_SHORT).show();
                        }
                    });
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                    Log.d(TAG, "instantiateItem: " + Constants.BASE_URL_IMAGE + resultBeanData.getResult().getAct_info().get(position).getIcon_url());
                    Glide.with(mContext)
                            .load(Constants.BASE_URL_IMAGE + resultBeanData.getResult().getAct_info().get(position).getIcon_url())
                            .into(imageView);
                    container.addView(imageView);
                    return imageView;
                }

                @Override
                public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                    super.destroyItem(container, position, object);
                    container.removeView((View) object);
                }
            });
        }

    }

    private class ChannelViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private ResultBeanData.ResultBean resultBean;

        private GridView gv_channel;

        public ChannelViewHolder(@NonNull View itemView, Context context, ResultBeanData.ResultBean resultBean) {
            super(itemView);
            this.context = context;
            this.resultBean = resultBean;
            gv_channel = itemView.findViewById(R.id.gv_channel);
        }

        private void setData(ResultBeanData.ResultBean resultBean) {
            ChannelAdapter channelAdapter = new ChannelAdapter(mContext, resultBeanData);
            gv_channel.setAdapter(channelAdapter);
            gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position <= 8) {
                        Intent intent = new Intent(mContext, GoodsListActivity.class);
                        intent.putExtra("position", position);
                        mContext.startActivity(intent);
                    }
                }
            });
        }

    }

    private class BannerViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private ResultBeanData resultBean;

        private Banner banner;

        public BannerViewHolder(@NonNull View itemView, Context context, ResultBeanData resultBean) {
            super(itemView);
            this.mContext = context;
            this.resultBean = resultBean;
            banner = itemView.findViewById(R.id.banner);
        }

        public void setData(List<ResultBeanData.ResultBean.BannerInfoBean> bannerInfoBeanList) {
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            List<String> imagUris = new ArrayList<>();
            for (int i = 0; i < bannerInfoBeanList.size(); i++) {
                imagUris.add(Constants.BASE_URL_IMAGE + bannerInfoBeanList.get(i).getImage());
//                Log.d(TAG, "setData: " + imagUris.get(i));
            }
            banner.setBannerAnimation(Transformer.Accordion);
            banner.setImages(imagUris);
            banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(mContext).load(path).into(imageView);
                }
            });
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Toast.makeText(mContext, "position: " + position, Toast.LENGTH_SHORT).show();
                }
            });
            banner.start();
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    if (position - 1 < resultBeanData.getResult().getBanner_info().size()) {
                        int option = resultBeanData.getResult().getBanner_info().get(position).getOption();
                        String product_id = "";
                        String name = "";
                        String cover_price = "";
                        if (position - 1 == 0) {
                            product_id = "627";
                            cover_price = "32.00";
                            name = "剑三T恤批发";
                        } else if (position - 1 == 1) {
                            product_id = "21";
                            cover_price = "8.00";
                            name = "同人原创】剑网3 剑侠情缘叁 Q版成男 口袋胸针";
                        } else {
                            product_id = "1341";
                            cover_price = "50.00";
                            name = "【蓝诺】《天下吾双》 剑网3同人本";
                        }
                        String image = resultBeanData.getResult().getBanner_info().get(position - 1).getImage();
                        GoodsBean goodsBean = new GoodsBean(name, cover_price, image, product_id);

                        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                        intent.putExtra("goods_bean", goodsBean);
                        mContext.startActivity(intent);

                    }
                }
            });
        }

    }


}
