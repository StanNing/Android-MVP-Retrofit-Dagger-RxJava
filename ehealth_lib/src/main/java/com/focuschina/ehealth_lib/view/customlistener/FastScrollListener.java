package com.focuschina.ehealth_lib.view.customlistener;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.AbsListView;

import com.focuschina.ehealth_lib.util.BmpUtil;
import com.squareup.picasso.Picasso;

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

    private final Context context;
    private final Picasso picasso;

    @Inject
    public FastScrollListener(@NonNull Context context, @NonNull BmpUtil bmpUtil) {
        this.context = context;
        this.picasso = bmpUtil.get();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            picasso.resumeTag(context);
        } else {
            picasso.pauseTag(context);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
