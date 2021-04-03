package com.swaptech.habitstwo.listhabits

import android.content.Context
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.*
import com.swaptech.data.models.HabitForLocal
import com.swaptech.domain.models.HabitDone
import com.swaptech.domain.usecases.*
import com.swaptech.habitstwo.HabitsComparator
import com.swaptech.habitstwo.mapper.HabitsAndHabitsForLocalConverter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
@ExperimentalCoroutinesApi
class HabitsListViewModel(
        private val addHabitToLocalUseCase: AddHabitToLocalUseCase,
        private val getHabitsUseCase: GetHabitsUseCase,
        private val getHabitsFromLocalUseCase: GetHabitsFromLocalUseCase,
        private val deleteAllFromLocalUseCase: DeleteAllFromLocalUseCase,
        private val addHabitUseCase: AddHabitUseCase,
        private val setHabitIsCompletedOnServerUseCase: SetHabitIsCompletedOnServerUseCase) : ViewModel() {

    companion object {
        var searchFilter = MutableStateFlow("")
    }


    private val habitsFlow = searchFilter.flatMapLatest {
        getHabitsFromLocalUseCase.getHabitsFromLocal(it)
    }

    var habits = habitsFlow.asLiveData() as LiveData<MutableList<HabitForLocal>>
        private set

    var habitsFromDatabaseForSync = habits

    var position = 0


    fun getHabits() {
        viewModelScope.launch {
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

    private fun loadHabitsToLocal(habitsToLocal: MutableList<HabitForLocal>) {
        viewModelScope.launch {
            habitsToLocal.forEach { habit ->
                addHabitToLocalUseCase.addHabitToLocal(habit)
            }
        }
    }

    fun syncHabits() {
        viewModelScope.launch {
            val converter = HabitsAndHabitsForLocalConverter()
            habitsFromDatabaseForSync.value?.forEach { habit ->
                if (habit.uid == "" ) {
                    addHabitUseCase.addHabit(converter.serializeToHabit(habit))
                }
            }
            habits.value?.clear()
            getHabits()
            habitsFromDatabaseForSync.value?.clear()
        }
    }

    fun setHabitIsCompletedOnServer(habitDone: HabitDone) {
        viewModelScope.launch {
            setHabitIsCompletedOnServerUseCase.setHabitIsCompletedOnServer(habitDone)
        }
    }
}