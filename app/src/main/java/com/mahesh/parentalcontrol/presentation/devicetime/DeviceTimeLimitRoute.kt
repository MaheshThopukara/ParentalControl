package com.mahesh.parentalcontrol.presentation.devicetime

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mahesh.parentalcontrol.core.util.toHour
import com.mahesh.parentalcontrol.core.util.toMinutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceTimeLimitRoute(
    viewModel: DeviceTimeLimitViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit
) {
    val deviceTimeLimit by viewModel.deviceTimeLimit.collectAsStateWithLifecycle()

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)

    // 1) Create once
    val timePickerState = rememberTimePickerState(
        initialHour = deviceTimeLimit.timeLimitMillis.toHour(),
        initialMinute = deviceTimeLimit.timeLimitMillis.toMinutes(),
        is24Hour = true
    )

    // 2) Keep it in sync when source changes
    LaunchedEffect(deviceTimeLimit.timeLimitMillis) {
        timePickerState.hour = deviceTimeLimit.timeLimitMillis.toHour()
        timePickerState.minute = deviceTimeLimit.timeLimitMillis.toMinutes()
    }

    // 3) Derive millis only from state
    val pickedMillis = remember(timePickerState.hour, timePickerState.minute) {
        timePickerState.hour * 60L * 60L * 1000L +
                timePickerState.minute * 60L * 1000L
    }

    DeviceTimeLimitScreen(
        isOn = deviceTimeLimit.isTurnedOn,
        pickedMillis = pickedMillis,
        timePickerState = timePickerState,
        onToggle = { turnOn ->
            val millis = if (turnOn) pickedMillis else deviceTimeLimit.timeLimitMillis
            viewModel.updateDeviceTimeLimit(turnOn, millis)
        },
        onNavigationIconClick = onNavigationIconClick,
        scrollBehavior = scrollBehavior
    )
}
