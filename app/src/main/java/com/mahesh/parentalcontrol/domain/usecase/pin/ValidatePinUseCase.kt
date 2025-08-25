package com.mahesh.parentalcontrol.domain.usecase.pin

import com.mahesh.parentalcontrol.domain.repository.PinRepository

class ValidatePinUseCase(
    private val repository: PinRepository
) {
    suspend operator fun invoke(enteredPin: String) = repository.validatePin(enteredPin)
}