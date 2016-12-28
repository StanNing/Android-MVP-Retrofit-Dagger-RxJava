package com.focuschina.ehealth_lib.model;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: TBody
 * @Description: TODO: (通用解析类 嵌套)
 * @date 2016/12/28 下午5:07
 */
public class TBody<T> {

    private T body;

    public TBody() {
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
