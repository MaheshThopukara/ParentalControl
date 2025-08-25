package com.mahesh.parentalcontrol.presentation.pin.register

data class RegisterPinUiState(
    val pin: String = "",
    val confirmPin: String = "",
    val error: String? = null,
    val isSaving: Boolean = false,
    val `continue`: Boolean = false
) {
    val canContinue: Boolean
        get() = pin.isNotBlank() && confirmPin.isNotBlank()
}
