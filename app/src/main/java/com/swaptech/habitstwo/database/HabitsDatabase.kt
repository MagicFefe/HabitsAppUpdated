package com.swaptech.habitstwo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.swaptech.habitstwo.server.models.Habit

@Database(entities = [HabitEntity::class, Habit::class],version = 1,  exportSchema = false)
abstract class HabitsDatabase : RoomDatabase() {
    abstract fun habitsDao(): HabitsDao
    abstract fun habitsFromServerDao(): HabitsFromServerDao
}