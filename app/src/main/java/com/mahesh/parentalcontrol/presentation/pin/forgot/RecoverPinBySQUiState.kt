package com.mahesh.parentalcontrol.presentation.pin.forgot

import com.mahesh.parentalcontrol.domain.model.SecurityQuestion

data class RecoverPinBySQUiState(
    val questions: List<SecurityQuestion> = emptyList(),
    val answers: Map<String, String> = emptyMap(), // key -> input
    val isSubmitting: Boolean = false,
    val error: String? = null,
    val proceedNext: Boolean = false,
    val minRequired: Int = 3
)