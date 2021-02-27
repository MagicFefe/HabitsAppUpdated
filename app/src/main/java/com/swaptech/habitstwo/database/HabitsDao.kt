package com.swaptech.habitstwo.database

import androidx.room.*

@Dao
interface HabitsDao {
    @Query("SELECT * FROM Habits")
    fun getAll(): List<HabitEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg Habits: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

    @Update
    suspend fun updateHabit(habit: HabitEntity)
}