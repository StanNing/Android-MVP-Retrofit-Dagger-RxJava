package com.focuschina.ehealth_lib.task;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: RxReceiver
 * @Description: TODO: (RxEvent 消息订阅接收器)
 * @date 2017/1/9 下午8:25
 */
public interface RxReceiver<E> {
    void onReceiveEvent(E event) throws Exception;

    void onReceiveFinish();
}
