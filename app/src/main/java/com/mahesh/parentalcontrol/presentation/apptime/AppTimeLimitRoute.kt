package com.mahesh.parentalcontrol.presentation.apptime

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mahesh.parentalcontrol.domain.model.AppUsage
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.mahesh.parentalcontrol.core.util.toHour
import com.mahesh.parentalcontrol.core.util.toMinutes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTimeLimitRoute(
    viewModel: AppTimeLimitViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit
) {
    val appUsages by viewModel.appUsages.collectAsStateWithLifecycle()

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)

    // Dialog + picker state
    var selectedApp by remember { mutableStateOf<AppUsage?>(null) }
    var showTimePickerDialog by rememberSaveable { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(is24Hour = true)

    // Sync picker when dialog opens or selection changes
    LaunchedEffect(showTimePickerDialog, selectedApp?.dailyLimitMillis) {
        if (showTimePickerDialog) {
            val millis = selectedApp?.dailyLimitMillis ?: 0L
            timePickerState.hour = millis.toHour()
            timePickerState.minute = millis.toMinutes()
        }
    }

    // Derive millis from picker (recomputes only when hour/min change)
    val pickedMillis = remember(timePickerState.hour, timePickerState.minute) {
        timePickerState.hour * 60L * 60L * 1000L +
                timePickerState.minute * 60L * 1000L
    }

    // Confirm handler (no !!)
    val onConfirm: () -> Unit = {
        val app = selectedApp
        when {
            pickedMillis == 0L -> viewModel.deleteAppTimeLimit(app!!.packageName)
            app!!.dailyLimitMillis == null -> viewModel.addAppTimeLimit(app!!.packageName, pickedMillis)
            else -> viewModel.updateAppTimeLimit(app!!.packageName, pickedMillis)
        }
        selectedApp = null
        showTimePickerDialog = false
    }

    AppTimeLimitScreen(
        appUsages = appUsages,
        onItemClick = { app ->
            selectedApp = app
            showTimePickerDialog = true
        },
        showDialog = showTimePickerDialog,
        selectedAppName = selectedApp?.appName,
        timePickerState = timePickerState,
        onDialogConfirm = onConfirm,
        onDialogDismiss = {
            selectedApp = null
            showTimePickerDialog = false
        },
        onNavigationIconClick = onNavigationIconClick,
        scrollBehavior = scrollBehavior
    )
}