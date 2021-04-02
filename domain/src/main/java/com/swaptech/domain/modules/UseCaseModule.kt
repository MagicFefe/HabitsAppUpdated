package com.swaptech.domain.modules


import com.swaptech.domain.Repository
import com.swaptech.domain.usecases.*
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule(private val repo: Repository) {
    @Provides
    fun provideAddHabitToLocalUseCase() = AddHabitToLocalUseCase(repo)
    @Provides
    fun provideAddHabitUseCase() = AddHabitUseCase(repo)
    @Provides
    fun provideDeleteAllLocalUseCase() = DeleteAllFromLocalUseCase(repo)
    @Provides
    fun provideDeleteHabitUseCase() = DeleteHabitUseCase(repo)
    @Provides
    fun provideGetHabitsFromLocalUseCase() = GetHabitsFromLocalUseCase(repo)
    @Provides
    fun provideGetHabitsUseCase() = GetHabitsUseCase(repo)
    @Provides
    fun provideRefreshHabitUseCase() = RefreshHabitUseCase(repo)
    @Provides
    fun provideSetHabitIsCompletedOnServerUseCase() = SetHabitIsCompletedOnServerUseCase(repo)
}