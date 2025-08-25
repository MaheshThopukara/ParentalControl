package com.mahesh.parentalcontrol.presentation.pin.login

data class PinLoginUiState(
    val enteredPin: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)