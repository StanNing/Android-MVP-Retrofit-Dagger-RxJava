package com.focuschina.ehealth_sz.ui.home;

import android.os.Bundle;
import android.view.View;

import com.focuschina.ehealth_lib.base.BaseFragment;
import com.focuschina.ehealth_sz.R;

import javax.inject.Inject;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: MineFragment
 * @Description: TODO: (我的)
 * @date 2016/12/15 下午3:29
 */
public class MineFragment extends BaseFragment implements MainContract.MineView {

    @Inject
    MinePresenter minePresenter;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_mine;
    }

    @Override
    protected void onSingleClick(View v) {

    }

    @Override
    protected void bindView(Bundle bundle) {

    }

    @Override
    protected void initData() {
        minePresenter.doTest();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void showMineView() {
        showMsg("第二页显示完毕");
    }
}
