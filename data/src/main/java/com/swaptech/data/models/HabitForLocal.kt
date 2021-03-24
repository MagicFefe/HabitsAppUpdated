package com.swaptech.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.swaptech.domain.DatabaseHabit

@Entity(tableName = "HabitsLocal")
data class HabitForLocal(
        override var color: Int = 0,
        override var count: Int,
        @PrimaryKey (autoGenerate = true) override var date: Int,
        override var description: String,
        override val doneDates: Int,
        override var frequency: Int,
        override var priority: String,
        override var title: String,
        override var type: String,
        override var uid: String = "",
) : DatabaseHabit
