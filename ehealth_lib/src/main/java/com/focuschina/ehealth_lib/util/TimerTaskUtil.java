package com.focuschina.ehealth_lib.util;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: TimerTaskUtil
 * @Description: TODO: (时间工具类)
 * @date 16/6/3 下午5:23
 */
public class TimerTaskUtil {

    /**
     * 倒数计时
     * @param time 秒
     * @return 计时器
     */
    public static Observable<Integer> countdown(int time) {
        if (time < 0) time = 0; //倒计时时间>=0
        final int countTime = time;
        return Observable
                .interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(increaseTime -> countTime - increaseTime.intValue())
                .take(countTime + 1);
    }

}
