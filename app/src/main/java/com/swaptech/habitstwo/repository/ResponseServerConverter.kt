package com.swaptech.habitstwo.repository

import com.swaptech.habitstwo.App
import com.swaptech.habitstwo.R
import com.swaptech.habitstwo.database.HabitEntity
import com.swaptech.habitstwo.server.models.Habit

class ResponseServerConverter {
    private val good = App.res?.getString(R.string.good_radio_button)
    private val bad = App.res?.getString(R.string.bad_radio_button)
    private val high = App.res?.getString(R.string.high_priority)
    private val medium = App.res?.getString(R.string.medium_priority)
    private val low = App.res?.getString(R.string.low_priority)

    private val typeOfHabitMap = mapOf<String, Int>(good!! to 0,bad!! to 1)
    private val priorityOfHabitMap = mapOf<String, Int>(high!! to 2, medium!! to 1, low!! to 0)

    fun serializeToHabitEntity(habit: Habit) {

        //var habitEntity: HabitEntity = HabitEntity(uid = habit.uid)

    }
}