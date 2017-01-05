package com.focuschina.ehealth_lib.http.datasource;

import com.focuschina.ehealth_lib.base.BaseView;
import com.focuschina.ehealth_lib.config.AppConfig;
import com.focuschina.ehealth_lib.config.SpHelper;
import com.focuschina.ehealth_lib.di.http.UnEncrypted;
import com.focuschina.ehealth_lib.http.api.BaseApi;
import com.focuschina.ehealth_lib.http.async.Async;
import com.focuschina.ehealth_lib.http.async.AsyncHandler;
import com.focuschina.ehealth_lib.task.TasksRepository;
import com.focuschina.ehealth_lib.model.HSPSService;
import com.focuschina.ehealth_lib.util.AppUtil;
import com.focuschina.ehealth_lib.util.JsonUtil;
import com.focuschina.ehealth_lib.util.LogUtil;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Retrofit;
import rx.Subscription;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: HspsDataSource
 * @Description: TODO: (单例模式，基础服务信息，获取配置，加载基础数据，更新。。。)
 * @date 2016/12/23 上午10:52
 */
@Singleton
public class HspsDataSource implements BaseDataSource<HSPSService> {

    private Retrofit retrofit;
    private AppConfig appConfig;
    private SpHelper spHelper;

    private HSPSService mHSPSService = null; //服务地址配置类,私有不允许公开,获取调用下面的方法

    @Inject
    public HspsDataSource(@UnEncrypted Retrofit retrofit, AppConfig appConfig, SpHelper spHelper) {
        this.retrofit = retrofit;
        this.appConfig = appConfig;
        this.spHelper = spHelper;
    }

    /**
     * 获取服务对象
     * 如果不走配置文件，直接return new HSPSService();空对象
     */
    @Override
    public HSPSService getSource() {
        if (null == mHSPSService) {
            String HSPSJsonStr = spHelper.getHSPSServiceFromLocal();
            mHSPSService = JsonUtil.toClass(HSPSJsonStr, HSPSService.class);
            if (null == mHSPSService) return new HSPSService();  //异常处理
        }
        return mHSPSService;
//        return new HSPSService();
    }

    @Override
    public void start() {
        Subscription startSub = Async.start(retrofit.create(BaseApi.ServiceApi.class).downloadMainifest(
                appConfig.getProductId(),
                AppConfig.APP_PLT_ID_ANDROID,
                appConfig.getVersion(),
                getSource().getTimestamp() //获取本地更新时间戳，用于跟服务效验比对是否需要更新
        ), new AsyncHandler<HSPSService, BaseView>() { //实际此处可以用jsonObject替代HSPS泛型对象，可以减少一次转换
            @Override
            public void onNext(HSPSService hspsService) {
                save(hspsService);
                update();
            }

            @Override
            public void onError(Throwable arg0) {
                super.onError(arg0);
                update();
            }
        });
        TasksRepository.addBgTask(startSub);
    }

    /**
     * 保存数据到本地
     *
     * @param hspsService 服务地址数据，多一步转换
     */
    @Override
    public void save(HSPSService hspsService) {
        String HSPSJsonStr = JsonUtil.toJson(hspsService).trim();
        //如果配置文件有更新,并且可以正常解析,效验服务地址返回的有效性
        if (!AppUtil.isEmpty(HSPSJsonStr)) {
            mHSPSService = null; //清除配置服务缓存
            spHelper.setHSPSServiceToLocal(HSPSJsonStr);
        }
    }

    /**
     * 构建基础数据请求地址
     *
     * @param paramKey 请求参数key
     * @return 返回请求服务地址
     */
    public String baseUrl(String paramKey) {
        String baseUrl = "";
        if (null != getSource().getCoreservice()) {
            try {
                baseUrl = getSource().getServerurl() + getSource().getCoreservice().get(paramKey).getAsString();
                baseUrl += "?"
                        + "productId=" + appConfig.getProductId()
                        + "&pltId=" + AppConfig.APP_PLT_ID_ANDROID
                        + "&version=" + "1.00.00"
                        + "&sversion=" + "1.00.00"
                        + "&sessionid=" + ""
                        + "&paramMethod=" + paramKey
                        + "&paramContent=";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return baseUrl;
    }

    /**
     * 更新获取医院数据
     */
    @Override
    public void update() {
        LogUtil.test("update hos data");
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(10000);
                    complete();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void complete() {
        TasksRepository.disposeBgTask();
    }

    @Override
    public void delete(Type type) {

    }

    @Override
    public void allClear() {

    }
}