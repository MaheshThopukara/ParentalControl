package com.mahesh.parentalcontrol.presentation.pin.questions

import com.mahesh.parentalcontrol.domain.model.SecurityQuestion

data class SecurityQuestionsSetupUiState(
    val questions: List<SecurityQuestion> = emptyList(),
    val answers: Map<String, String> = emptyMap(),
    val isSaving: Boolean = false,
    val error: String? = null,
    val `continue`: Boolean = false
)