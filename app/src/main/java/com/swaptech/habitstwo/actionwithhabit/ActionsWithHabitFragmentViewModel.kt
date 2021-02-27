package com.swaptech.habitstwo.actionwithhabit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.swaptech.habitstwo.repository.HabitModelRepository
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.model.RecItem

class ActionsWithHabitFragmentViewModel(application: Application) : AndroidViewModel(application) {

    var name = ""
    var description = ""
    var priority = ""
    var typeOfHabit = ""
    var periodicity = ""
    var habit: RecItem = RecItem("", "", "", "", "", 0, 0, R.color.dark_grey, 0)
    var countOfExecsOfHabit = 0
    var frequencyOfExecs = 0
    var _goodHabits = mutableListOf<RecItem>()
    var _badHabits = mutableListOf<RecItem>()
    private val repo = HabitModelRepository(application)

    init {
        load()
    }

    private fun load() {
        repo.readFromRepository()
    }

    fun add() {
        val habits = mutableListOf<RecItem>()
        habits.addAll(_goodHabits)
        habits.addAll(_badHabits)
        repo.loadToRepository(habits)

    }

    fun delete(item: RecItem) {
        repo.deleteFromRepository(item)
    }

    fun refreshHabit(item: RecItem) {
        repo.updateToRepository(item)
    }

}