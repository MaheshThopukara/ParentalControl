package com.mahesh.parentalcontrol.presentation.pin.recoverycode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahesh.parentalcontrol.domain.usecase.pin.GenerateAndStoreRecoveryCodeUseCase
import com.mahesh.parentalcontrol.domain.usecase.pin.MarkRecoveryCodeShownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecoveryCodeViewModel @Inject constructor(
    private val generateAndStore: GenerateAndStoreRecoveryCodeUseCase,
    private val markRecoveryCodeShownUseCase: MarkRecoveryCodeShownUseCase,
) : ViewModel() {

    private val _ui = MutableStateFlow(RecoveryCodeUiState())
    val ui: StateFlow<RecoveryCodeUiState> = _ui

    init {
        generateRecoveryCode()
    }

    fun generateRecoveryCode() {
        viewModelScope.launch {
            _ui.update { it.copy(isLoading = true, error = null) }
            try {
                val code = generateAndStore()
                _ui.update { it.copy(isLoading = false, code = code) }
            } catch (t: Throwable) {
                _ui.update { it.copy(isLoading = false, error = t.message ?: "Failed to generate code") }
            }
        }
    }

    fun markShown() {
        viewModelScope.launch {
            markRecoveryCodeShownUseCase()
            _ui.update { it.copy(proceedNext = true) }
        }
    }
}