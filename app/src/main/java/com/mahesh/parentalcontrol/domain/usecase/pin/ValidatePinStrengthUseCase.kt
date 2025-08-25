package com.mahesh.parentalcontrol.domain.usecase.pin

class ValidatePinStrengthUseCase {
    data class Result(val ok: Boolean, val message: String? = null)

    /**
     * Example policy:
     * - 4–8 digits
     * - digits only
     */
    operator fun invoke(pin: String): Result {
        if (pin.length !in 4..8) return Result(false, "PIN must be 4–8 digits")
        if (!pin.all { it.isDigit() }) return Result(false, "PIN must contain only digits")
        if (pin.toSet().size == 1) return Result(false, "PIN is too weak")
        return Result(true)
    }
}
