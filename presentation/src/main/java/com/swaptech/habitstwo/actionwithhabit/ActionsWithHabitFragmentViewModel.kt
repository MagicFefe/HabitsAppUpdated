package com.swaptech.habitstwo.actionwithhabit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swaptech.data.models.HabitForLocal
import com.swaptech.domain.models.Habit
import com.swaptech.domain.models.HabitUID
import com.swaptech.domain.usecases.AddHabitToLocalUseCase
import com.swaptech.domain.usecases.AddHabitUseCase
import com.swaptech.domain.usecases.DeleteHabitUseCase
import com.swaptech.domain.usecases.RefreshHabitUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class ActionsWithHabitFragmentViewModel constructor(
        private val addHabitUseCase: AddHabitUseCase,
        private val deleteHabitUseCase: DeleteHabitUseCase,
        private val refreshHabitUseCase: RefreshHabitUseCase,
        private val addHabitToLocalUseCase: AddHabitToLocalUseCase) : ViewModel() {

    var name = ""
    var description = ""
    var priority = ""
    var typeOfHabit = ""
    var periodicity = ""
    var countOfExecsOfHabit = 0
    var frequencyOfExecs = 0

    fun addToServer(habit: Habit) {
        //Blocking main thread until habit is added to server
        runBlocking {
            flow<Response<Any>> {
                addHabitUseCase.addHabit(habit)
            }.flowOn(Dispatchers.IO).collect()
        }
    }


    fun deleteFromServer(habitUID: HabitUID) {
        viewModelScope.launch {
            deleteHabitUseCase.deleteHabitUseCase(habitUID)
        }

    }

    fun refreshHabitForServer(habit: Habit) {
        viewModelScope.launch {
            refreshHabitUseCase.updateHabit(habit)
        }
    }

    fun addToLocal(habit: HabitForLocal) {
        viewModelScope.launch {
            addHabitToLocalUseCase.addHabitToLocal(habit)
        }
    }
}