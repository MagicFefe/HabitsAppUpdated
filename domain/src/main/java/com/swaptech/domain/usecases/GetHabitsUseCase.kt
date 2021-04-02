package com.swaptech.domain.usecases

import com.swaptech.domain.Repository
import com.swaptech.domain.models.Habit
import retrofit2.Response
import javax.inject.Inject

open class GetHabitsUseCase @Inject constructor(private val repo: Repository) {
    suspend fun getHabits(): Response<List<Habit>> {
        return repo.getHabits()
    }
}