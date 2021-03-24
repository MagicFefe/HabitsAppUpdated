package com.swaptech.habitstwo

import android.widget.Button
import androidx.lifecycle.viewModelScope
import com.swaptech.habitstwo.listhabits.HabitsListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Button.getButtonColor(): String? {
    return this.tag?.toString()
}


fun HabitsListViewModel.loadGoodHabitsToLocal() {
    viewModelScope.launch(Dispatchers.IO) {
        goodHabits.value?.forEach {
            addHabitToLocalUseCase.addHabitToLocal(it)
        }
    }
}
fun HabitsListViewModel.loadBadHabitsToLocal() {
    viewModelScope.launch(Dispatchers.IO) {
        badHabits.value?.forEach {
            addHabitToLocalUseCase.addHabitToLocal(it)
        }
    }
}