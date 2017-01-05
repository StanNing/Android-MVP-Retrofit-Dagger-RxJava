package com.focuschina.ehealth_lib.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: BaseDialog
 * @Description: TODO: (dialog 基类)
 * @date 2016/12/8 下午4:02
 */
public abstract class BaseDialog extends DialogFragment {

    enum DialogType {
        mid, bottom, top, full
    }

    private View mRootView;
    private SparseArray<View> mViews;
    Window window;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, getDialogStyle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getDialogLayout(), container);
        mViews = new SparseArray<>();
        window = getDialog().getWindow();
        bindView(savedInstanceState);
        return mRootView;
    }

    /**
     * 绑定视图
     *
     * @param bundle savedInstanceState
     */
    protected void bindView(Bundle bundle) {
        build(DialogType.mid);
    }

    /**
     * 再次根据选择的类型进行布局设置
     *
     * @param type 风格
     * @return dialog
     */
    protected BaseDialog build(DialogType type) {
        switch (type) {
            case mid:
                break;
            case bottom:
                initBottomDialog();
                break;
            case top:
                break;
            case full:
                break;
            default:
                break;
        }
        return this;
    }

    protected void initBottomDialog() {
        if (null != window) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            //设置dialog的位置在底部
            lp.gravity = Gravity.BOTTOM;
            window.setAttributes(lp);
            window.setGravity(Gravity.BOTTOM);
        }
    }

    public void setAnimations(@StyleRes int animationId) {
        if (null != window) window.setWindowAnimations(animationId);
    }

    public void show(FragmentActivity activity) {
        this.show(activity.getSupportFragmentManager(), getDialogTag());
    }

    public void show(Fragment fragment) {
        this.show(fragment.getFragmentManager(), getDialogTag());
    }

    protected abstract String getDialogTag(); //获取dialog的标签

    protected abstract int getDialogLayout(); //获取dialog的布局

    protected abstract int getDialogStyle(); //获取dialog的风格

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

}
