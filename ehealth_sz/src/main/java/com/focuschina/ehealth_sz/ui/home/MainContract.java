package com.focuschina.ehealth_sz.ui.home;

import com.focuschina.ehealth_lib.base.BasePresenter;
import com.focuschina.ehealth_lib.base.BaseView;
import com.focuschina.ehealth_lib.model.hosdata.Dep;

import java.util.List;

/**
 * Copyright (C) Focus Technology
 *
 * @ClassName: MainContract
 * @Description: TODO: (main 契约)
 * @author: Ning Jiajun
 * @date: 2016/12/15 下午3:55
 */
public interface MainContract {

    interface MainView {
        void switchTab(int position);
    }

    interface HomeView extends BaseView {
        void showDepList(List<Dep> depList); //展现科室列表

        void showEachDepView(int index, Dep dep); //展现单个科室
    }

    interface MineView extends BaseView {

    }

    interface IHomePresenter extends BasePresenter<HomeView> {
        void fetchDepListData(); //获取首页展示的科室列表数据

        void fetchEachDepData(List<Dep> depList); //获取单个科室数据
    }

    interface IMinePresenter extends BasePresenter<MineView> {
    }
}
