package com.swaptech.habitstwo.listhabits

import android.util.Log
import androidx.lifecycle.*
import com.swaptech.data.models.HabitForLocal
import com.swaptech.domain.usecases.AddHabitToLocalUseCase
import com.swaptech.domain.usecases.DeleteAllFromLocalUseCase
import com.swaptech.domain.usecases.GetHabitsFromLocalUseCase
import com.swaptech.domain.usecases.GetHabitsUseCase
import com.swaptech.habitstwo.App
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.mapper.HabitsAndHabitsForLocalConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


class HabitsListViewModel constructor(
        val addHabitToLocalUseCase: AddHabitToLocalUseCase,
        private val getHabitsUseCase: GetHabitsUseCase,
        private val getHabitsFromLocalUseCase: GetHabitsFromLocalUseCase,
        private val deleteAllFromLocalUseCase: DeleteAllFromLocalUseCase) : ViewModel() {

    companion object {
        var searchFilter: String? = null
    }
    private fun sortToBadAndGoodHabits(habits: List<HabitForLocal>) {
        val goodHabitsForLiveData = mutableListOf<HabitForLocal>()
        val badHabitsForLiveData = mutableListOf<HabitForLocal>()
        habits.forEach {
            if(it.type == App.res?.getString(R.string.good_radio_button)) {
                goodHabitsForLiveData.add(it)
            } else if(it.type == App.res?.getString(R.string.bad_radio_button)) {
                badHabitsForLiveData.add(it)
            }
        }

        goodHabits.value = goodHabitsForLiveData
        badHabits.value = badHabitsForLiveData
    }

    var badHabits: MutableLiveData<MutableList<HabitForLocal>> = MutableLiveData<MutableList<HabitForLocal>>()
        private set
    var goodHabits = MutableLiveData<MutableList<HabitForLocal>>()
        private set

    var position = 0


    private val habits = mutableListOf<HabitForLocal>()

    fun getHabits() {
        viewModelScope.launch(Dispatchers.IO) {

            val habitsFromLocal = getHabitsFromLocalUseCase.getHabitsFromLocal()
            val habitsFromServer =  getHabitsUseCase.getHabits()

            try {
                val ok = habitsFromLocal as? LiveData<List<HabitForLocal>>
                //ok?.value?.let { habits.addAll(it) }
            } catch (e: Exception) {
                Log.d("ERROR", "${e.message}")
            }

            if (habitsFromServer.isSuccessful) {

                val converter = HabitsAndHabitsForLocalConverter()
                val responseFromServerWithoutCast = habitsFromServer.body()
                val responseFromServer = mutableListOf<HabitForLocal>()

                responseFromServerWithoutCast?.forEach {
                    responseFromServer.add(converter.deserializeToHabitForLocal(it))
                }
                habits.clear()
                habits.addAll(responseFromServer)

            } else {
                viewModelScope.launch(Dispatchers.Main) {
                    //Toast.makeText(getApplication(), "ERROR - ${habitsFromServer.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            viewModelScope.launch(Dispatchers.Main) {
                sortToBadAndGoodHabits(habits)
                //Toast.makeText(getApplication(), "Successfully loaded!", Toast.LENGTH_SHORT).show()
            }

        }
    }
    fun deleteAllFromLocal() {
        deleteAllFromLocalUseCase.deleteAllFromLocal()
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