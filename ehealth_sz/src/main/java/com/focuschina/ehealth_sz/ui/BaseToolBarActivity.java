package com.focuschina.ehealth_sz.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.focuschina.ehealth_lib.base.BaseActivity;
import com.focuschina.ehealth_lib.base.BaseDialog;
import com.focuschina.ehealth_sz.R;
import com.focuschina.ehealth_sz.ui.splash.dialog.LoadingDialog;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: BaseToolBarActivity
 * @Description: TODO: (带导航栏的基类 activity)
 * @date 2016/12/14 上午10:50
 */
public abstract class BaseToolBarActivity extends BaseActivity {

    private Toolbar mToolbar; //标题栏
    private TextView mToolbarTitle; //标题

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
    }

    /**
     * 初始化标题栏
     */
    protected void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar); //切记，资源此处统一定义ID为toolbar,不然找不到资源
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        ActionBar actionBar = null;
        if (mToolbar != null) {
            //将Toolbar显示到界面
            setSupportActionBar(mToolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_back_btn_white);
        }
        if (mToolbarTitle != null && null != actionBar) {
            //设置默认的标题不显示, 使用自定义标题
            actionBar.setDisplayShowTitleEnabled(false);
            mToolbarTitle.setText(getTitleTx());
        }
    }

    /**
     * 隐藏后退按钮
     */
    protected void hideHomeAsUp() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    /**
     * 设置标题居中
     */
    protected void setTitleInCenter() {
        if (mToolbarTitle != null) {
            mToolbarTitle.setGravity(Gravity.CENTER);
            Toolbar.LayoutParams lp = new Toolbar.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            mToolbarTitle.setLayoutParams(lp);
        }
    }

    /**
     * 更改左侧按钮图标
     *
     * @param iconDrawableId  左侧图标资源ID
     * @param onClickListener 左侧图标点击事件
     */
    protected void setLeftIcon(int iconDrawableId, View.OnClickListener onClickListener) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mToolbar.setNavigationIcon(iconDrawableId);
        mToolbar.setNavigationOnClickListener(onClickListener);
    }

    protected int getTitleMenu() {
        return 0;
    }

    protected String getTitleTx() {
        return "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuRid = getTitleMenu();
        if (0 == menuRid) {
            super.onCreateOptionsMenu(menu);
        } else {
            getMenuInflater().inflate(menuRid, menu);
            bindMenuView(menu);
        }
        return true;
    }

    protected void bindMenuView(Menu menu) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private volatile LoadingDialog loadingDialog; //加载进度条
    private volatile int showCount = 0;

    @Override
    protected BaseDialog getLoadingDialog() {
        if (null == loadingDialog) {
            synchronized (LoadingDialog.class) {
                if (null == loadingDialog) {
                    showCount = 0;
                    loadingDialog = LoadingDialog.newInstance(super.bmpUtil);
                }
            }
        }
        return loadingDialog;
    }

    @Override
    public synchronized void showProgress() {
        synchronized (LoadingDialog.class) {
            if (showCount == 0) {
                super.showProgress();
            }
            showCount++;
        }
    }

    @Override
    public void hideProgress() {
        synchronized (LoadingDialog.class) {
            showCount--;
            if (showCount > 0) {
                return;
            }
            super.hideProgress();
            loadingDialog = null;
        }
    }
}
