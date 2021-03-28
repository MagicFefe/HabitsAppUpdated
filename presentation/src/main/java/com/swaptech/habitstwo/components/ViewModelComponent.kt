package com.swaptech.habitstwo.components

import com.swaptech.habitstwo.di.ViewModelModule
import com.swaptech.habitstwo.actionwithhabit.AddFragment
import com.swaptech.habitstwo.actionwithhabit.EditFragment
import com.swaptech.habitstwo.listhabits.ListOfHabitsFragment
import com.swaptech.habitstwo.listhabits.HabitsListContainerFragment
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
interface ViewModelComponent {
    fun inject(habitsListContainerFragment: HabitsListContainerFragment)
    fun inject(listOfHabitsFragment: ListOfHabitsFragment)
    fun inject(addFragment: AddFragment)
    fun inject(editFragment: EditFragment)
}