package com.focuschina.ehealth_lib.di.app;

import android.app.Application;
import android.content.Context;

import com.focuschina.ehealth_lib.config.AppConfig;
import com.focuschina.ehealth_lib.config.SpHelper;
import com.focuschina.ehealth_lib.mgt.ActivityMgt;
import com.focuschina.ehealth_lib.util.BmpUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName:
 * @Description: TODO: (这里用一句话描述这个类的作用)
 * @date 2016/11/17 下午4:35
 */
@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    SpHelper provideSharedPreferences() {
        return new SpHelper(application);
    }

    @Provides
    @Singleton
    AppConfig provideAppConfig(SpHelper spHelper, Context context) {
        return new AppConfig(spHelper, context);
    }

    @Provides
    @Singleton
    ActivityMgt provideActivityMgt() {
        return new ActivityMgt();
    }

    @Provides
    @Singleton
    BmpUtil provideBmpUtil(Context context) {
        return new BmpUtil(context);
    }

}
