package com.focuschina.ehealth_lib.di.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.focuschina.ehealth_lib.db.DbHelper;
import com.focuschina.ehealth_lib.model.account.DaoMaster;
import com.focuschina.ehealth_lib.model.account.DaoSession;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: DbModule
 * @Description: TODO: (db module 数据库对象)
 * @date 2016/12/8 下午2:00
 */
@Module
public class DbModule {

    @Singleton
    @Provides
    DaoSession provideDaoSession(DaoMaster daoMaster) {
        return daoMaster.newSession();
    }

    @Singleton
    @Provides
    DaoMaster provideDaoMaster(SQLiteDatabase database) {
        return new DaoMaster(database);
    }

    @Singleton
    @Provides
    SQLiteDatabase provideDatabase(Context context) {
        return new DbHelper(context, null).getWritableDatabase();
    }
}
