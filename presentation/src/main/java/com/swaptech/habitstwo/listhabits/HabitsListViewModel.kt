package com.swaptech.habitstwo.listhabits

import androidx.lifecycle.*
import com.swaptech.data.models.HabitForLocal
import com.swaptech.domain.usecases.*
import com.swaptech.habitstwo.App
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.mapper.HabitsAndHabitsForLocalConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
/*
 class HabitsListViewModel constructor(
 private val addHabitToLocalUseCase: AddHabitToLocalUseCase,
 private val getHabitsUseCase: GetHabitsUseCase,
 private val getHabitsFromLocalUseCase: GetHabitsFromLocalUseCase,
 private val deleteAllFromLocalUseCase: DeleteAllFromLocalUseCase,
 private val addHabitUseCase: AddHabitUseCase) : ViewModel() {

     companion object {
         var searchFilter = ""
     }

     private val _badHabits = MutableLiveData<MutableList<HabitForLocal>>()
     val badHabits: LiveData<MutableList<HabitForLocal>> get() = _badHabits

     private val _goodHabits = MutableLiveData<MutableList<HabitForLocal>>()
     val goodHabits: LiveData<MutableList<HabitForLocal>> get() = _goodHabits

     var habitsFromDatabaseForSync = listOf<HabitForLocal>()
         private set
     private val habits = mutableListOf<HabitForLocal>()
     var position = 0

     private fun sortToBadAndGoodHabits(habits: List<HabitForLocal>) {
         val typeGood = App.res?.getString(R.string.good_radio_button)
         val typeBad = App.res?.getString(R.string.bad_radio_button)

         val goodHabitsForLiveData = habits.filter { it.type == typeGood }
         val badHabitsForLiveData = habits.filter { it.type == typeBad }

         _badHabits.value = badHabitsForLiveData as MutableList<HabitForLocal>
         _goodHabits.value = goodHabitsForLiveData as MutableList<HabitForLocal>
     }

     private fun deleteAllFromLocal() {
         deleteAllFromLocalUseCase.deleteAllFromLocal()
     }

     private fun loadHabitsToLocal() {
         viewModelScope.launch {
             goodHabits.value?.let {
                 it.forEach { habit ->
                     addHabitToLocalUseCase.addHabitToLocal(habit)
                 }
             }
             badHabits.value?.let {
                 it.forEach { habit ->
                     addHabitToLocalUseCase.addHabitToLocal(habit)
                 }
             }
         }
     }

     fun getHabits() {
         viewModelScope.launch(Dispatchers.IO) {

             val habitsFromServer = getHabitsUseCase.getHabits()

             if (habitsFromServer.isSuccessful) {

                 val converter = HabitsAndHabitsForLocalConverter()
                 val responseFromServerWithoutCast = habitsFromServer.body()
                 val responseFromServer = mutableListOf<HabitForLocal>()

                 responseFromServerWithoutCast?.forEach {
                     responseFromServer.add(converter.deserializeToHabitForLocal(it))
                 }

                 habits.clear()
                 habits.addAll(responseFromServer)

                 this.launch(Dispatchers.Main) {
                     sortToBadAndGoodHabits(habits)
                     deleteAllFromLocal()
                     loadHabitsToLocal()
                 }
             }
         }
     }

     fun getHabitsFromLocal(filter: String = "") {
         viewModelScope.launch {

             getHabitsFromLocalUseCase
                     .getHabitsFromLocal(filter)
                     .flowOn(Dispatchers.IO)
                     .collect { habitsFromLocal ->
                         sortToBadAndGoodHabits(habitsFromLocal as List<HabitForLocal>)
                         habitsFromDatabaseForSync = habitsFromLocal
                     }
         }
     }

     fun syncHabits() {
         viewModelScope.launch {
             val converter = HabitsAndHabitsForLocalConverter()
             habitsFromDatabaseForSync.forEach { habit ->
                 if (habit.uid.isEmpty()) {
                     addHabitUseCase.addHabit(converter.serializeToHabit(habit))
                 }
             }
             this.launch(Dispatchers.Main) {
                 habitsFromDatabaseForSync = listOf()
             }
         }

     }
 }

 */


class HabitsListViewModel(
        private val addHabitToLocalUseCase: AddHabitToLocalUseCase,
        private val getHabitsUseCase: GetHabitsUseCase,
        private val getHabitsFromLocalUseCase: GetHabitsFromLocalUseCase,
        private val deleteAllFromLocalUseCase: DeleteAllFromLocalUseCase,
        private val addHabitUseCase: AddHabitUseCase) : ViewModel() {

    companion object {
        var searchFilter: String = ""
    }

    private val _badHabits = MutableLiveData<MutableList<HabitForLocal>>()
    val badHabits: LiveData<MutableList<HabitForLocal>> get() = _badHabits

    private val _goodHabits = MutableLiveData<MutableList<HabitForLocal>>()
    val goodHabits: LiveData<MutableList<HabitForLocal>> get() = _goodHabits

    var habitsFromDatabaseForSync = listOf<HabitForLocal>()
        private set

    var position = 0


    private fun sortToBadAndGoodHabits(habits: List<HabitForLocal>) {

        val typeGood = App.res?.getString(R.string.good_radio_button)
        val typeBad = App.res?.getString(R.string.bad_radio_button)

        val goodHabitsForLiveData = habits.filter { it.type == typeGood }
        val badHabitsForLiveData = habits.filter { it.type == typeBad }

        _badHabits.value = badHabitsForLiveData as MutableList<HabitForLocal>
        _goodHabits.value = goodHabitsForLiveData as MutableList<HabitForLocal>
    }


    fun getHabits() {
        viewModelScope.launch(Dispatchers.IO) {
            val habits = mutableListOf<HabitForLocal>()
            val habitsFromServer = getHabitsUseCase.getHabits()

            if (habitsFromServer.isSuccessful) {

                val converter = HabitsAndHabitsForLocalConverter()
                val responseFromServerWithoutCast = habitsFromServer.body()
                val responseFromServer = mutableListOf<HabitForLocal>()

                responseFromServerWithoutCast?.forEach {
                    responseFromServer.add(converter.deserializeToHabitForLocal(it))
                }
                habits.clear()
                habits.addAll(responseFromServer)

            }
            this.launch(Dispatchers.Main) {
                sortToBadAndGoodHabits(habits)
                deleteAllFromLocal()
                loadHabitsToLocal()
            }
        }
    }

    private fun deleteAllFromLocal() {
        deleteAllFromLocalUseCase.deleteAllFromLocal()
    }

    private fun loadHabitsToLocal() {
        viewModelScope.launch {
            goodHabits.value?.let {
                it.forEach { habit ->
                    addHabitToLocalUseCase.addHabitToLocal(habit)
                }
            }
            badHabits.value?.let {
                it.forEach { habit ->
                    addHabitToLocalUseCase.addHabitToLocal(habit)
                }
            }
        }
    }

    //SEA OF BUGS!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    fun getHabitsFromLocal(filter: String = "") {
        viewModelScope.launch {
            getHabitsFromLocalUseCase
                    .getHabitsFromLocal(filter)
                    .flowOn(Dispatchers.IO)
                    .collect { habitsFromLocal ->
                        sortToBadAndGoodHabits(habitsFromLocal as List<HabitForLocal>)
                        habitsFromDatabaseForSync = habitsFromLocal
                    }
        }
    }

    fun syncHabits() {
        viewModelScope.launch {
            val converter = HabitsAndHabitsForLocalConverter()
            habitsFromDatabaseForSync.forEach { habit ->
                if (habit.uid.isEmpty()) {
                    addHabitUseCase.addHabit(converter.serializeToHabit(habit))
                }
            }
            this.launch(Dispatchers.Main) {
                habitsFromDatabaseForSync = listOf()
            }
        }

    }

    /*
    init {
        //load()
    }
     */
    /*
    fun load() {
        repo.readFromRepository()
        val highPriority = App.res?.getString(R.string.high_priority)?.toLowerCase()
        val mediumPriority = App.res?.getString(R.string.medium_priority)?.toLowerCase()
        val lowPriority = App.res?.getString(R.string.low_priority)?.toLowerCase()
        when(searchFilter) {
             highPriority -> {
                _badHabits = HabitModelRepository.badHabits.filter { it.priority?.toLowerCase() == highPriority } as MutableList<RecItem>
                _goodHabits = HabitModelRepository.goodHabits.filter { it.priority?.toLowerCase() == highPriority } as MutableList<RecItem>
            }
            mediumPriority -> {
                _badHabits = HabitModelRepository.badHabits.filter { it.priority?.toLowerCase() == mediumPriority } as MutableList<RecItem>
                _goodHabits = HabitModelRepository.goodHabits.filter { it.priority?.toLowerCase() == mediumPriority } as MutableList<RecItem>
            }
            lowPriority -> {
                _badHabits = HabitModelRepository.badHabits.filter { it.priority?.toLowerCase() == lowPriority } as MutableList<RecItem>
                _goodHabits = HabitModelRepository.goodHabits.filter { it.priority?.toLowerCase() == lowPriority } as MutableList<RecItem>
            }
            null -> {
                _badHabits = HabitModelRepository.badHabits
                _goodHabits = HabitModelRepository.goodHabits
            }
        }
    }
     */
}