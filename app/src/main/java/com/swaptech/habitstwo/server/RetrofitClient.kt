package com.swaptech.habitstwo.server

import com.swaptech.habitstwo.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val retrofitClient: Retrofit by lazy {
        Retrofit.Builder().client(OkHttpClientForRetrofit.okHttpClient).addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build()
    }
}