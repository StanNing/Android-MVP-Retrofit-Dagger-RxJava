package com.focuschina.ehealth_lib.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: BmpUtil
 * @Description: TODO: (图像处理工具类, 目前为单例，持有appContext)
 * @date 2016/12/16 上午11:35
 */
public class BmpUtil {

    private Context appContext;
    private Activity activity;
    private Fragment fragment;

    public BmpUtil(Context context) {
        this.appContext = context;
    }

    public BmpUtil(Activity activity) {
        this.activity = activity;
    }

    public BmpUtil(Fragment fragment) {
        this.fragment = fragment;
    }

    /**
     * 直接显示图片
     *
     * @param url     图片地址
     * @param imgView 容器
     * @param <T>     容器类型
     */
    public <T extends ImageView> void displayImg(String url, T imgView) {
        get()
                .load(url)
                .into(imgView);
    }

    /**
     * 显示gif图片
     *
     * @param gifResId gif图片资源id
     * @param imgView  容器
     * @param <T>      容器类型
     */
    public <T extends ImageView> void displayGif(@DrawableRes int gifResId, T imgView) {
        get()
                .load(gifResId)
                .into(imgView);
    }

    /**
     * 显示有加载中，加载失败的加载方法
     *
     * @param url          图片地址
     * @param imgView      容器
     * @param loadingImgId 加载中图片ID
     * @param errorImgId   加载失败图片ID
     * @param <T>          容器类型
     */
    public <T extends ImageView> void displayImg(String url, T imgView, int loadingImgId, int errorImgId) {
        get()
                .load(url)
                .placeholder(loadingImgId)
                .error(errorImgId)
                .into(imgView);
    }

    /**
     * 重设图片尺寸
     *
     * @param url     图片地址
     * @param imgView 容器
     * @param x       宽
     * @param y       高
     * @param <T>     容器类型
     */
    public <T extends ImageView> void resizeImg(String url, T imgView, int x, int y) {
        get()
                .load(url)
                .override(x, y)
                .into(imgView);
    }

    /**
     * 获取Glide 使用优先级 fragment > activity > application
     *
     * @return RequestManager
     */
    public RequestManager get() {
        if (null != fragment) {
            return Glide.with(fragment);
        } else if (null != activity) {
            return Glide.with(activity);
        }
        return Glide.with(appContext);
    }

    /**
     * 清空bitmap
     *
     * @param bitmap 图片
     */
    public void clearBitmapUsage(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            LogUtil.test("clearBitmapUsage");
            bitmap.recycle();
            System.gc();
        }
    }

    /**
     * 根据宽高比例转换对应要显示的尺寸高度
     *
     * @param w     宽度
     * @param h     高度
     * @param realW 屏幕实际宽度
     * @return 实际需要显示的高度
     */
    public int resizeH(int w, int h, int realW) {
        float rate = (float) w / (float) h;
        return (int) (realW / rate);
    }
}
