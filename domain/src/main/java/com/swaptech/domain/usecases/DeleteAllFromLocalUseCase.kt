package com.swaptech.domain.usecases


import com.swaptech.domain.Repository
import javax.inject.Inject

open class DeleteAllFromLocalUseCase @Inject constructor(private val repo: Repository) {
    fun deleteAllFromLocal() {
        repo.deleteAllFromLocal()
    }
}