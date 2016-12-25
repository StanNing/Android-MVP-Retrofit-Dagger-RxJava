package com.focuschina.ehealth_lib.mgt;

import android.app.Activity;

import com.focuschina.ehealth_lib.util.LogUtil;

import java.util.Stack;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: ActivityMgt
 * @Description: TODO: (activity manager 管理类)
 * @date 2016/12/14 下午5:20
 */
public class ActivityMgt implements IMgt<Activity> {

    private Stack<Activity> mAcList;

    public ActivityMgt() {
        this.mAcList = new Stack<>();
    }

    @Override
    public void addToMgt(Activity activity) {
        if (null != mAcList) {
            LogUtil.test("add activity: " + activity.getClass().getSimpleName());
            mAcList.add(activity);
        }
    }

    @Override
    public void remove(Activity activity) {
        if (null != mAcList) {
            LogUtil.test("remove activity: " + activity.getClass().getSimpleName());
            mAcList.remove(activity);
        }
    }

    @Override
    public void checkMgtStatus() {
        if (null == mAcList) {
            LogUtil.test("mAcList = null, all clear");
            return;
        }
        LogUtil.test("mAcList size: " + mAcList.size());
        for (int i = 0; i < mAcList.size(); i++) {
            LogUtil.test("activity" + i + " = " + mAcList.get(i).getLocalClassName());
        }
    }

    @Override
    public Activity getLastMember() {
        return null == mAcList ? null : mAcList.get(mAcList.size() - 1);
    }

    @Override
    public void clear() {
        checkMgtStatus();
        if (null == mAcList) return;
        for (int i = 0; i < mAcList.size(); i++) {
            LogUtil.test("mAcList clear start -----" + i);
            Activity perActivity = mAcList.get(i);
            if (null != perActivity) {
                perActivity.finish();
                mAcList.remove(perActivity);
            }
        }
        mAcList.clear();
        checkMgtStatus();
    }
}
