package com.swaptech.habitstwo.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.swaptech.habitstwo.server.models.Habit

@Dao
interface HabitsFromServerDao {
    @Query("SELECT * FROM HabitsFromServer")
    fun getHabits(): LiveData<List<Habit>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)
}