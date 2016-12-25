package com.focuschina.ehealth_sz.ui.home;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.focuschina.ehealth_lib.base.BaseFragment;
import com.focuschina.ehealth_lib.util.AppUtil;
import com.focuschina.ehealth_lib.util.BmpUtil;
import com.focuschina.ehealth_sz.R;
import com.focuschina.ehealth_sz.config.ResSizeCfg;

import javax.inject.Inject;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: HomeFragment
 * @Description: TODO: (首页)
 * @date 2016/12/15 下午3:12
 */
public class HomeFragment extends BaseFragment implements MainContract.HomeView {

    @Inject
    HomePresenter homePresenter;

    @Inject
    BmpUtil bmpUtil;

    private ImageView homeBannerIv;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_home;
    }

    @Override
    protected void onSingleClick(View v) {
    }

    @Override
    protected void bindView(Bundle bundle) {
        homeBannerIv = findViewById(R.id.home_banner_img);
        resizeBanner();
    }

    /**
     * 重设banner高度
     */
    private void resizeBanner() {
        ViewGroup.LayoutParams lp = homeBannerIv.getLayoutParams();
        lp.height = bmpUtil.resizeH(
                ResSizeCfg.BANNER_HOME_W,
                ResSizeCfg.BANNER_HOME_H,
                AppUtil.getWindowWidthPix(getActivity()));
        homeBannerIv.setLayoutParams(lp);
    }

    @Override
    protected void initData() {
        homePresenter.doTest();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void showHomeView() {
        showMsg("第一页显示完毕");
    }
}
