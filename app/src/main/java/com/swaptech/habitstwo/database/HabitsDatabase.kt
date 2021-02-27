package com.swaptech.habits.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.swaptech.habitstwo.database.HabitEntity
import com.swaptech.habitstwo.database.HabitsDao

@Database(entities = arrayOf(HabitEntity::class), version = 1, exportSchema = false)
abstract class HabitsDatabase : RoomDatabase() {
    abstract fun habitsDao(): HabitsDao


}

