package com.focuschina.ehealth_sz.ui.home;

import com.focuschina.ehealth_lib.base.BaseView;
import com.focuschina.ehealth_lib.base.TaskPresenter;

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
        void showHomeView();
    }

    interface MineView extends BaseView {
        void showMineView();
    }

    abstract class AbHomePresenter extends TaskPresenter<HomeView> {
        abstract void doTest();
    }

    abstract class AbMinePresenter extends TaskPresenter<MineView> {
        abstract void doTest();
    }
}
