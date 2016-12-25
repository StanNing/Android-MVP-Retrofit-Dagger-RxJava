package com.focuschina.ehealth_lib.mgt;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: IMgt
 * @Description: TODO: (基本管理服务)
 * @date 2016/12/14 下午5:20
 */
public interface IMgt<T> {
    void addToMgt(T t); //添加进管理

    void remove(T t); //移除管理

    void checkMgtStatus(); //检查管理状态

    T getLastMember(); //获取管理类最后一个

    void clear(); //清空管理
}
