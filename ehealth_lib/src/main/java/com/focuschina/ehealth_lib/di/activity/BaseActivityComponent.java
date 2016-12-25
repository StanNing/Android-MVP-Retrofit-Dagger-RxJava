package com.focuschina.ehealth_lib.di.activity;

import android.app.Activity;
import android.widget.Toast;

import com.focuschina.ehealth_lib.base.BaseActivity;
import com.focuschina.ehealth_lib.base.BaseFragment;
import com.focuschina.ehealth_lib.di.app.AppComponent;
import com.focuschina.ehealth_lib.http.TasksRepository;
import com.focuschina.ehealth_lib.view.customlistener.FastScrollListener;

import dagger.Component;

/**
 * Created by stan on 2016/11/17.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface BaseActivityComponent {
    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);

    Activity activity();

    Toast toast();

    TasksRepository tasksRepository();

    FastScrollListener fastScrollListener(); //提供快速滑动不重复加载图片的监听器
}
