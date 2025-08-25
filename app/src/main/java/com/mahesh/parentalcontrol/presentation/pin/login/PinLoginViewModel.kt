package com.mahesh.parentalcontrol.presentation.pin.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahesh.parentalcontrol.domain.usecase.pin.ValidatePinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinLoginViewModel @Inject constructor(
    private val validatePinUseCase: ValidatePinUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PinLoginUiState())
    val uiState: StateFlow<PinLoginUiState> = _uiState

    fun onPinChange(newPin: String) {
        _uiState.value = _uiState.value.copy(enteredPin = newPin, error = null)
    }

    fun onSubmitPin() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val isValid = validatePinUseCase(_uiState.value.enteredPin)

            if (isValid) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    proceedNext = true
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Invalid PIN"
                )
            }
        }
    }
}