package com.swaptech.data.di

import com.swaptech.data.database.HabitsFromServerDao
import com.swaptech.data.models.HabitForLocal
import com.swaptech.data.repository.RepositoryImpl
import com.swaptech.data.server.Api
import com.swaptech.domain.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(api: Api, dao: HabitsFromServerDao): Repository {
        return RepositoryImpl(api, dao)
    }
}