package com.focuschina.ehealth_lib.config;

import android.content.Context;

import com.focuschina.ehealth_lib.util.AppUtil;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName:
 * @Description:
 * @date 16/5/12 下午4:37
 */
public class AppConfig {

    private SpHelper spHelper;
    private Context appContext;

    public AppConfig(SpHelper spHelper, Context appContext) {
        this.spHelper = spHelper;
        this.appContext = appContext;
    }

    /**
     * 是否debug
     */
    public static final boolean DEBUG = true;
    /**
     * 正式版还是p版
     * false--p板
     * ture --正式
     */
    public static boolean BUILD = false;

    /**
     * 是否打印
     */
    public static boolean isPrint() {
        return !BUILD && DEBUG;
    }

    public static final String APP_PLT_ID_ANDROID = "02";
    public static final String APP_PRODUCT_ID_EH_NJ = "001";

    public String getVersion() {
        return AppUtil.getAppVersionName(appContext);
    }
}
