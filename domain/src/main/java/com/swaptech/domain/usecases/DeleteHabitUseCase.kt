package com.swaptech.domain.usecases

import com.swaptech.domain.Repository
import com.swaptech.domain.models.HabitUID
import javax.inject.Inject

class DeleteHabitUseCase @Inject constructor(private val repo: Repository) {
    suspend fun deleteHabitUseCase(habitUID: HabitUID) {
        repo.deleteHabit(habitUID)
    }
}