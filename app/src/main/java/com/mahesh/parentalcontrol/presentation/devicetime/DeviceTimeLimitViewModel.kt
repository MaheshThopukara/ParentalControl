package com.mahesh.parentalcontrol.presentation.devicetime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahesh.parentalcontrol.domain.model.AppUsage
import com.mahesh.parentalcontrol.domain.model.DeviceTimeLimit
import com.mahesh.parentalcontrol.domain.usecase.GetDeviceTimeLimitUseCase
import com.mahesh.parentalcontrol.domain.usecase.UpdateDeviceTimeLimitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceTimeLimitViewModel @Inject constructor(
    private val getDeviceTimeLimitUseCase: GetDeviceTimeLimitUseCase,
    private val updateDeviceTimeLimitUseCase: UpdateDeviceTimeLimitUseCase
) : ViewModel() {
    private val _deviceTimeLimit = MutableStateFlow<DeviceTimeLimit>(DeviceTimeLimit())
    val deviceTimeLimit: StateFlow<DeviceTimeLimit> = _deviceTimeLimit

    init {
        loadDeviceTimeLimit()
    }

    fun loadDeviceTimeLimit() {
        viewModelScope.launch {
            _deviceTimeLimit.value = getDeviceTimeLimitUseCase()
        }
    }

    fun updateDeviceTimeLimit(isTurnedOn: Boolean, timeLimitMillis: Long) {
        viewModelScope.launch {
            updateDeviceTimeLimitUseCase(isTurnedOn, timeLimitMillis)
            loadDeviceTimeLimit() // TODO: Refactor with flow
        }
    }


}