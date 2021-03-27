package com.swaptech.data.database

import androidx.room.*
import com.swaptech.data.models.HabitForLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitsFromServerDao {
    @Query("SELECT * FROM HabitsLocal WHERE priority LIKE '%' || :priority || '%' ")
    fun getHabits(priority: String): Flow<List<HabitForLocal>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg habit: HabitForLocal)

    @Update
    suspend fun updateHabit(habit: HabitForLocal)

    @Delete
    suspend fun deleteHabit(habit: HabitForLocal)

    @Query("DELETE FROM HabitsLocal")
    fun deleteAll()
}