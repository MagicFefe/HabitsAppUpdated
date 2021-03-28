package com.swaptech.habitstwo.listhabits

import android.content.Context
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.*
import com.swaptech.data.models.HabitForLocal
import com.swaptech.domain.usecases.*
import com.swaptech.habitstwo.HabitsComparator
import com.swaptech.habitstwo.mapper.HabitsAndHabitsForLocalConverter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class HabitsListViewModel(
        private val addHabitToLocalUseCase: AddHabitToLocalUseCase,
        private val getHabitsUseCase: GetHabitsUseCase,
        private val getHabitsFromLocalUseCase: GetHabitsFromLocalUseCase,
        private val deleteAllFromLocalUseCase: DeleteAllFromLocalUseCase,
        private val addHabitUseCase: AddHabitUseCase) : ViewModel() {

    companion object {
        var searchFilter = MutableStateFlow("")
    }

    @ExperimentalCoroutinesApi
    private val habitsFlow = searchFilter.flatMapLatest {
        getHabitsFromLocalUseCase.getHabitsFromLocal(it)
    }
    @ExperimentalCoroutinesApi
    var habits = habitsFlow.asLiveData() as LiveData<MutableList<HabitForLocal>>
        private set


    @ExperimentalCoroutinesApi
    var habitsFromDatabaseForSync = habits

    var position = 0


    @ExperimentalCoroutinesApi
    fun getHabits() {
        viewModelScope.launch(Dispatchers.IO) {
            val habitsFromServerList = mutableListOf<HabitForLocal>()
            val habitsFromServer = getHabitsUseCase.getHabits()

            if (habitsFromServer.isSuccessful) {

                val converter = HabitsAndHabitsForLocalConverter()
                val responseFromServerWithoutCast = habitsFromServer.body()
                val responseFromServer = mutableListOf<HabitForLocal>()

                responseFromServerWithoutCast?.forEach {
                    responseFromServer.add(converter.deserializeToHabitForLocal(it))
                }
                habitsFromServerList.clear()
                habitsFromServerList.addAll(responseFromServer)

                this.launch(Dispatchers.Main) {

                    deleteAllFromLocal()
                    loadHabitsToLocal(habitsFromServerList)
                }
            }

        }
    }

    private fun deleteAllFromLocal() {
        deleteAllFromLocalUseCase.deleteAllFromLocal()
    }

    @ExperimentalCoroutinesApi
    private fun loadHabitsToLocal(habitsToLocal: MutableList<HabitForLocal>) {
        viewModelScope.launch {
            habitsToLocal.forEach { habit ->
                addHabitToLocalUseCase.addHabitToLocal(habit)
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun syncHabits() {
        viewModelScope.launch(Dispatchers.IO) {
            val converter = HabitsAndHabitsForLocalConverter()
            habitsFromDatabaseForSync.value?.forEach { habit ->
                if (habit.uid == "") {
                    addHabitUseCase.addHabit(converter.serializeToHabit(habit))
                }
            }
            getHabits()
        }
    }
}