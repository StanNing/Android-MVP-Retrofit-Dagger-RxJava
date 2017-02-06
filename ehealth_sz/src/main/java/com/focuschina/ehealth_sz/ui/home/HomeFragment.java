package com.focuschina.ehealth_sz.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.focuschina.ehealth_lib.base.BaseFragment;
import com.focuschina.ehealth_lib.di.activity.ForActivity;
import com.focuschina.ehealth_lib.model.hosdata.Dep;
import com.focuschina.ehealth_lib.util.AppUtil;
import com.focuschina.ehealth_lib.util.BmpUtil;
import com.focuschina.ehealth_sz.R;
import com.focuschina.ehealth_sz.config.ResSizeCfg;

import java.util.List;

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
    @ForActivity
    BmpUtil bmpUtil;

    LayoutInflater inflater;

    private ImageView homeBannerIv;
    private TextView resBtn; //预约按钮
    private LinearLayout depMainLl; //科室列表布局

    private int[] depIcons = new int[]{
            R.mipmap.ic_dep_1,
            R.mipmap.ic_dep_2,
            R.mipmap.ic_dep_3,
            R.mipmap.ic_dep_4,
            R.mipmap.ic_dep_5,
            R.mipmap.ic_dep_6}; //最后一个为更多

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_home;
    }

    @Override
    protected void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.home_res_btn:
                break;
        }
    }

    @Override
    protected void bindView(Bundle bundle) {
        inflater = getLayoutInflater(bundle);
        homeBannerIv = findViewById(R.id.home_banner_img);
        resizeBanner();
        resBtn = findViewById(R.id.home_res_btn);
        bindClickEvent(resBtn);
        depMainLl = findViewById(R.id.home_dep_main_ll);
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
        homePresenter.checkTask(); //检查任务
        homePresenter.fetchDepListData(); //获取科室数据
    }

    /**
     * 显示科室布局
     *
     * @param depList 科室列表数据
     */
    @Override
    public void showDepList(List<Dep> depList) {
        if (null == depList) { //无科室列表数据，直接隐藏布局
            depMainLl.setVisibility(View.GONE);
            return;
        }
        homePresenter.fetchEachDepData(depList);
    }

    /**
     * 展示每个科室
     *
     * @param index 数据索引
     * @param dep   数据
     */
    @Override
    public void showEachDepView(int index, Dep dep) {
        if (index >= depIcons.length || index < 0) return; //容错
        if (index % 2 == 0) { //动态创建每行布局
            LinearLayout innerLl = new LinearLayout(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.topMargin = AppUtil.dp2px(getActivity(), 1);
            innerLl.setLayoutParams(lp);
            innerLl.setOrientation(LinearLayout.HORIZONTAL);
            depMainLl.addView(innerLl);
        }
        ((LinearLayout) depMainLl.getChildAt(depMainLl.getChildCount() - 1))
                .addView(getChildLayout(index, dep));
    }

    /**
     * @param index 数据索引
     * @param dep   数据
     * @return 每个子布局
     */
    private View getChildLayout(int index, Dep dep) {
        int iconResId = depIcons[index];
        LinearLayout.LayoutParams childLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        childLp.weight = 1.0f;
        childLp.rightMargin = AppUtil.dp2px(getActivity(), 1);
        RelativeLayout childRl = (RelativeLayout) inflater.inflate(R.layout.view_dep_module, null);
        childRl.setLayoutParams(childLp);
        ImageView depIconIv = (ImageView) childRl.findViewById(R.id.dep_module_icon_iv); //科室图表
        TextView depNameTx = (TextView) childRl.findViewById(R.id.dep_module_name_tx); //科室名
        //绑定数据
        depIconIv.setImageResource(iconResId);
        depNameTx.setText(dep.getDepartmentName());
        depNameTx.setTag(dep.getDepartmentId());
        //绑定点击事件
        bindClickEventOb(childRl).subscribe(aVoid -> {
            showMsg(depNameTx.getText());
        });
        return childRl;
    }

    @Override
    public void destroy() {
        homePresenter.detachView();
    }
}
