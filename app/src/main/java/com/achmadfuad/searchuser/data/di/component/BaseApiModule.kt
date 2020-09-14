package com.achmadfuad.searchuser.data.di.component

import com.achmadfuad.searchuser.data.constant.KoinPropertyConstants.API_PROPERTY
import com.achmadfuad.searchuser.framework.di.module.ApiModule
import com.achmadfuad.searchuser.framework.webapi.BaseNetworkInterceptor
import com.achmadfuad.searchuser.framework.webapi.ServiceFactory
import com.achmadfuad.searchuser.framework.webapi.WebApi
import com.achmadfuad.searchuser.framework.webapi.impl.RetrofitServiceFactory
import okhttp3.Interceptor
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BaseApiModule(api: WebApi) : ApiModule(api)

val applicationApiModule = module {
    single {
        BaseApiModule(getProperty(API_PROPERTY))
    }
    single<Interceptor> {
        BaseNetworkInterceptor(
            api = get<BaseApiModule>().api
        )
    }
    single<CallAdapter.Factory> { RxJava2CallAdapterFactory.create() }
    single<Converter.Factory> { GsonConverterFactory.create() }
    single<ServiceFactory> {
        RetrofitServiceFactory(
                api = get<BaseApiModule>().api,
                interceptor = get(),
                adapterFactory = get(),
                converterFactory = get()
        )
    }
}