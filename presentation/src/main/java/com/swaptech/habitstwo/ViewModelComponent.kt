package com.swaptech.habitstwo

import com.swaptech.habitstwo.actionwithhabit.AddFragment
import com.swaptech.habitstwo.actionwithhabit.EditFragment
import com.swaptech.habitstwo.listhabits.BadHabitsFragment
import com.swaptech.habitstwo.listhabits.GoodHabitsFragment
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
interface ViewModelComponent {
    fun inject(goodHabitsFragment: GoodHabitsFragment)
    fun inject(badHabitsFragment: BadHabitsFragment)
    fun inject(addFragment: AddFragment)
    fun inject(editFragment: EditFragment)
}