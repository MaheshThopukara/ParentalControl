package com.mahesh.parentalcontrol.presentation.pin.forgot

data class RecoverByCodeUiState(
    val code: String = "",
    val isSubmitting: Boolean = false,
    val error: String? = null,
    val proceedNext: Boolean = false
)