package com.mahesh.parentalcontrol.presentation.dashboard

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahesh.parentalcontrol.domain.usecase.GetAppUsageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getAppUsageUseCase: GetAppUsageUseCase,
) : ViewModel() {

    companion object {
        private const val TAG = "DashboardViewModel"
    }

    data class App(
        val appName: String = "",
        val screenTime: Long = 0L,
        val color: Color = Color.Gray,
        val percentage: Int = 0
    )

    data class TopAppUiState(
        val totalScreenTime: Long = 0L,
        val topApps: List<App> = emptyList(),
    )

    private val _topAppUiState = MutableStateFlow<TopAppUiState>(TopAppUiState())
    val topAppUiState: StateFlow<TopAppUiState> = _topAppUiState

    init {
        Log.d(TAG, "init: ${hashCode()}")
        loadApps()
    }

    fun loadApps() {
        viewModelScope.launch {
            val colors = mutableListOf<Color>(
                Color.Cyan,
                Color.Blue,
                Color.Green
            )
            val apps = getAppUsageUseCase()
            val totalScreenTime = apps.sumOf { it.screenTimeMillis }
            val top3Apps = apps.take(3)
            val topApps = top3Apps.mapIndexedNotNull { index, it ->
                val percentage = try {
                    (it.screenTimeMillis * 100 / totalScreenTime).toInt()
                } catch (exception: ArithmeticException) {
                    0
                }
                if (percentage == 0) {
                    null
                } else {
                    App(
                        appName = it.appName,
                        screenTime = it.screenTimeMillis,
                        color = colors[index],
                        percentage = percentage
                    )
                }
            }
            val totalApps = topApps.toMutableList()
            val othersScreenTime = apps.drop(3).sumOf { it.screenTimeMillis }
            val percentage = try {
                (othersScreenTime * 100 / totalScreenTime).toInt()
            } catch (exception: ArithmeticException) {
                0
            }
            if (percentage != 0) {
                totalApps.add(
                    App(
                        appName = "Others",
                        screenTime = othersScreenTime,
                        color = Color.Gray,
                        percentage = (othersScreenTime * 100 / totalScreenTime).toInt()
                    )
                )
            }
            Log.d("percentage", "totalApps: $totalApps")
            _topAppUiState.value = TopAppUiState(totalScreenTime, totalApps)
        }
    }
}