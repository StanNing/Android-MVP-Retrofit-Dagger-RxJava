package com.focuschina.ehealth_lib.db.upgrade;

import android.database.sqlite.SQLiteDatabase;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName:
 * @Description: TODO: (这里用一句话描述这个类的作用)
 * @date 16/7/26 上午11:25
 */
public class V1Migration implements Migration{
    @Override
    public void migrate(SQLiteDatabase db) {
//        db.execSQL("ALTER TABLE NOTE ADD COLUMN test"); // test
    }
}
