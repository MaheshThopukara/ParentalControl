package com.mahesh.parentalcontrol.presentation.apptime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahesh.parentalcontrol.domain.model.AppUsage
import com.mahesh.parentalcontrol.domain.usecase.AddAppTimeLimitUseCase
import com.mahesh.parentalcontrol.domain.usecase.DeleteAppTimeLimitUseCase
import com.mahesh.parentalcontrol.domain.usecase.GetAppUsageUseCase
import com.mahesh.parentalcontrol.domain.usecase.UpdateAppTimeLimitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppTimeLimitViewModel @Inject constructor(
    private val getAppUsageUseCase: GetAppUsageUseCase,
    private val addAppTimeLimitUseCase: AddAppTimeLimitUseCase,
    private val deleteAppTimeLimitUseCase: DeleteAppTimeLimitUseCase,
    private val updateAppTimeLimitUseCase: UpdateAppTimeLimitUseCase
) : ViewModel() {

    private val _appUsages = MutableStateFlow<List<AppUsage>>(emptyList())
    val appUsages: StateFlow<List<AppUsage>> = _appUsages

    init {
        loadAppUsages()
    }

    fun loadAppUsages() {
        viewModelScope.launch {
            _appUsages.value = getAppUsageUseCase()
        }
    }

    fun addAppTimeLimit(packageName: String, dailyLimitMillis: Long) {
        viewModelScope.launch {
            addAppTimeLimitUseCase(packageName, dailyLimitMillis)
            loadAppUsages() // TODO: Refactor with flow
        }
    }

    fun updateAppTimeLimit(packageName: String, dailyLimitMillis: Long) {
        viewModelScope.launch {
            updateAppTimeLimitUseCase(packageName, dailyLimitMillis)
            loadAppUsages() // TODO: Refactor with flow
        }
    }

    fun deleteAppTimeLimit(packageName: String) {
        viewModelScope.launch {
            deleteAppTimeLimitUseCase(packageName)
            loadAppUsages() // TODO: Refactor with flow
        }

    }
}