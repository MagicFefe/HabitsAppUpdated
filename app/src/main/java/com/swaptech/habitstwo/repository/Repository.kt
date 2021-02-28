package com.swaptech.habitstwo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.swaptech.habitstwo.database.HabitsDatabase
import com.swaptech.habitstwo.server.RetrofitApi
import com.swaptech.habitstwo.server.models.Habit
import com.swaptech.habitstwo.server.models.HabitDone
import com.swaptech.habitstwo.server.models.HabitUID
import retrofit2.Response

class Repository(context: Context) {
    val db = Room.databaseBuilder(context, HabitsDatabase::class.java, "fromServer").allowMainThreadQueries().build()
    val dao = db.habitsFromServerDao()


    suspend fun getHabits(): Response<List<Habit>> {
        return RetrofitApi.api.getHabitsFromServer()
    }
    suspend fun addHabit(habit: Habit): Response<HabitUID> {
        return RetrofitApi.api.addHabitToServer(habit)
    }
    suspend fun updateHabit(habit: Habit) : Response<HabitUID> {
        return RetrofitApi.api.updateHabitToServer(habit)
    }
    suspend fun deleteHabit(habitUID: HabitUID): Response<Any> {
        return RetrofitApi.api.deleteHabitFromServer(habitUID)
    }
    suspend fun setHabitIsCompletedInServer(habitDone: HabitDone): Response<Any> {
        return RetrofitApi.api.setHabitIsCompletedInServer(habitDone)
    }


    fun getHabitsFromLocal(): LiveData<List<Habit>> {
        return dao.getHabits()
    }
    suspend fun addHabitsToLocal(habit: Habit) {
        dao.insertAll(habit)
    }
    suspend fun updateHabitToLocal(habit: Habit) {
        dao.updateHabit(habit)
    }
    suspend fun deleteHabitFromLocal(habit: Habit) {
        dao.deleteHabit(habit)
    }
}