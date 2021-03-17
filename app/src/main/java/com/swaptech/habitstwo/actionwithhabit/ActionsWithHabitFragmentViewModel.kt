package com.swaptech.habitstwo.actionwithhabit

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.swaptech.habitstwo.model.HabitForLocal
import com.swaptech.habitstwo.repository.Repository
import com.swaptech.habitstwo.server.models.Habit
import com.swaptech.habitstwo.server.models.HabitUID
import kotlinx.coroutines.*

class ActionsWithHabitFragmentViewModel(application: Application) : AndroidViewModel(application) {

    var name = ""
    var description = ""
    var priority = ""
    var typeOfHabit = ""
    var periodicity = ""
    //var habit: RecItem = RecItem("", "", "", "", "", 0, 0, R.color.dark_grey, 0)
    var countOfExecsOfHabit = 0
    var frequencyOfExecs = 0
    //var habitForLocal = HabitForLocal(0, 0, 0, "", 1, 0, "", "", "")
    //var _goodHabits = mutableListOf<RecItem>()
    //var _badHabits = mutableListOf<RecItem>()
    //var goodHabits = mutableListOf<HabitForLocal>()
    //var badHabits = mutableListOf<HabitForLocal>()
    //private val repo = HabitModelRepository(application)
    val repo = Repository(application)

    fun addToLocal(habitForLocal: HabitForLocal) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addHabitsToLocal(habitForLocal)
        }
    }
    fun addToServer(habit: Habit) {
        viewModelScope.launch {
            repo.addHabit(habit)
        }
    }
    fun deleteFromLocal(habitForLocal: HabitForLocal) {
        viewModelScope.launch {
            repo.deleteHabitFromLocal(habitForLocal)
        }
    }
    fun deleteFromServer(habitUID: HabitUID) {
        viewModelScope.launch {
            repo.deleteHabit(habitUID)
        }
    }
    fun refreshHabitForLocal(habitForLocal: HabitForLocal) {
        viewModelScope.launch {
            repo.updateHabitToLocal(habitForLocal)
        }
    }
    fun refreshHabitForServer(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateHabit(habit)
        }
    }
    var isAdded = MutableLiveData(false)



    suspend fun getUIDForHabit(habit: Habit): Habit? {
        var result: Habit? = null
        var habitForLocalDB: Habit? = null
        val job = viewModelScope.async {

            val response = repo.getHabits()

            if (response.isSuccessful) {
                val habits = response.body()
                if (habits != null) {
                    val habitFromServer = if(habits.isNotEmpty()) habits.get(habits.lastIndex) else
                        Habit(description = "NOT FOUND",  frequency = -1, priority = 0,
                                title = "NOT FOUND", type = 1)
                    habitFromServer.let {
                        habitForLocalDB = habit
                        habitForLocalDB = habitForLocalDB.apply {
                            this?.uid = it.uid
                        }
                        if (habitForLocalDB == it) {
                            isAdded.postValue(true)
                            result = habitForLocalDB
                            return@async result
                        }
                    }
                } else {
                    this.launch(Dispatchers.Main) {
                        Toast.makeText(getApplication(), "NULL", Toast.LENGTH_LONG).show()
                    }
                }

            } else {
                this.launch(Dispatchers.Main) {
                    Toast.makeText(getApplication(), "${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

        }

        job.await()

        return result

    }
}