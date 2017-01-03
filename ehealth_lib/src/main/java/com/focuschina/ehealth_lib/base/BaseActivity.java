package com.focuschina.ehealth_lib.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.focuschina.ehealth_lib.EhApplication;
import com.focuschina.ehealth_lib.di.activity.ActivityModule;
import com.focuschina.ehealth_lib.di.activity.BaseActivityComponent;
import com.focuschina.ehealth_lib.di.activity.DaggerBaseActivityComponent;
import com.focuschina.ehealth_lib.mgt.ActivityMgt;
import com.focuschina.ehealth_lib.util.BmpUtil;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: BaseActivity
 * @Description: TODO: (Activity 基类)
 * @date 2016/11/17 下午5:00
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    public static long defaultWindowDuration = 2; //默认点击间隔

    @Inject
    Toast toast;

    @Inject
    ActivityMgt activityMgt;

    @Inject
    protected BmpUtil bmpUtil;

    private BaseActivityComponent baseActivityComponent;

    protected ActivityModule activityModule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bindView(savedInstanceState);
        initInjector();
        activityMgt.addToMgt(this); //等依赖注入完成后添加进管理
    }

    protected abstract int getLayoutId();

    protected abstract void bindView(Bundle bundle);

    /**
     * 初始化需要依赖
     */
    protected void initInjector() {
        this.activityModule = new ActivityModule(this);
        baseActivityComponent = DaggerBaseActivityComponent.builder()
                .appComponent(((EhApplication) getApplication()).getAppComponent())
                .activityModule(activityModule)
                .build();
        baseActivityComponent.inject(this);
    }

    public ActivityModule getActivityModule() {
        return activityModule;
    }

    public BaseActivityComponent getBaseActivityComponent() {
        return baseActivityComponent;
    }

    /**
     * 绑定控件点击监听事件,限制2秒内重复点击
     *
     * @param v 点击视图
     * @return 返回Observable<Void>,需要自己实现回调监听
     */
    protected Observable<Void> bindClickEventOb(View v) {
        return RxView.clicks(v).throttleFirst(defaultWindowDuration, TimeUnit.SECONDS);
    }

    /**
     * 绑定控件点击监听事件,限制2秒内重复点击
     *
     * @param v 绑定的视图
     */
    protected void bindClickEvent(View v) {
        bindClickEventOb(v).subscribe(aVoid -> onSingleClick(v));
    }

    protected abstract void onSingleClick(View v);

    protected void showToast(String content) {
        if (null != toast) {
            toast.setText(content);
            toast.show();
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(Object message) {
        String msg = "";
        try { //check res Id or String msg
            msg = message instanceof Integer ? getString((Integer) message) : message + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        showToast(msg);
    }

    @Override
    protected void onDestroy() {
        destroy();
        bmpUtil.cancelTag(this); //回收标记，防止内存泄露
        activityMgt.remove(this);
        super.onDestroy();
    }

    protected void rplFrag(int r, Fragment frag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(r, frag);
        fragmentTransaction.commit();
    }

    protected <T extends Activity> void startAct(Class<T> act) {
        startActivity(new Intent(this, act));
    }
}
