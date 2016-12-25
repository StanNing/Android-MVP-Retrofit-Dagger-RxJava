package com.focuschina.ehealth_lib.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Copyright (C) Focus Technology
 *
 * @ClassName: SpHelper
 * @Description: TODO: (SP)
 * @author: Ning Jiajun
 * @date: 16/6/1 下午5:10
 */
public class SpHelper {
    private static final String SP_HSPS_SERVICE = "SP_HSPS_SERVICE";//HSPS服务地址

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SpHelper(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sp.edit();
    }

    public void putTestJson(String testJson) {
        editor.putString("testJson", testJson);
        editor.commit();
    }

    /**
     * 服务地址本地存储
     *
     * @param HSPSJson
     */
    public void setHSPSServiceToLocal(String HSPSJson) {
        editor.putString(SP_HSPS_SERVICE, HSPSJson);
        editor.commit();
    }

    /**
     * 获取本地服务
     *
     * @return
     */
    public String getHSPSServiceFromLocal() {
        return sp.getString(SP_HSPS_SERVICE, "");
    }
}
