package com.swaptech.domain.usecases

import com.swaptech.domain.DatabaseHabit
import com.swaptech.domain.Repository
import javax.inject.Inject

open class AddHabitToLocalUseCase @Inject constructor(private val repo: Repository) {
    suspend fun addHabitToLocal(habit: DatabaseHabit) {
        repo.addHabitsToLocal(habit)
    }
}