package com.swaptech.habitstwo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.swaptech.habitstwo.model.HabitForLocal
import com.swaptech.habitstwo.database.HabitsDatabase
import com.swaptech.habitstwo.server.RetrofitApi
import com.swaptech.habitstwo.server.models.Habit
import com.swaptech.habitstwo.server.models.HabitDone
import com.swaptech.habitstwo.server.models.HabitUID
import retrofit2.Response

class Repository(context: Context) {

    private val db = Room.databaseBuilder(context, HabitsDatabase::class.java, "fromServer")
            .allowMainThreadQueries().build()
    private val dao = db.habitsFromServerDao()


    suspend fun getHabits(): Response<List<Habit>> {
        return RetrofitApi.api.getHabitsFromServer()
    }
    suspend fun addHabit(habit: Habit): Response<HabitUID> {
        return RetrofitApi.api.addHabitToServer(habit)
    }
    suspend fun updateHabit(habit: Habit) : Response<HabitUID> {
        return RetrofitApi.api.updateHabitToServer(habit)
    }
    suspend fun deleteHabit(habitUID: HabitUID): Response<Unit> {
        return RetrofitApi.api.deleteHabitFromServer(habitUID)
    }
    suspend fun setHabitIsCompletedInServer(habitDone: HabitDone): Response<Any> {
        return RetrofitApi.api.setHabitIsCompletedInServer(habitDone)
    }


    fun getHabitsFromLocal(): LiveData<List<HabitForLocal>> {
        return dao.getHabits()
    }
    suspend fun addHabitsToLocal(habit: HabitForLocal) {
        dao.insertAll(habit)
    }
    suspend fun updateHabitToLocal(habit: HabitForLocal) {
        dao.updateHabit(habit)
    }
    suspend fun deleteHabitFromLocal(habit: HabitForLocal) {
        dao.deleteHabit(habit)
    }
    fun deleteAllFromLocal() {
        dao.deleteAll()
    }
}