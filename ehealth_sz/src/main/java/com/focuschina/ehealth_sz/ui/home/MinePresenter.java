package com.focuschina.ehealth_sz.ui.home;

import com.focuschina.ehealth_lib.config.AppConfig;
import com.focuschina.ehealth_lib.di.http.UnEncrypted;
import com.focuschina.ehealth_lib.http.async.Async;
import com.focuschina.ehealth_lib.http.async.AsyncHandler;
import com.focuschina.ehealth_lib.http.TasksRepository;
import com.focuschina.ehealth_lib.http.api.BaseApi;
import com.focuschina.ehealth_lib.model.HSPSService;

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
public class MinePresenter extends MainContract.AbMinePresenter {

    private Retrofit retrofit;

    private MainContract.MineView mView;

    private TasksRepository tasksRepository;

    private AppConfig appConfig;

    @Inject
    public MinePresenter(Retrofit retrofit, TasksRepository tasksRepository, AppConfig appConfig) {
        this.retrofit = retrofit;
        this.tasksRepository = tasksRepository;
        this.appConfig = appConfig;
    }

    @Override
    void doTest() {
        mView.showMineView();
//        initTestReq();
    }

//    private void initTestReq() {
//        Async.start(retrofit.create(BaseApi.ServiceApi.class).downloadMainifest(
//                AppConfig.APP_PRODUCT_ID_EH_NJ,
//                AppConfig.APP_PLT_ID_ANDROID,
//                appConfig.getVersion(),
//                appConfig.getSever().getTimestamp()
//        ), new AsyncHandler<HSPSService>() {
//            @Override
//            public void onNext(HSPSService hspsService) {
//                mView.showMsg("响应成功");
//                super.onNext(hspsService);
//            }
//        });
//    }

    @Inject
    @Override
    public void attachView(MainContract.MineView view) {
        mView = view;
    }

    @Override
    public void detachView() {

    }
}
