package com.mahesh.parentalcontrol.presentation.pin.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahesh.parentalcontrol.domain.usecase.pin.VerifyRecoveryCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecoverByCodeViewModel @Inject constructor(
    private val verifyRecoveryCode: VerifyRecoveryCodeUseCase
) : ViewModel() {
    private val _ui = MutableStateFlow(RecoverByCodeUiState())
    val ui: StateFlow<RecoverByCodeUiState> = _ui

    fun onCodeChanged(raw: String) {
        _ui.update { it.copy(code = formatRecoveryCode(raw), error = null) }
    }

    fun onSubmit() {
        viewModelScope.launch {
            val input = _ui.value.code
            if (input.isBlank()) {
                _ui.update { it.copy(error = "Enter your recovery code") }
                return@launch
            }
            _ui.update { it.copy(isSubmitting = true, error = null) }
            val ok = verifyRecoveryCode(input)
            _ui.update {
                it.copy(
                    isSubmitting = false,
                    proceedNext = ok,
                    error = if (!ok) "Invalid recovery code" else null
                )
            }
        }
    }

    /** Group as AAAA-BBBB-CCCC while user types (visual only). */
    private fun formatRecoveryCode(raw: String): String {
        val cleaned = raw.uppercase().replace(Regex("[^A-Z0-9]"), "")
        return cleaned.chunked(4).joinToString("-").take(14) // "XXXX-XXXX-XXXX"
    }
}