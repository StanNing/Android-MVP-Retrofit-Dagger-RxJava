package com.focuschina.ehealth_lib.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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

    public BmpUtil(Context context) {
        this.appContext = context;
    }

    /**
     * 直接显示图片
     *
     * @param url     图片地址
     * @param imgView 容器
     * @param <T>     容器类型
     */
    public <T extends ImageView> void displayImg(String url, T imgView) {
        //.fit() 如果调用了该API, Picasso会对图片的大小及ImageView进行测量,
        // 计算出最佳的大小及最佳的图片质量来进行图片展示,减少内存,并对视图没有影响;
        Picasso.with(appContext)
                .load(url)
                .noPlaceholder()
                .fit() //智能展示图片
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
        Picasso.with(appContext)
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
        Picasso.with(appContext)
                .load(url)
                .noPlaceholder()
                .resize(x, y)
                .onlyScaleDown()
                .into(imgView);
    }

    /**
     * 获取Picasso
     *
     * @return Picasso
     */
    public Picasso get() {
        return Picasso.with(appContext);
    }

    /**
     * 目前对lv中快速滑动统一设置tag标记，用于处理重复加载图片，使用的tag为当前act的context
     * 此方法是对快速滑动过程中设置的tag进行回收，防止内存泄露
     *
     * @param context Activity context
     */
    public void cancelTag(Context context) {
        get().cancelTag(context);
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
