package com.focuschina.ehealth_lib.db.upgrade;

import android.database.sqlite.SQLiteDatabase;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: Migration
 * @Description: TODO: (数据库升级迁移操作)
 * @date 16/7/26 上午11:24
 */
public interface Migration {
    void migrate(SQLiteDatabase db);
}
