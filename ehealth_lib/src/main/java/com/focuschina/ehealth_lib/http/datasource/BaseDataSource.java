package com.focuschina.ehealth_lib.http.datasource;

/**
 * Copyright (C) Focus Technology
 *
 * @ClassName: BaseDataSource
 * @Description: TODO: (数据源获取 基类)
 * @author: Ning Jiajun
 * @date: 2016/12/23 上午10:13
 */
public interface BaseDataSource<T> {

    void start(); //开始

    void save(T t); //保存

    T getSource(); //获取

    void update(); //更新

    void complete(); //完成

    void delete(Type type); //删除

    void allClear(); //清空

    enum Type {

    }
}
