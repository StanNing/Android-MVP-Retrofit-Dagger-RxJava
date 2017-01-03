package com.focuschina.ehealth_sz.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.focuschina.ehealth_lib.EhApplication;
import com.focuschina.ehealth_lib.adapter.FragVPAdapter;
import com.focuschina.ehealth_lib.view.widget.NoScrollViewPager;
import com.focuschina.ehealth_sz.R;
import com.focuschina.ehealth_sz.ui.BaseToolBarActivity;
import com.focuschina.ehealth_sz.ui.home.di.DaggerMainComponent;
import com.focuschina.ehealth_sz.ui.home.di.MainComponent;
import com.focuschina.ehealth_sz.ui.home.di.MainModule;

import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;

/**
 * Copyright (C) Focus Technology
 *
 * @ClassName: MainTabActivity
 * @Description: TODO: (启动Tab页 主页)
 * @author: Ning Jiajun
 * @date: 2016/12/14 下午4:46
 */
public class MainTabActivity extends BaseToolBarActivity implements MainContract.MainView {

    private NoScrollViewPager viewPager;
    private FragVPAdapter vpAdapter;
    private ArrayList<Fragment> mFragments;
    private String[] fragmentNames = new String[2];

    private HomeFragment homeFragment;
    private MineFragment mineFragment;

    private RadioButton homeTab, mineTab;
    private RadioGroup tabGroup;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindView(Bundle bundle) {
        tabGroup = (RadioGroup) findViewById(R.id.main_tab_group);
        homeTab = (RadioButton) findViewById(R.id.main_tab_home);
        mineTab = (RadioButton) findViewById(R.id.main_tab_mine);
        initTabFrags();
        //tab 切换 -- 首页 & 我的
        tabGroup.setOnCheckedChangeListener((group, checkedId) ->
                viewPager.setCurrentItem(checkedId == R.id.main_tab_home ? 0 : 1));
    }

    private void initTabFrags() {
        viewPager = (NoScrollViewPager) findViewById(R.id.main_tab_viewpager);
        mFragments = new ArrayList<>();
        fragmentNames[0] = homeTab.getText().toString();
        fragmentNames[1] = mineTab.getText().toString();

        homeFragment = HomeFragment.newInstance(); //home
        mineFragment = MineFragment.newInstance(); //mine
        mFragments.add(homeFragment);
        mFragments.add(mineFragment);

        vpAdapter = new FragVPAdapter(getSupportFragmentManager(), fragmentNames, mFragments);
        viewPager.setAdapter(vpAdapter);
        viewPager.setNoScroll(true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switchTab(position); //切换tab
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void switchTab(int position) {

    }

    @Override
    protected String getTitleTx() {
        setTitleInCenter();
        hideHomeAsUp();
        return getString(R.string.app_name);
    }

    @Override
    protected void initInjector() {
        super.initInjector();
        DaggerMainComponent.Builder builder = DaggerMainComponent.builder()
                .appComponent(((EhApplication) getApplication()).getAppComponent())
                .activityModule(super.getActivityModule());

        MainComponent HomeComponent = builder.mainModule(new MainModule(homeFragment)).build();
        HomeComponent.inject(homeFragment);

        MainComponent MineComponent = builder.mainModule(new MainModule(mineFragment)).build();
        MineComponent.inject(mineFragment);
    }

    @Override
    protected void onSingleClick(View v) {

    }

    private long mExitTime; // 退出时间
    private static final int INTERVAL = 2000;// 退出间隔

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {// 处理返回按钮退出
            if (currentTimeMillis() - mExitTime > INTERVAL) {
                showToast("再按一次返回键,可直接退出程序");
                mExitTime = currentTimeMillis();
            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void destroy() {
        ((EhApplication) getApplication()).exit();
    }
}
