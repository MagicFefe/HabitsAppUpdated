package com.swaptech.habitstwo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.swaptech.habitstwo.model.HabitForLocal

@Database(entities = [HabitForLocal::class], version = 1,  exportSchema = false)
abstract class HabitsDatabase : RoomDatabase() {
    abstract fun habitsFromServerDao(): HabitsFromServerDao
}