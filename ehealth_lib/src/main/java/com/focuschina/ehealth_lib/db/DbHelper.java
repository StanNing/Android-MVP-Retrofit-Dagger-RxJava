package com.focuschina.ehealth_lib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.focuschina.ehealth_lib.db.upgrade.Migration;
import com.focuschina.ehealth_lib.db.upgrade.V1Migration;
import com.focuschina.ehealth_lib.model.account.DaoMaster;
import com.focuschina.ehealth_lib.model.account.UserLoginDao;
import com.focuschina.ehealth_lib.util.LogUtil;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName:
 * @Description: TODO: (这里用一句话描述这个类的作用)
 * @date 2016/12/8 上午9:45
 */
public class DbHelper extends DaoMaster.OpenHelper {

    private static final String DB_NAME = "eh.db";

    public DbHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        LogUtil.i(DbHelper.class.getSimpleName(), "Upgrade from" + oldVersion + "to" + newVersion);

        MigrationHelper.migrate(sqLiteDatabase, UserLoginDao.class); //检查执行更新

        /**自定义更新，暂时不用，改用第三方升级工具*/
//        SortedMap<Integer, Migration> migrations = ALL_MIGRATIONS.subMap(oldVersion, newVersion);
//        executeMigrations(sqLiteDatabase, migrations.keySet());
    }

    /**
     * 执行升级 暂时弃置不用
     *
     * @param paramSQLiteDatabase 数据库
     * @param migrationVersions   升级需要执行的版本
     */
    private void executeMigrations(final SQLiteDatabase paramSQLiteDatabase,
                                   final Set<Integer> migrationVersions) {
        for (final Integer version : migrationVersions) {
            ALL_MIGRATIONS.get(version).migrate(paramSQLiteDatabase);
        }
    }

    private static final SortedMap<Integer, Migration> ALL_MIGRATIONS = new TreeMap<>();

    static { //升级更新版本存放
        ALL_MIGRATIONS.put(1, new V1Migration());
//        ALL_MIGRATIONS.put(2, new V2Migration());
//        ALL_MIGRATIONS.put(3, new V3Migration());
    }

}
