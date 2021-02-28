package com.swaptech.habitstwo.server

import com.swaptech.habitstwo.AUTH_TOKEN

import okhttp3.Interceptor


object RetrofitInterceptor {
    val interceptor by lazy {
        Interceptor { chain ->
            val originalRequest = chain.request()
            val builder = originalRequest.newBuilder().header(
                "Authorization", AUTH_TOKEN
            )
            val newRequest = builder.build()
            chain.proceed(newRequest)
        }
    }
}