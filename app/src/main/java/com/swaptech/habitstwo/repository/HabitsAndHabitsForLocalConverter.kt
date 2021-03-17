package com.swaptech.habitstwo.repository

import com.swaptech.habitstwo.App
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.model.HabitForLocal
import com.swaptech.habitstwo.server.models.Habit

class HabitsAndHabitsForLocalConverter {
    private val good = App.res?.getString(R.string.good_radio_button)
    private val bad = App.res?.getString(R.string.bad_radio_button)
    private val high = App.res?.getString(R.string.high_priority)
    private val medium = App.res?.getString(R.string.medium_priority)
    private val low = App.res?.getString(R.string.low_priority)

    private val typeOfHabitMap = mapOf<String, Int>(good!! to 0, bad!! to 1)
    private val priorityOfHabitMap = mapOf<String, Int>(high!! to 2, medium!! to 1, low!! to 0)

    fun serializeToHabit(habit: HabitForLocal): Habit {
        val typeOfHabit = habit.type
        var typeOfHabitInt = 0
        for ((value: String, key: Int) in typeOfHabitMap) {
            if (typeOfHabit == value) {
                typeOfHabitInt = key
                break
            }
        }
        val priorityOfHabit = habit.priority
        var priorityOfHabitInt = 0
        for ((value: String, key: Int) in priorityOfHabitMap) {
            if (priorityOfHabit == value) {
                priorityOfHabitInt = key
            }
        }
        val result = Habit(color = habit.color, count = habit.count, date = habit.date,
                description = habit.description, doneDates = mutableListOf(habit.doneDates),
                frequency = habit.frequency, priority = priorityOfHabitInt, title = habit.title,
                type = typeOfHabitInt, uid = habit.uid)

        return result
    }

    fun deserializeToHabitForLocal(habit: Habit): HabitForLocal {
        val typeOfHabit = habit.type
        var typeOfHabitString = ""
        for ((value: String, key: Int) in typeOfHabitMap) {
            if (typeOfHabit == key) {
                typeOfHabitString = value
                break
            }
        }
        val priorityOfHabit = habit.priority
        var priorityOfHabitString = ""
        for ((value: String, key: Int) in priorityOfHabitMap) {
            if (priorityOfHabit == key) {
                priorityOfHabitString = value
            }
        }

        val result = HabitForLocal(color = habit.color, count = habit.count, date = habit.date,
                description = habit.description, doneDates = 0,
                frequency = habit.frequency, priority = priorityOfHabitString, title = habit.title,
                type = typeOfHabitString, uid = habit.uid)

        return result
    }
}