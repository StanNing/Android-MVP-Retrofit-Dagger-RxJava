package com.focuschina.ehealth_lib.view.customlistener;

import android.support.annotation.NonNull;
import android.widget.AbsListView;

import com.bumptech.glide.RequestManager;
import com.focuschina.ehealth_lib.di.activity.ForActivity;
import com.focuschina.ehealth_lib.util.BmpUtil;

import javax.inject.Inject;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: FastScrollListener
 * @Description: TODO: (用于展示图片的ListView,滑动过快重复加载图片)
 * @date 2016/12/19 上午10:18
 */
public class FastScrollListener implements AbsListView.OnScrollListener {

    //    private final Context context;
    //    private final Picasso picasso;
    private final RequestManager glide;

    @Inject
    public FastScrollListener(@NonNull @ForActivity BmpUtil bmpUtil) {
        this.glide = bmpUtil.get();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
//            picasso.resumeTag(context);
            glide.resumeRequests();
        } else {
//            picasso.pauseTag(context);
            glide.pauseRequests();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
