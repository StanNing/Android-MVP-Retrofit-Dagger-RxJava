package com.focuschina.ehealth_lib.base;

import com.focuschina.ehealth_lib.http.TasksRepository;

import rx.Subscription;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: TaskPresenter
 * @Description: TODO: (任务统一处理)
 * @date 2016/12/12 下午4:07
 */
public abstract class TaskPresenter<T extends BaseView> implements BasePresenter<T> {

    @Override
    public void subscribe(TasksRepository tasksRepository, Subscription subscription) {
        if (null != tasksRepository) { //添加任务
            tasksRepository.addLifeCycleTask(subscription);
        }
    }

    @Override
    public void unsubscribe(TasksRepository tasksRepository) {
        if (null != tasksRepository) { //销毁任务池,移除任务
            tasksRepository.disposeByLifecycle();
        }
    }
}
