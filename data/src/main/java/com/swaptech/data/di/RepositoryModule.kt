package com.swaptech.data.di

import com.swaptech.data.database.HabitsFromServerDao
import com.swaptech.data.repository.RepositoryImpl
import com.swaptech.data.server.Api
import com.swaptech.domain.Repository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun provideRepository(api: Api, dao: HabitsFromServerDao): Repository {
        return RepositoryImpl(api, dao)
    }
}