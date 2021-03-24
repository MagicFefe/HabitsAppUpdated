package com.swaptech.habitstwo

import com.swaptech.data.di.*
import com.swaptech.domain.modules.HabitModule
import com.swaptech.domain.usecases.*
import com.swaptech.habitstwo.actionwithhabit.AddFragment
import com.swaptech.habitstwo.actionwithhabit.EditFragment
import com.swaptech.habitstwo.listhabits.BadHabitsFragment
import com.swaptech.habitstwo.listhabits.GoodHabitsFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApiModule::class, DatabaseModule::class,
    NetworkModule::class, RepositoryModule::class, ContextModule::class])
@Singleton
interface ApplicationComponent {

    fun getAddHabitUseCase(): AddHabitUseCase
    fun getDeleteHabitUseCase(): DeleteHabitUseCase
    fun getGetHabitsFromLocalUseCase(): GetHabitsFromLocalUseCase
    fun getGetHabitsUseCase(): GetHabitsUseCase
    fun getUpdateHabitUseCase(): RefreshHabitUseCase
    fun getAddHabitToLocalUseCase(): AddHabitToLocalUseCase
    fun getDeleteAllFromLocalUseCase(): DeleteAllFromLocalUseCase
    /*
    fun inject(goodHabitsFragment: GoodHabitsFragment)
    fun inject(badHabitsFragment: BadHabitsFragment)
    fun inject(addFragment: AddFragment)
    fun inject(editFragment: EditFragment)

     */
    fun viewModelComponent(): ViewModelComponent
}