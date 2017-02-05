package com.focuschina.ehealth_lib.task;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: RxEvent
 * @Description: TODO: (消息事件类)
 * @date 2017/1/6 下午5:38
 */
public class RxEvent {

    private final String tag;
    private final Object data;
    private final Boolean sticky;

    public RxEvent(String tag, Object data, boolean sticky) {
        this.tag = tag;
        this.data = data;
        this.sticky = sticky;
    }

    public RxEvent(String tag, Object data) {
        this(tag, data, false);
    }

    public Object getData() {
        return data;
    }

    public String getTag() {
        return tag;
    }

    public Boolean isSticky() {
        return sticky;
    }
}
