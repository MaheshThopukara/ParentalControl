package com.mahesh.parentalcontrol

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahesh.parentalcontrol.domain.usecase.pin.IsRecoveryCodeShownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isRecoveryCodeShownUseCase: IsRecoveryCodeShownUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    data class AuthState(val hasLoginDetails: Boolean? = null)

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    init {
        Log.d(TAG, "init: $this")
        viewModelScope.launch {
            if (isRecoveryCodeShownUseCase()) {
                _authState.update { it.copy(hasLoginDetails = true) }
            } else {
                _authState.update { it.copy(hasLoginDetails = false) }
            }
        }
    }
}