package com.swaptech.habitstwo.repository

import android.content.Context
import androidx.room.Room
import com.swaptech.habitstwo.database.HabitEntity
import com.swaptech.habits.database.HabitsDatabase
import com.swaptech.habitstwo.App
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.model.RecItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HabitModelRepository(context: Context) {

    companion object {
        var goodHabits = mutableListOf<RecItem>()
        var badHabits = mutableListOf<RecItem>()
    }

    private val db = Room.databaseBuilder(context, HabitsDatabase::class.java, "habits").allowMainThreadQueries().build()
    private val dao = db.habitsDao()
    private var habits = mutableListOf<RecItem>()

    private fun loadFromDatabase() {
        val habitEntities = dao.getAll()
        habitEntities.forEach { habits.add(
            RecItem(
                it.name,
                it.description,
                it.priority,
                it.typeOfHabit,
                it.periodicity,
                it.countOfExecs,
                it.frequencyOfExecs,
                it.color,
                it.id
            ))
        }
    }

    private fun loadToDatabase() {
        val habitEntities = mutableListOf<HabitEntity>()
        habits.forEach {
            habitEntities.add(
                HabitEntity(
                    it.id,
                    it.color,
                    it.name ?: "",
                    it.description ?: "",
                    it.priority ?: "",
                    it.typeOfHabit ?: "",
                    it.periodicity ?: "",
                    it.countOfExecsOfHabit,
                    it.frequencyOfExecs
                )
            )

        }
        GlobalScope.launch(Dispatchers.IO) {
            habitEntities.forEach {
                dao.insertAll(it)
            }
        }


    }

    private fun updateToDataBase(item: HabitEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.updateHabit(item)
        }

    }

    private fun deleteFromDatabase(item: HabitEntity) {
        GlobalScope.launch(Dispatchers.IO) {
            dao.deleteHabit(item)
        }

    }

    fun readFromRepository() {
        loadFromDatabase()
        val goodTypeOfHabit = App.res?.getString(R.string.good_radio_button)
        val badTypeOfHabit = App.res?.getString(R.string.bad_radio_button)
        goodHabits = habits.filter { it.typeOfHabit == goodTypeOfHabit } as MutableList<RecItem>
        badHabits = habits.filter { it.typeOfHabit == badTypeOfHabit } as MutableList<RecItem>
    }

    fun loadToRepository(data: MutableList<RecItem>) {
        habits.addAll(data)
        loadToDatabase()
    }

    fun updateToRepository(item: RecItem) {
        val habit = HabitEntity(
            item.id,
            item.color,
            item.name ?: "",
            item.description
                ?: "",
            item.priority ?: "",
            item.typeOfHabit ?: "",
            item.periodicity ?: "",
            item.countOfExecsOfHabit,
            item.frequencyOfExecs
        )
        updateToDataBase(habit)
    }

    fun deleteFromRepository(item: RecItem) {
        val habit = HabitEntity(
            item.id,
            item.color,
            item.name ?: "",
            item.description ?: "",
            item.priority ?: "",
            item.typeOfHabit ?: "",
            item.periodicity ?: "",
            item.countOfExecsOfHabit,
            item.frequencyOfExecs
        )
        deleteFromDatabase(habit)
    }
}