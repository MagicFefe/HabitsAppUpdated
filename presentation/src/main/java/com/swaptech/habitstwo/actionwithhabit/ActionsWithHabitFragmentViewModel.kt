package com.swaptech.habitstwo.actionwithhabit

import android.icu.text.CaseMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swaptech.data.models.HabitForLocal
import com.swaptech.domain.DatabaseHabit
import com.swaptech.domain.models.Habit
import com.swaptech.domain.models.HabitUID
import com.swaptech.domain.usecases.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class ActionsWithHabitFragmentViewModel constructor(
        private val addHabitUseCase: AddHabitUseCase,
        private val deleteHabitUseCase: DeleteHabitUseCase,
        private val refreshHabitUseCase: RefreshHabitUseCase,
        private val addHabitToLocalUseCase: AddHabitToLocalUseCase,
        private val getHabitsFromLocalUseCase: GetHabitsFromLocalUseCase) : ViewModel() {

    var name = ""
    var description = ""
    var priority = ""
    var typeOfHabit = ""
    var periodicity = ""
    var countOfExecsOfHabit = 0
    var frequencyOfExecs = 0

    private val _habits = MutableLiveData<List<HabitForLocal>>()
    val habits: LiveData<List<HabitForLocal>> = _habits

    var nonUniqueHabit = HabitForLocal(color = 0, count = 0, date = 0, description = "",
            doneDates = 0, frequency = 0, priority = "", title = "", type = "", uid = "")
    private set
    fun addToServer(habit: Habit) {
        //Blocking main thread until habit is added to server
        runBlocking {
            flow<Response<Any>> {
                addHabitUseCase.addHabit(habit)
            }.flowOn(Dispatchers.IO).collect()
        }
    }

    init {
        getHabitsFromLocal()
    }

    fun checkUniquenessOfTitle(title: String): Boolean {
        _habits.value?.forEach { habit ->
            if(habit.title == title) {
                nonUniqueHabit = habit
                return false
            }
        }
        return true
    }
    fun deleteFromServer(habitUID: HabitUID) {
        viewModelScope.launch {
            deleteHabitUseCase.deleteHabitUseCase(habitUID)
        }

    }
    private fun getHabitsFromLocal() {
        viewModelScope.launch {
            getHabitsFromLocalUseCase.getHabitsFromLocal("")
                    .flowOn(Dispatchers.IO)
                    .collect {
                 _habits.value = it as List<HabitForLocal>
            }
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