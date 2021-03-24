package com.swaptech.data.di

import com.swaptech.data.AUTH_TOKEN
import com.swaptech.data.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideRetrofitInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val builder = originalRequest.newBuilder().header(
                "Authorization", AUTH_TOKEN
            )
            val newRequest = builder.build()
            chain.proceed(newRequest)
        }
    }

    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(interceptor).build()
    }

    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build()
    }
}