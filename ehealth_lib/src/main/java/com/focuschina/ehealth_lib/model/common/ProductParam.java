package com.focuschina.ehealth_lib.model.common;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: ProductParam
 * @Description: TODO: (公共参数接口反参)
 * @date 2016/12/26 下午2:10
 */
public class ProductParam<T> {
    private String paramKey = "";//paramKey,//参数key
    private T paramValue;//paramValue,//参数value
    private String paramDesc = "";//paramDesc//参数描述

    public ProductParam() {
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public T getParamValue() {
        return paramValue;
    }

    public void setParamValue(T paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    public static class QueryParam {
        public String paramKey; //接口入参

        public QueryParam(String paramKey) {
            this.paramKey = paramKey;
        }
    }

}
