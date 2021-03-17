package com.swaptech.habitstwo.server.models

import androidx.room.Entity
import androidx.room.PrimaryKey


data class Habit(
    var color: Int = 1,
    var count: Int = 1,
    var date: Int = 1,
    var description: String,
    val doneDates: MutableList<Int> = mutableListOf(0),
    var frequency: Int,
    var priority: Int,
    var title: String,
    var type: Int,
    var uid: String = "",
)
