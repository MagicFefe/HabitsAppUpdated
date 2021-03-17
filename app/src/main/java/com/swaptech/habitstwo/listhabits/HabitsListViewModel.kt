package com.swaptech.habitstwo.listhabits

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.swaptech.habitstwo.App
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.model.HabitForLocal
import com.swaptech.habitstwo.repository.HabitsAndHabitsForLocalConverter
import com.swaptech.habitstwo.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitsListViewModel constructor(application: Application) : AndroidViewModel(application) {

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


    var isLoaded = MutableLiveData(false)
        private set

    var badHabits: MutableLiveData<MutableList<HabitForLocal>> = MutableLiveData<MutableList<HabitForLocal>>()
        private set
    var goodHabits = MutableLiveData<MutableList<HabitForLocal>>()
        private set

    var position = 0

    val repo = Repository(application)
    private val habits = mutableListOf<HabitForLocal>()

    fun getHabits() {
        viewModelScope.launch(Dispatchers.IO) {

            val habitsFromLocal = repo.getHabitsFromLocal()
            val habitsFromServer = repo.getHabits()


            habitsFromLocal.value?.let { habits.addAll(it) }

            if (habitsFromServer.isSuccessful) {

                val converter = HabitsAndHabitsForLocalConverter()
                val responseFromServerWithoutCast = habitsFromServer.body()
                val responseFromServer = mutableListOf<HabitForLocal>()

                responseFromServerWithoutCast?.forEach {
                    responseFromServer.add(converter.deserializeToHabitForLocal(it))
                }

                habits.addAll(responseFromServer)

            } else {
                viewModelScope.launch(Dispatchers.Main) {
                    Toast.makeText(getApplication(), "ERROR - ${habitsFromServer.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            viewModelScope.launch(Dispatchers.Main) {
                sortToBadAndGoodHabits(habits)
                //Toast.makeText(getApplication(), "Successfully loaded!", Toast.LENGTH_SHORT).show()
            }

        }
    }
    fun deleteAllFromLocal() {
        repo.deleteAllFromLocal()
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