package com.focuschina.ehealth_sz.ui.splash;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.focuschina.ehealth_lib.EhApplication;
import com.focuschina.ehealth_lib.util.TimerTaskUtil;
import com.focuschina.ehealth_sz.R;
import com.focuschina.ehealth_sz.ui.BaseToolBarActivity;
import com.focuschina.ehealth_sz.ui.home.MainTabActivity;

import rx.Subscription;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: LoadingActivity
 * @Description: TODO: (应用启动页)
 * @date 2016/12/22 下午2:02
 */
public class LoadingActivity extends BaseToolBarActivity {

    private Bitmap bgBmp; //背景图片
    private ImageView bgIv; //背景容器
    private Subscription timer; //定时任务，3秒后跳转首页

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loading;
    }

    @Override
    protected void bindView(Bundle bundle) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //全屏

        bgIv = (ImageView) findViewById(R.id.bg_iv);
        bgBmp = BitmapFactory.decodeResource(this.getResources(), R.mipmap.splash);
        bgIv.setImageBitmap(bgBmp);

        ((EhApplication) getApplication()).getAppComponent().hspsDataSource().start(); //开启基础服务

        //启动页等待3秒
        timer = TimerTaskUtil.countdown(3).subscribe(
                integer -> {
                },
                throwable -> {
                },
                () -> { //on complete 计时完成
                    startAct(MainTabActivity.class);
                    finish();
                });
    }

    @Override
    protected void onSingleClick(View v) {

    }

    @Override
    public void destroy() {
        bmpUtil.clearBitmapUsage(bgBmp);
        bgBmp = null;
        timer.unsubscribe();
    }
}
