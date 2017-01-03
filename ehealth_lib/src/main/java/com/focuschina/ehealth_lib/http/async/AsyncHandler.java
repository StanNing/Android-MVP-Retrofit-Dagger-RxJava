package com.focuschina.ehealth_lib.http.async;

import com.focuschina.ehealth_lib.R;
import com.focuschina.ehealth_lib.base.BaseView;
import com.focuschina.ehealth_lib.config.AppConfig;
import com.focuschina.ehealth_lib.util.LogUtil;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: 请求回调，对返回通过baseView做了统一处理
 * @date 16/5/25 下午5:06
 */
public abstract class AsyncHandler<T, V extends BaseView> {

    V v;

    //不需要v做处理
    public AsyncHandler() {
    }

    //需要v做处理
    public AsyncHandler(V v) {
        this.v = v;
    }

    public void onNext(T t) {
    }

    public void onCompleted() {
        if (null != v) v.hideProgress();
    }

    public void onError(Throwable arg0) {
        if (AppConfig.isPrint()) LogUtil.i(LogUtil.TAG_URL, "onError:" + arg0);
        if (null != v) {
            v.hideProgress();
            v.showMsg(R.string.net_error_msg);
        }
    }

}
