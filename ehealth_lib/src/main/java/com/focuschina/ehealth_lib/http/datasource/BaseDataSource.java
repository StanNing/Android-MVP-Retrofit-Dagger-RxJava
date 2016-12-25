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

    void start();

    void save(T t);

    T getSource();

    void update();

    void complete();

    void delete(Type type);

    void allClear();

    enum Type{

    }
}
