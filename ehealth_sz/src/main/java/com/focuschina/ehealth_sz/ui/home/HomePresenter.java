package com.focuschina.ehealth_sz.ui.home;

import android.support.annotation.NonNull;

import com.focuschina.ehealth_lib.config.AppConfig;
import com.focuschina.ehealth_lib.http.api.BaseApi;
import com.focuschina.ehealth_lib.http.async.Async;
import com.focuschina.ehealth_lib.http.async.AsyncHandler;
import com.focuschina.ehealth_lib.http.datasource.HspsDataSource;
import com.focuschina.ehealth_lib.model.Response;
import com.focuschina.ehealth_lib.model.TBody;
import com.focuschina.ehealth_lib.model.common.ProductParam;
import com.focuschina.ehealth_lib.model.hosdata.Dep;
import com.focuschina.ehealth_lib.task.RxBus;
import com.focuschina.ehealth_lib.task.TasksRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscription;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: HomePresenter
 * @Description: TODO: (首页业务处理)
 * @date 2016/12/15 下午4:52
 */
public class HomePresenter implements MainContract.IHomePresenter {

    private Retrofit retrofit;

    private MainContract.HomeView mView;

    private TasksRepository tasksRepository;

    private HspsDataSource hspsDataSource;

    private AppConfig appConfig;

    private RxBus rxBus;

    private List<String> ids;
    private List<Dep> depList;

    @Inject
    public HomePresenter(Retrofit retrofit, TasksRepository tasksRepository,
                         HspsDataSource hspsDataSource, AppConfig appConfig, RxBus rxBus) {
        this.retrofit = retrofit;
        this.tasksRepository = tasksRepository;
        this.hspsDataSource = hspsDataSource;
        this.appConfig = appConfig;
        this.rxBus = rxBus;
    }

    @Inject
    @Override
    public void attachView(MainContract.HomeView view) {
        mView = view;
    }

    @Override
    public void checkTask() {
        if (tasksRepository.checkBgTask()) { //检查是否存在后台任务
            mView.showProgress();
            Subscription eventSub = rxBus.subscribeEventSticky(
                    HspsDataSource.EventType.class,
                    HspsDataSource.TAG,
                    event -> {
                        switch (event) {
                            case success:
                                mView.showMsg("更新成功");
                                break;
                        }
                        mView.hideProgress();
                    });
            tasksRepository.addLifeCycleTask(eventSub);
        }
    }

    @Override
    public void fetchDepListData() {
        mView.showProgress();
        //获取推荐科室ids
        Observable<Response<ProductParam<String>>> recommendDepOb = retrofit
                .create(BaseApi.ServiceApi.class)
                .getProductParam(
                        hspsDataSource.baseUrl(BaseApi.ServiceApi.GET_PRODUCT_PARAM),
                        new ProductParam.QueryParam("home_dep_list")
                );

        //获取科室列表数据
        Observable<Response<TBody<List<Dep>>>> depInfoOb = retrofit
                .create(BaseApi.ServiceApi.class)
                .searchDeptmentInfoNew(
                        hspsDataSource.baseUrl(BaseApi.ServiceApi.SEARCH_DEPTMENT_INFO_NEW),
                        new Dep.QueryParam(appConfig.getHosCode()
                        )
                );

        Subscription start = Async.start(Observable.merge(recommendDepOb, depInfoOb),
                new AsyncHandler<Object, MainContract.HomeView>(mView) {
                    @Override
                    public void onNext(Object o) {
                        if (null != o && o instanceof Response) initDepList((Response) o);
                    }
                });

        tasksRepository.addLifeCycleTask(start);
    }

    private void initDepList(Response response) {
        try {
            Object rspData = response.getRspData();
            if (rspData instanceof ProductParam) { //获得推荐科室Ids
                String value = (String) ((ProductParam) rspData).getParamValue();
                JsonArray returnData = new JsonParser().parse(value).getAsJsonArray();
                ids = new Gson().fromJson(returnData, new TypeToken<List<String>>() {
                }.getType());
            } else if (rspData instanceof TBody) { //获得科室列表数据
                depList = (List<Dep>) ((TBody) rspData).getBody();
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        if (null != ids && null != depList) { //筛选出首页推荐科室
            List<Dep> recommendDepList = new ArrayList<>();
            for (int i = 0; i < ids.size(); i++) {
                for (Dep d : depList) {
                    if (d.getDepartmentId().equals(ids.get(i))) {
                        recommendDepList.add(d);
                        break;
                    }
                }
            }
            //显示科室数据
            mView.showDepList(recommendDepList);
        }
    }

    /**
     * 封装单个科室数据
     *
     * @param depList 科室列表数据源
     */
    @Override
    public void fetchEachDepData(@NonNull List<Dep> depList) {
        Dep dep = new Dep();
        dep.setDepartmentName("更多");
        dep.setDepartmentId("");
        depList.add(dep);
        for (int i = 0; i < depList.size(); i++) {
            Dep eachDep = depList.get(i);
            mView.showEachDepView(i, eachDep);
        }
    }

    @Override
    public void detachView() {
        tasksRepository.disposeByLifecycle();
    }
}
