package com.focuschina.ehealth_lib;

import android.app.Application;

import com.focuschina.ehealth_lib.di.app.AppComponent;
import com.focuschina.ehealth_lib.di.app.AppModule;
import com.focuschina.ehealth_lib.di.app.DaggerAppComponent;
import com.focuschina.ehealth_lib.mgt.ActivityMgt;
import com.focuschina.ehealth_lib.task.RxBus;

import javax.inject.Inject;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: EhApplication
 * @Description: TODO: (app 基类  提供：通用服务)
 * @date 2016/11/17 下午4:11
 */
public class EhApplication extends Application {

    private AppComponent mAppComponent;

    @Inject ActivityMgt activityMgt;

    @Inject RxBus rxBus;

    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();
    }

    private void initInjector() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        mAppComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    /**
     * 退出应用时，销毁所有的Activity
     */
    public void exit() {
        try {
            activityMgt.clear();
            rxBus.removeAllStickyEvents(); // 移除所有Sticky事件
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //如果开发者调用Process.kill或者System.exit之类的方法杀死进程，
            //请务必在此之前调用MobclickAgent.onKillProcess(Context context)方法，用来保存统计数据。
//            MobclickAgent.onKillProcess(this);
            System.exit(0);
        }
    }
}
