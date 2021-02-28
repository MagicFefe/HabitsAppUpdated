package com.swaptech.habitstwo.server

import com.swaptech.habitstwo.database.HabitEntity
import com.swaptech.habitstwo.server.models.Habit
import com.swaptech.habitstwo.server.models.HabitDone
import com.swaptech.habitstwo.server.models.HabitUID
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @GET("habit")
    suspend fun getHabitsFromServer(): Response<List<Habit>>

    @PUT("habit")
    suspend fun addHabitToServer(@Body habit: Habit): Response<HabitUID>

    @PUT("habit")
    suspend fun updateHabitToServer(@Body habit: Habit): Response<HabitUID>

    @DELETE("habit")
    suspend fun deleteHabitFromServer(@Body uid: HabitUID): Response<Any>

    @POST("habit_done")
    suspend fun setHabitIsCompletedInServer(habitDone: HabitDone): Response<Any>
}