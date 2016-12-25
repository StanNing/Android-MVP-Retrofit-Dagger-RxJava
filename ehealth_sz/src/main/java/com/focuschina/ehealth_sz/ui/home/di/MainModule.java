package com.focuschina.ehealth_sz.ui.home.di;

import com.focuschina.ehealth_lib.di.activity.PerActivity;
import com.focuschina.ehealth_sz.ui.home.MainContract;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: MainModule
 * @Description: TODO: (Main - HomeFragment & MineFragment 所需依赖)
 * @date 2016/12/15 下午4:26
 */
@Module
public class MainModule {

    private MainContract.HomeView homeView;
    private MainContract.MineView mineView;

    public MainModule(MainContract.HomeView homeView) {
        this.homeView = homeView;
    }

    public MainModule(MainContract.MineView mineView) {
        this.mineView = mineView;
    }

    @Provides
    @PerActivity
    MainContract.HomeView provideHomeView() {
        return homeView;
    }

    @Provides
    @PerActivity
    MainContract.MineView provideMineView() {
        return mineView;
    }
}
