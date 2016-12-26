package com.focuschina.ehealth_lib.config;

import android.content.Context;

import com.focuschina.ehealth_lib.util.AppUtil;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: AppConfig
 * @Description: App配置类，单例
 * @date 16/5/12 下午4:37
 */
public class AppConfig {

    private SpHelper spHelper;
    private Context appContext;
    private String pkgName;
    private String productId;

    public AppConfig(SpHelper spHelper, Context appContext) {
        this.spHelper = spHelper;
        this.appContext = appContext;
        this.pkgName = AppUtil.getPackageNames(appContext);
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
    private static final String APP_PRODUCT_ID_EH_SZ = "020";

    private static final String APP_PKG_NAME_NJ = "com.focustech.medical"; // 健康南京App
    private static final String APP_PKG_NAME_GL = "com.focustech.mmgl";    // 鼓楼医院App
    private static final String APP_PKG_NAME_SZ = "com.focustech.jshtcm";    // 省中医院App


    public String getVersion() {
        return AppUtil.getAppVersionName(appContext);
    }

    /**
     * 获取产品ID
     *
     * @return productId
     */
    public String getProductId() {
        if (null == productId) {
            switch (pkgName) {
                case APP_PKG_NAME_SZ:
                    productId = APP_PRODUCT_ID_EH_SZ;
                    break;
            }
        }
        return productId;
    }

}
