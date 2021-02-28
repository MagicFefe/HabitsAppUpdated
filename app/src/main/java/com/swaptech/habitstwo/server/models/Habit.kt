package com.swaptech.habitstwo.server.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "HabitsFromServer")
data class Habit(
    var color: Int = 0,
    var count: Int,
    var date: Int,
    var description: String,
    val doneDates: Int = 1,
    var frequuency: Int,
    var priority: Int,
    var title: String,
    var type: Int,
    var uid: String = "",
    @PrimaryKey(autoGenerate = true) var id: Int
)
