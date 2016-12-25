package com.focuschina.ehealth_lib.di.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.focuschina.ehealth_lib.EhApplication;
import com.focuschina.ehealth_lib.config.AppConfig;
import com.focuschina.ehealth_lib.config.SpHelper;
import com.focuschina.ehealth_lib.di.db.DbModule;
import com.focuschina.ehealth_lib.di.http.NetModule;
import com.focuschina.ehealth_lib.di.http.UnEncrypted;
import com.focuschina.ehealth_lib.http.datasource.HspsDataSource;
import com.focuschina.ehealth_lib.mgt.ActivityMgt;
import com.focuschina.ehealth_lib.model.account.DaoMaster;
import com.focuschina.ehealth_lib.model.account.DaoSession;
import com.focuschina.ehealth_lib.util.BmpUtil;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Copyright (C) Focus Technology
 *
 * @ClassName: AppComponent
 * @Description: TODO: (app 对象管理类)
 * @author: Ning Jiajun
 * @date: 2016/12/13 上午11:26
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class, DbModule.class})
public interface AppComponent {
    void inject(EhApplication ehApplication);

    Context context();

    @UnEncrypted
    Retrofit UnEncryptedRetrofit(); //提供网络服务 非加密的

    Retrofit retrofit(); //提供网络服务

    SpHelper spHelper(); //提供sharedPreference功能

    AppConfig appConfig(); //提供配置管理类

    DaoSession daoSession(); //提供数据库操作

    DaoMaster daoMaster();

    SQLiteDatabase sqLiteDatabase();

    ActivityMgt activityMgt(); //提供activity manager

    BmpUtil bmpUtil(); //提供图片处理类

    HspsDataSource hspsDataSource(); //提供基础服务
}
