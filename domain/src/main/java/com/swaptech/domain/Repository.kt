package com.swaptech.domain

import com.swaptech.domain.models.Habit
import com.swaptech.domain.models.HabitDone
import com.swaptech.domain.models.HabitUID
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {

    //Networking
    suspend fun getHabits(): Response<List<Habit>>
    suspend fun addHabit(habit: Habit): Response<Any>
    suspend fun updateHabit(habit: Habit) : Response<HabitUID>
    suspend fun deleteHabit(habitUID: HabitUID): Response<Unit>
    suspend fun setHabitIsCompletedInServer(habitDone: HabitDone): Response<Any>

    //Local Interacting
    fun getHabitsFromLocal(priority: String): Flow<List<DatabaseHabit>>
    suspend fun addHabitsToLocal(habit: DatabaseHabit)
    suspend fun updateHabitToLocal(habit: DatabaseHabit)
    suspend fun deleteHabitFromLocal(habit: DatabaseHabit)
    fun deleteAllFromLocal()

}