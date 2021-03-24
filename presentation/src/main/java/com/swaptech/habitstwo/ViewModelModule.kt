package com.swaptech.habitstwo

import com.swaptech.domain.usecases.*
import com.swaptech.habitstwo.actionwithhabit.ActionsWithHabitFragmentViewModel
import com.swaptech.habitstwo.listhabits.HabitsListViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {
    @Provides
    fun provideActionsWithHabitFragmentViewModel(
            addHabitUseCase: AddHabitUseCase,
            deleteHabitUseCase: DeleteHabitUseCase,
            refreshHabitUseCase: RefreshHabitUseCase,
            addHabitToLocalUseCase: AddHabitToLocalUseCase): ActionsWithHabitFragmentViewModel {
        return ActionsWithHabitFragmentViewModel(addHabitUseCase, deleteHabitUseCase, refreshHabitUseCase, addHabitToLocalUseCase)
    }

    @Provides
    fun provideHabitsListViewModel(addHabitToLocalUseCase: AddHabitToLocalUseCase,
                                   getHabitsUseCase: GetHabitsUseCase,
                                   getHabitsFromLocalUseCase: GetHabitsFromLocalUseCase,
                                   deleteAllFromLocalUseCase: DeleteAllFromLocalUseCase): HabitsListViewModel {
        return HabitsListViewModel(addHabitToLocalUseCase, getHabitsUseCase, getHabitsFromLocalUseCase, deleteAllFromLocalUseCase)
    }
}