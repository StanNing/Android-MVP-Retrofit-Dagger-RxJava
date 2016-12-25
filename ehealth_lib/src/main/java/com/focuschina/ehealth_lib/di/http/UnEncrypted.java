package com.focuschina.ehealth_lib.di.http;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Copyright (C) Focus Technology
 *
 * @author Ning jiajun
 * @ClassName:
 * @Description: TODO: (这里用一句话描述这个类的作用)
 * @date 2016/12/21 下午2:39
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface UnEncrypted {

}
