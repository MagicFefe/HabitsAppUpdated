package com.swaptech.habitstwo.server

import okhttp3.OkHttpClient

object OkHttpClientForRetrofit {
    val okHttpClient by lazy {
        OkHttpClient().newBuilder().addInterceptor(RetrofitInterceptor.interceptor).build()
    }
}