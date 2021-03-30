package com.swaptech.data.repository

import com.swaptech.data.database.HabitsFromServerDao
import com.swaptech.data.models.HabitForLocal
import com.swaptech.data.server.Api
import com.swaptech.domain.DatabaseHabit
import com.swaptech.domain.Repository
import com.swaptech.domain.models.Habit
import com.swaptech.domain.models.HabitDone
import com.swaptech.domain.models.HabitUID
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: Api,
                                         private val dao: HabitsFromServerDao): Repository {
    //Networking
    override suspend fun getHabits(): Response<List<Habit>> {
        return api.getHabitsFromServer()
    }

    override suspend fun addHabit(habit: Habit): Response<Any> {
        return api.addHabitToServer(habit)
    }

    override suspend fun updateHabit(habit: Habit) : Response<HabitUID> {
        return api.updateHabitToServer(habit)
    }

    override suspend fun deleteHabit(habitUID: HabitUID): Response<Unit> {
        return api.deleteHabitFromServer(habitUID)
    }

    override suspend fun setHabitIsCompletedOnServer(habitDone:HabitDone): Response<Any> {
        return api.setHabitIsCompletedInServer(habitDone)
    }

    //Local Interacting
    override fun getHabitsFromLocal(priority: String): Flow<List<HabitForLocal>> {
        return dao.getHabits(priority)
    }

    override suspend fun addHabitsToLocal(habit: DatabaseHabit) {
        dao.insertAll(habit as HabitForLocal)
    }

    override suspend fun updateHabitToLocal(habit: DatabaseHabit) {
        dao.updateHabit(habit as HabitForLocal)
    }

    override suspend fun deleteHabitFromLocal(habit: DatabaseHabit) {
        dao.deleteHabit(habit as HabitForLocal)
    }

    override fun deleteAllFromLocal() {
        dao.deleteAll()
    }
}