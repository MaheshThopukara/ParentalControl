package com.mahesh.parentalcontrol.domain.usecase.pin

import com.mahesh.parentalcontrol.domain.repository.PinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SavePinUseCase(private val repo: PinRepository) {
    suspend operator fun invoke(pin: String) = withContext(Dispatchers.IO) {
        repo.savePin(pin)
    }
}
