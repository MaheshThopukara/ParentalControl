package com.mahesh.parentalcontrol.presentation.pin.forgot

import com.mahesh.parentalcontrol.domain.model.SecurityQuestion

data class RecoverPinByCodeUiState(
    val code: String = "",
    val isSubmitting: Boolean = false,
    val error: String? = null,
    val proceedNext: Boolean = false
)