package com.learnandroid.shoppingmall.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.learnandroid.shoppingmall.R;
import com.learnandroid.shoppingmall.base.BaseFragment;
import com.learnandroid.shoppingmall.community.fragment.CommunityFragment;
import com.learnandroid.shoppingmall.home.fragment.HomeFragment;
import com.learnandroid.shoppingmall.shoppingcart.fragment.ShoppingCartFragment;
import com.learnandroid.shoppingmall.type.fragment.TypeFragment;
import com.learnandroid.shoppingmall.user.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private List<Fragment> fragments;
    private RadioGroup rg_main;
    private int position = 0;
    private BaseFragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initId();
        initFragments();
        initListener();
    }

    private void initId() {
        rg_main = findViewById(R.id.rg_main);
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new UserFragment());
    }

    private void initListener() {
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_type:
                        position = 1;
                        break;
                    case R.id.rb_community:
                        position = 2;
                        break;
                    case R.id.rb_cart:
                        position = 3;
                        break;
                    case R.id.rb_user:
                        position = 4;
                        break;
                }
                BaseFragment toFragment = getFragment(position);
                switchFragment(tempFragment, toFragment);
            }
        });
        rg_main.check(R.id.rb_home);
    }

    private void switchFragment(BaseFragment fromFragment, BaseFragment toFragment) {
        if (tempFragment != toFragment) {
            tempFragment = toFragment;
            if (toFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (!toFragment.isAdded()) {
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.frameLayout, toFragment).commit();
                } else {
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(toFragment).commit();
                }
            }
        }
    }

    private BaseFragment getFragment(int position) {
        if (fragments != null && fragments.size() > 0) {
            BaseFragment fragment = (BaseFragment) fragments.get(position);
            return fragment;
        }
        return null;
    }


}
