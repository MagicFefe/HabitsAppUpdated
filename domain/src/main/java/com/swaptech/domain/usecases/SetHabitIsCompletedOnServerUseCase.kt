package com.swaptech.domain.usecases

import com.swaptech.domain.Repository
import com.swaptech.domain.models.HabitDone
import javax.inject.Inject

class SetHabitIsCompletedOnServerUseCase @Inject constructor(private val repo: Repository) {
    suspend fun setHabitIsCompletedOnServer(habitDone: HabitDone) {
        repo.setHabitIsCompletedOnServer(habitDone)
    }
}