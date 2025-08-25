package com.mahesh.parentalcontrol.presentation.pin.recoverycode

data class RecoveryCodeUiState(
    val code: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val `continue`: Boolean = false
)