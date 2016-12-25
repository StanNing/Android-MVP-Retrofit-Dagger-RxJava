package com.focuschina.ehealth_lib.http.async;

import com.focuschina.ehealth_lib.config.AppConfig;
import com.focuschina.ehealth_lib.util.LogUtil;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName:
 * @date 16/5/25 下午5:06
 */
public abstract class AsyncHandler<T> {

    public void onNext(T t) {

    }

    public void onCompleted() {
//            SysManager.getInstance().dismissProgressDialog();
    }

    public void onError(Throwable arg0) {
//            SysManager.getInstance().dismissProgressDialog();
        if (AppConfig.isPrint()) LogUtil.i(LogUtil.TAG_URL, "onError:" + arg0);
//        AppUtil.showToast(context, R.string.net_error_msg);
    }
}
