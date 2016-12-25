package com.focuschina.ehealth_lib.http;

import com.focuschina.ehealth_lib.util.LogUtil;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: TasksRepository
 * @Description: TODO: (RX 任务池 1:对所有订阅事件统一管理; 2:对后台任务进行区分; 3:对视图中产生的订阅事件,根据其生命周期进行管理)
 * @date 16/6/6 下午7:08
 */
public class TasksRepository {

    private static final String TAG = "TasksRepository";

    public static enum TaskType {
        Default, Lifecycle, BG
    }

    private static CompositeSubscription bgSubscription;
    private CompositeSubscription defaultSubscription;
    private CompositeSubscription lifecycleSubscription;

    public TasksRepository() {
        this.defaultSubscription = new CompositeSubscription();
//        this.bgSubscription = new CompositeSubscription();
        bgNewInstance();
        this.lifecycleSubscription = new CompositeSubscription();
    }

    /**
     * 后台任务用静态持有
     *
     * @return 后台任务管理实例
     */
    private static CompositeSubscription bgNewInstance() {
        if (null == bgSubscription) {
            LogUtil.i(TAG, "new CompositeSubscription bg task!");
            bgSubscription = new CompositeSubscription();
        }
        return bgSubscription;
    }

    public static void addBgTask(Subscription subscription) {
        bgNewInstance().add(subscription);
    }

    public static void disposeBgTask(){
        bgNewInstance().unsubscribe();
        LogUtil.i(TAG, "TasksRepository-BG Capacity: " + bgSubscription.hasSubscriptions());
    }

    public void addTask(TaskType taskType, Subscription subscription) {
        switch (taskType) {
            case Lifecycle:
                lifecycleSubscription.add(subscription);
                break;
            case BG:
                addBgTask(subscription);
                break;
            default:
                defaultSubscription.add(subscription);
                break;
        }
    }

    /**
     * 添加跟生命周期绑定的任务
     *
     * @param subscription 任务订阅
     */
    public void addLifeCycleTask(Subscription subscription) {
        lifecycleSubscription.add(subscription);
    }

    /**
     * 移除跟生命周期绑定的任务
     */
    public void disposeByLifecycle() {
        lifecycleSubscription.unsubscribe();
        checkCurCapacity();
    }

    /**
     * 检查当前任务状态
     */
    public void checkCurCapacity() {
        LogUtil.i(TAG, "TasksRepository-Default Capacity: " + defaultSubscription.hasSubscriptions());
        LogUtil.i(TAG, "TasksRepository-Lifecycle Capacity: " + lifecycleSubscription.hasSubscriptions());
        LogUtil.i(TAG, "TasksRepository-BG Capacity: " + bgSubscription.hasSubscriptions());
    }

}
