package com.swaptech.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.swaptech.data.models.HabitForLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitsFromServerDao {
    @Query("SELECT * FROM HabitsLocal")
    fun getHabits(): Flow<List<HabitForLocal>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg habit: HabitForLocal)

    @Update
    suspend fun updateHabit(habit: HabitForLocal)

    @Delete
    suspend fun deleteHabit(habit: HabitForLocal)

    @Query("SELECT * FROM HabitsLocal WHERE priority = :priority")
    fun sortByPriority(priority: String): LiveData<List<HabitForLocal>>

    @Query("DELETE FROM HabitsLocal")
    fun deleteAll()
}