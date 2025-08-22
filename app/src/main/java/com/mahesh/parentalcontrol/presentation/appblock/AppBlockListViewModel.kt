package com.mahesh.parentalcontrol.presentation.appblock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahesh.parentalcontrol.domain.model.AppBlockList
import com.mahesh.parentalcontrol.domain.usecase.DeleteAppBlockListUseCase
import com.mahesh.parentalcontrol.domain.usecase.GetAppBlockListUseCase
import com.mahesh.parentalcontrol.domain.usecase.GetAppBlockSettingUseCase
import com.mahesh.parentalcontrol.domain.usecase.SetAppBlockListUseCase
import com.mahesh.parentalcontrol.domain.usecase.SetAppBlockSettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppBlockListViewModel @Inject constructor(
    private val setAppBlockSettingUseCase: SetAppBlockSettingUseCase,
    private val setAppBlockListUseCase: SetAppBlockListUseCase,
    private val deleteAppBlockListUseCase: DeleteAppBlockListUseCase,
    private val getAppBlockSettingUseCase: GetAppBlockSettingUseCase,
    private val getAppBlockListUseCase: GetAppBlockListUseCase
) : ViewModel() {

    data class AppBlockListUiState(
        val isBlockListEnabled: Boolean = false,
        val appBlockList: List<AppBlockList> = emptyList()
    )

    private val _appBlockListUiState = MutableStateFlow<AppBlockListUiState>(AppBlockListUiState())
    val appBlockListUiState: StateFlow<AppBlockListUiState> = _appBlockListUiState

    init {
        loadAppBlockList()
    }

    fun loadAppBlockList() {
        viewModelScope.launch {
            val blockListEnabled = getAppBlockSettingUseCase()
            val appBlockList = getAppBlockListUseCase()
            _appBlockListUiState.value = AppBlockListUiState(blockListEnabled, appBlockList)
        }
    }

    fun addToBlockList(packageName: String) {
        viewModelScope.launch {
            setAppBlockListUseCase(packageName)
            loadAppBlockList() // TODO: Refresh with flow
        }
    }

    fun deleteFromBlockList(packageName: String) {
        viewModelScope.launch {
            deleteAppBlockListUseCase(packageName)
            loadAppBlockList() // TODO: Refresh with flow
        }
    }

    fun setBlockListEnabled(isBlockListEnabled: Boolean) {
        viewModelScope.launch {
            setAppBlockSettingUseCase(isBlockListEnabled)
            loadAppBlockList() // TODO: Refresh with flow
        }
    }

}