package com.swaptech.habitstwo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "HabitsLocal")
data class HabitForLocal(
        var color: Int = 0,
        var count: Int,
        @PrimaryKey (autoGenerate = true) var date: Int,
        var description: String,
        val doneDates: Int,
        var frequency: Int,
        var priority: String,
        var title: String,
        var type: String,
        var uid: String = "",
)
