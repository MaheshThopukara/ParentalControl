package com.mahesh.parentalcontrol.presentation.pin.register

data class PinSetupUiState(
    val pin: String = "",
    val confirmPin: String = "",
    val error: String? = null,
    val isSaving: Boolean = false,
    val isPinSetupCompleted: Boolean = false,
    val proceedNext: Boolean = false
) {
    val canContinue: Boolean
        get() = pin.isNotBlank() && confirmPin.isNotBlank()
}
