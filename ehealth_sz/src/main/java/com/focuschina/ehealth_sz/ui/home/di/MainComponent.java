package com.focuschina.ehealth_sz.ui.home.di;

import com.focuschina.ehealth_lib.di.activity.ActivityModule;
import com.focuschina.ehealth_lib.di.activity.BaseActivityComponent;
import com.focuschina.ehealth_lib.di.activity.PerActivity;
import com.focuschina.ehealth_lib.di.app.AppComponent;
import com.focuschina.ehealth_sz.ui.home.HomeFragment;
import com.focuschina.ehealth_sz.ui.home.MainContract;
import com.focuschina.ehealth_sz.ui.home.MineFragment;

import dagger.Component;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: MainComponent
 * @Description: TODO: (main 注入器)
 * @date 2016/12/1 下午4:09
 */
@PerActivity
@Component(dependencies = {AppComponent.class}, modules = {ActivityModule.class, MainModule.class})
public interface MainComponent extends BaseActivityComponent {
    void inject(HomeFragment homeFragment);

    void inject(MineFragment mineFragment);

    MainContract.HomeView homeView();

    MainContract.MineView mineView();
}
