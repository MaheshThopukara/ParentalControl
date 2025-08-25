package com.mahesh.parentalcontrol.presentation.pin.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahesh.parentalcontrol.domain.usecase.pin.IsRecoveryCodeShownUseCase
import com.mahesh.parentalcontrol.domain.usecase.pin.SavePinUseCase
import com.mahesh.parentalcontrol.domain.usecase.pin.ValidatePinStrengthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterPinViewModel @Inject constructor(
    private val validatePin: ValidatePinStrengthUseCase,
    private val savePin: SavePinUseCase,
) : ViewModel() {

    companion object Companion {
        private const val TAG = "RegisterPinViewModel"
    }

    private val _ui = MutableStateFlow(RegisterPinUiState())
    val ui: StateFlow<RegisterPinUiState> = _ui

    init {
        Log.d(TAG, "init: ${hashCode()}")
    }

    fun onPinChanged(value: String) {
        _ui.update { it.copy(pin = value.take(8), error = null) }
    }

    fun onConfirmChanged(value: String) {
        _ui.update { it.copy(confirmPin = value.take(8), error = null) }
    }

    fun onSaveClicked() {
        viewModelScope.launch {
            val state = _ui.value
            if (state.pin != state.confirmPin) {
                _ui.update { it.copy(error = "PINs do not match") }
                return@launch
            }
            val result = validatePin(state.pin)
            if (!result.ok) {
                _ui.update { it.copy(error = result.message) }
                return@launch
            }
            _ui.update { it.copy(isSaving = true, error = null) }
            try {
                savePin(state.pin)
                _ui.update { it.copy(isSaving = false, `continue` = true) }
            } catch (t: Throwable) {
                _ui.update { it.copy(isSaving = false, error = t.message ?: "Failed to save PIN") }
            }
        }
    }
}