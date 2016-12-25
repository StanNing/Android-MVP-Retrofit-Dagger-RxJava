package com.focuschina.ehealth_lib.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.focuschina.ehealth_lib.util.LogUtil;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: BaseFragment
 * @Description: TODO: (fragment基类,activity必须继承BaseActivity; 使用lazy load,可见才加载)
 * @date 2016/12/1 下午5:07
 */
public abstract class BaseFragment extends Fragment implements BaseView {

    /**
     * 是否可见状态
     */
    private boolean isVisible;

    /**
     * 标志位，View已经初始化完成
     */
    private boolean isPrepared;

    /**
     * 是否第一次加载
     */
    private boolean isFirstLoad = true;

    protected View mRootView;

    @Inject
    Activity activity;

    @Inject
    Toast toast;
    private SparseArray<View> mViews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirstLoad = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), null);
            mViews = new SparseArray<>();
            bindView(savedInstanceState);
            initInjector();
            isPrepared = true;
            lazyLoad();
        } else {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }
        return mRootView;
    }

    /**
     * 初始化需要依赖
     */
    protected void initInjector() {
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).getBaseActivityComponent().inject(this);
        }
    }

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
    public void onDestroy() {
        destroy();
        super.onDestroy();
    }

    protected abstract int getLayoutId();

    /**
     * fragment中可以通过这个方法直接找到需要的view，而不需要进行类型强转
     *
     * @param viewId view 资源ID
     * @param <E>    view 类型
     * @return view
     */
    protected <E extends View> E findViewById(int viewId) {
        if (mRootView != null) {
            E view = (E) mViews.get(viewId);
            if (view == null) {
                view = (E) mRootView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return view;
        }
        return null;
    }

    /**
     * 绑定控件点击监听事件,限制2秒内重复点击
     *
     * @param v 点击视图
     */
    protected void bindClickEvent(View v) {
        RxView.clicks(v)
                .throttleFirst(BaseActivity.defaultWindowDuration, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    onSingleClick(v);
                });
    }

    protected abstract void onSingleClick(View v);

    protected abstract void bindView(Bundle bundle);

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
     * @param hidden hidden True if the fragment is now hidden, false if it is not
     *               visible.
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    /**
     * 要实现延迟加载Fragment内容,需要在 onCreateView
     * isPrepared = true;
     */
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;
        LogUtil.i(LogUtil.TAG_TEST, "Base Fragment lazyLoad initData!");
        initData();
    }

    protected abstract void initData();
}
