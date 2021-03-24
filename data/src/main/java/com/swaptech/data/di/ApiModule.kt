package com.swaptech.data.di

import com.swaptech.data.server.Api
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {
    @Provides
    fun provideRetrofitApi(retrofitClient: Retrofit): Api {
        return retrofitClient.create(Api::class.java)
    }
}