package com.swaptech.habitstwo.listhabits

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.swaptech.habitstwo.App
import com.swaptech.habitstwo.repository.HabitModelRepository
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.model.RecItem

class HabitsListViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        var searchFilter: String? = null
    }
    var _badHabits = mutableListOf<RecItem>()
    var _goodHabits = mutableListOf<RecItem>()

    var position = 0
    val repo = HabitModelRepository(application)
    var isLoaded: MutableLiveData<Boolean> = MutableLiveData()

    init {
        load()
    }

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

}