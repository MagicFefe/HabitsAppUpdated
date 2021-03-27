package com.swaptech.habitstwo.components

import com.swaptech.data.di.*
import com.swaptech.domain.usecases.*
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApiModule::class, DatabaseModule::class,
    NetworkModule::class, RepositoryModule::class, ContextModule::class])
@Singleton
interface ApplicationComponent {
    fun viewModelComponent(): ViewModelComponent
}