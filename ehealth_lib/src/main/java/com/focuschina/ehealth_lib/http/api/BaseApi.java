package com.focuschina.ehealth_lib.http.api;

import com.focuschina.ehealth_lib.model.HSPSService;
import com.focuschina.ehealth_lib.model.Response;
import com.focuschina.ehealth_lib.model.TBody;
import com.focuschina.ehealth_lib.model.common.ProductParam;
import com.focuschina.ehealth_lib.model.hosdata.Dep;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName: BaseApi 服务配置API
 * @date 16/5/12 下午3:20
 */
public class BaseApi {
    public static final String NULL_PARAM = "";

    public interface ServiceApi {
        //下载更新配置文件,服务地址获取
        @GET("/serverconfig/downloadMainifest")
        Observable<HSPSService> downloadMainifest(@Query("productId") String productId,
                                                  @Query("pltId") String pltId,
                                                  @Query("version") String version,
                                                  @Query("timestamp") String timestamp);

        String GET_PRODUCT_PARAM = "getProductParam"; //获取公共参数，在线配置（key & value）
        @POST Observable<Response<ProductParam<String>>> getProductParam(@Url String url, @Body ProductParam.QueryParam qp);

        String SEARCH_DEPTMENT_INFO_NEW = "searchDeptmentInfoNew";
        @POST Observable<Response<TBody<List<Dep>>>> searchDeptmentInfoNew(@Url String url, @Body Dep.QueryParam qp);
    }

    //2.4 用户中心
    public interface AccountApi {
        String USER_REGISTER = "userRegister"; //2.4.1 用户注册
//        @POST Observable<Response<UserRegister>> userRegister(@Url String url, @TBody UserRegister.QueryParam qp);
    }

}
