package com.swaptech.domain.usecases

import com.swaptech.domain.DatabaseHabit
import com.swaptech.domain.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHabitsFromLocalUseCase @Inject constructor(private val repo: Repository) {
    fun getHabitsFromLocal(): Flow<List<DatabaseHabit>> {
        return repo.getHabitsFromLocal()
    }
}