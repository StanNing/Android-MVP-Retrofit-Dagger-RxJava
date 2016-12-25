package com.focuschina.ehealth_sz.ui.home;

import com.focuschina.ehealth_lib.config.AppConfig;
import com.focuschina.ehealth_lib.http.TasksRepository;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: HomePresenter
 * @Description: TODO: (这里用一句话描述这个类的作用)
 * @date 2016/12/15 下午4:52
 */
public class HomePresenter extends MainContract.AbHomePresenter {

    private Retrofit retrofit;

    private MainContract.HomeView mView;

    private TasksRepository tasksRepository;

    private AppConfig appConfig;

    @Inject
    public HomePresenter(Retrofit retrofit, TasksRepository tasksRepository, AppConfig appConfig) {
        this.retrofit = retrofit;
        this.tasksRepository = tasksRepository;
        this.appConfig = appConfig;
    }

    @Inject
    @Override
    public void attachView(MainContract.HomeView view) {
        mView = view;
    }

    @Override
    void doTest() {
        mView.showHomeView();
    }

    @Override
    public void detachView() {

    }
}
