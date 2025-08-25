package com.mahesh.parentalcontrol.domain.usecase.pin

import com.mahesh.parentalcontrol.domain.repository.PinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeletePinUseCase(private val repo: PinRepository) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        repo.deletePin()
    }
}
