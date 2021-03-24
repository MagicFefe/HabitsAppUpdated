package com.swaptech.domain.usecases


import com.swaptech.domain.Repository
import com.swaptech.domain.models.Habit
import javax.inject.Inject

class RefreshHabitUseCase @Inject constructor(private val repo: Repository) {
    suspend fun updateHabit(habit: Habit) {
        repo.updateHabit(habit)
    }
}