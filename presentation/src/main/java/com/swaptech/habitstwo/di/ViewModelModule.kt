package com.swaptech.habitstwo.di

import com.swaptech.domain.usecases.*
import com.swaptech.habitstwo.actionwithhabit.ActionsWithHabitFragmentViewModel
import com.swaptech.habitstwo.listhabits.HabitsListViewModel
import dagger.Module
import dagger.Provides

@Module
open class ViewModelModule {
    @Provides
    fun provideActionsWithHabitFragmentViewModel(
            addHabitUseCase: AddHabitUseCase,
            deleteHabitUseCase: DeleteHabitUseCase,
            refreshHabitUseCase: RefreshHabitUseCase,
            addHabitToLocalUseCase: AddHabitToLocalUseCase,
            getHabitsFromLocalUseCase: GetHabitsFromLocalUseCase): ActionsWithHabitFragmentViewModel {

        return ActionsWithHabitFragmentViewModel(addHabitUseCase, deleteHabitUseCase,
                refreshHabitUseCase, addHabitToLocalUseCase, getHabitsFromLocalUseCase)
    }

    @Provides
    fun provideHabitsListViewModel(
            addHabitToLocalUseCase: AddHabitToLocalUseCase,
            getHabitsUseCase: GetHabitsUseCase,
            getHabitsFromLocalUseCase: GetHabitsFromLocalUseCase,
            deleteAllFromLocalUseCase: DeleteAllFromLocalUseCase,
            addHabitUseCase: AddHabitUseCase,
            setHabitIsCompletedOnServerUseCase: SetHabitIsCompletedOnServerUseCase): HabitsListViewModel {

        return HabitsListViewModel(addHabitToLocalUseCase, getHabitsUseCase,
                getHabitsFromLocalUseCase, deleteAllFromLocalUseCase, addHabitUseCase, setHabitIsCompletedOnServerUseCase)
    }
}