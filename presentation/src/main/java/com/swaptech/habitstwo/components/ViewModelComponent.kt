package com.swaptech.habitstwo.components

import com.swaptech.habitstwo.di.ViewModelModule
import com.swaptech.habitstwo.actionwithhabit.AddFragment
import com.swaptech.habitstwo.actionwithhabit.EditFragment
import com.swaptech.habitstwo.listhabits.BadHabitsFragment
import com.swaptech.habitstwo.listhabits.GoodHabitsFragment
import com.swaptech.habitstwo.listhabits.HabitsListContainerFragment
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
interface ViewModelComponent {
    fun inject(habitsListContainerFragment: HabitsListContainerFragment)
    fun inject(goodHabitsFragment: GoodHabitsFragment)
    fun inject(badHabitsFragment: BadHabitsFragment)
    fun inject(addFragment: AddFragment)
    fun inject(editFragment: EditFragment)
}