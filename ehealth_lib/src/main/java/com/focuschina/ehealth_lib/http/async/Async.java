package com.focuschina.ehealth_lib.http.async;

import android.support.annotation.NonNull;

import com.focuschina.ehealth_lib.base.BaseView;
import com.focuschina.ehealth_lib.model.Response;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Copyright (C) Focus Technology
 *
 * @ClassName: Async
 * @Description: TODO: (异步任务工作类)
 * @author: Ning Jiajun
 * @date: 16/7/19 下午3:16
 */
public class Async {

    /**
     * 自由处理回调的请求
     *
     * @param observable
     * @param asyncHandler
     * @param <T>
     * @return
     */
    public static <T, V extends BaseView> Subscription start(Observable<T> observable,
                                                             final AsyncHandler<T, V> asyncHandler) {
        return observable
                .subscribeOn(Schedulers.io()) //被订阅者发生的线程
                .observeOn(AndroidSchedulers.mainThread()) //消费者运行的线程
                .subscribe(
                        asyncHandler::onNext, //成功响应
                        asyncHandler::onError, //错误异常
                        asyncHandler::onCompleted);
    }

    /**
     * 无需处理回调的请求
     *
     * @param observable
     * @param <T>
     * @return
     */
    public static <T, V extends BaseView> Subscription start(Observable<T> observable) {
        final AsyncHandler<T, V> asyncHandler = new AsyncHandler<T, V>() { //无需重写回调处理
        };
        return observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        asyncHandler::onNext,
                        asyncHandler::onError,
                        asyncHandler::onCompleted);
    }

    /**
     * 链式调用请求处理 2个请求  test (可以用concat+first做? 貌似不能...)
     * 第二个请求不需要使用第一个请求的返回数据,响应后直接请求
     *
     * @param asyncHandler
     * @param obT
     * @param obK
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K, V extends BaseView> Subscription composite(final AsyncHandler<Response<K>, V> asyncHandler,
                                                                    @NonNull Observable<Response<T>> obT,
                                                                    @NonNull final Observable<Response<K>> obK) {
        return obT
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(
                        response -> response.getRspCode() == Response.SUCCESS ? //响应是否成功
                                obK //成功则进入下一个请求
                                :
                                Observable.just(new Response<K>().builder(
                                        response.getRspCode(),
                                        response.getRspMsg(),
                                        response.getOprTime())))//构造一个异常返回实体
                .subscribe(
                        asyncHandler::onNext, //成功响应
                        asyncHandler::onError, //错误异常
                        asyncHandler::onCompleted);
    }

}

