package com.focuschina.ehealth_lib.di.activity;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.focuschina.ehealth_lib.http.TasksRepository;
import com.focuschina.ehealth_lib.util.BmpUtil;
import com.focuschina.ehealth_lib.view.customlistener.FastScrollListener;

import dagger.Module;
import dagger.Provides;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: ActivityModule
 * @Description: TODO: (activity 提供类)
 * @date 2016/11/22 下午4:06
 */
@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return activity;
    }

    @Provides
    @PerActivity
    Toast provideToast(Context context) {
        return Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    @Provides
    @PerActivity
    TasksRepository provideTasksRepository() {
        return new TasksRepository();
    }

    @Provides
    @PerActivity
    FastScrollListener provideFastScrollListener(BmpUtil bmpUtil) {
        return new FastScrollListener(activity, bmpUtil);
    }
}
