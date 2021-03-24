package com.swaptech.domain.modules

import com.swaptech.domain.Repository
import com.swaptech.domain.usecases.*
import dagger.Module
import dagger.Provides

@Module
class HabitModule {
    @Provides
    fun provideAddHabitUseCase(repo: Repository) = AddHabitUseCase(repo)

    @Provides
    fun provideDeleteHabitUseCase(repo: Repository) = DeleteHabitUseCase(repo)

    @Provides
    fun provideGetHabitsFromLocalUseCase(repo: Repository) = GetHabitsFromLocalUseCase(repo)

    @Provides
    fun provideGetHabitsUseCase(repo: Repository) = GetHabitsUseCase(repo)

    @Provides
    fun provideUpdateHabitUseCase(repo: Repository) = RefreshHabitUseCase(repo)
}