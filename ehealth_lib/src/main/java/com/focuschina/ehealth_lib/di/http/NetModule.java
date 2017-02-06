package com.focuschina.ehealth_lib.di.http;

import com.focuschina.ehealth_lib.config.AppConfig;
import com.focuschina.ehealth_lib.util.AppUtil;
import com.focuschina.ehealth_lib.util.CodeUtil;
import com.focuschina.ehealth_lib.util.LogUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: NetModule
 * @Description: TODO: (网络模块 保存网络配置实例)
 * @date 2016/11/18 下午4:46
 */
@Module
public class NetModule {
    private static final String SERVER_CONFIG_URL = "http://config.jiankang51.cn/"; //正式环境
    private static final String SERVER_CONFIG_URL_P = "http://pconfig.jiankang51.cn/"; //P版环境

    public static long defaultTimeout = 60; //超时时间
    public static TimeUnit defaultUnit = TimeUnit.SECONDS; //单位秒

    /**
     * 特殊处理请求响应  加密+解密
     */
    private static final Interceptor REWRITE_NET_INTERCEPTOR = chain -> {
        Request originalRequest = chain.request(); //原始请求

        String[] baseParam = getBaseParam(originalRequest.url().toString());
        String orgReq = bodyToString(originalRequest.body()); //原始入参
        LogUtil.i(LogUtil.TAG_URL, "REQ:" + baseParam[1] + orgReq);
        String encodeReqParam = CodeUtil.encode(baseParam[1] + orgReq); //入参加密

        RequestBody encodeRequestBody = RequestBody.create(originalRequest.body().contentType(),
                encodeReqParam != null ? encodeReqParam : ""); //1

        Request encodeRequest = originalRequest
                .newBuilder()
                .url(baseParam[0]) //再次重定向地址
                .method(originalRequest.method(), encodeRequestBody)
                .build();

        Response originalResponse = chain.proceed(encodeRequest);

        String orgRsp = originalResponse.body().string(); //原始返回
        String decodedRsp = CodeUtil.decode(orgRsp); //解密返回数据
        LogUtil.i(LogUtil.TAG_URL, "RSP:" + decodedRsp);

        return originalResponse.newBuilder()
                .body(ResponseBody.create(originalResponse.body().contentType(), decodedRsp != null ? decodedRsp : ""))
                .build();
    };

    /**
     * 对于传入的地址做改造，拆分成2部分，基础参数部分放到后面的业务参数中一起加密处理
     *
     * @param url 接口地址 + 公共参数
     * @return 返回 String[] - [0]:地址； [1]:公共参数
     */
    private static String[] getBaseParam(String url) {
        String[] baseParam = new String[]{"", ""};
        if (!AppUtil.isEmpty(url)) {
            try {
                String[] split = url.split("\\?");
                if (split.length == 2) baseParam = split;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return baseParam;
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final Buffer buffer = new Buffer();
            if (request != null)
                request.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    /**
     * 日志输出，不对外提供，不在appComponent里面提供，内部提供
     *
     * @return HttpLoggingInterceptor 日志输出
     */
    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return httpLoggingInterceptor;
    }

    /**
     * 不加密参数
     *
     * @param httpLoggingInterceptor 日志输出
     * @return OkHttpClient
     */
    @Singleton
    @Provides
    OkHttpClient provideOk(HttpLoggingInterceptor httpLoggingInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(defaultTimeout, defaultUnit); //设置默认超时时间
        if (!AppConfig.BUILD) {
            builder.addInterceptor(httpLoggingInterceptor); //日志输出拦截器
        }
        return builder.build();
    }

    /**
     * 加密 输出带参数加密功能的OK客户端
     *
     * @param httpLoggingInterceptor 日志输出
     * @return OkHttpClient
     */
    @Singleton
    @Provides
    @Named("interceptor")
    OkHttpClient provideInterceptorOk(HttpLoggingInterceptor httpLoggingInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(defaultTimeout, defaultUnit) //设置默认超时时间
                .addInterceptor(REWRITE_NET_INTERCEPTOR); //添加拦截器,进行加解密
        if (!AppConfig.BUILD) {
            builder.addInterceptor(httpLoggingInterceptor); //日志输出拦截器
        }
        return builder.build();
    }

    /**
     * 返回不带加密解析的
     *
     * @param okHttpClient 客户端
     * @return Retrofit 非加密
     */
    @Singleton
    @Provides
    @UnEncrypted
    //非加密
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        String severUrl = AppConfig.BUILD ? SERVER_CONFIG_URL : SERVER_CONFIG_URL_P;
        return new Retrofit.Builder()
                .baseUrl(severUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * 返回带加密解析的
     *
     * @param okHttpClient 客户端
     * @return Retrofit 默认加密
     */
    @Singleton
    @Provides
    Retrofit provideEncodeRetrofit(@Named("interceptor") OkHttpClient okHttpClient) {
        String severUrl = AppConfig.BUILD ? SERVER_CONFIG_URL : SERVER_CONFIG_URL_P;
        return new Retrofit.Builder()
                .baseUrl(severUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

}
