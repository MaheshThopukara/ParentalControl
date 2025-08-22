package com.mahesh.parentalcontrol.presentation.apptime

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.mahesh.parentalcontrol.core.util.toAppTimeDisplay
import com.mahesh.parentalcontrol.core.util.toHour
import com.mahesh.parentalcontrol.core.util.toMinutes
import com.mahesh.parentalcontrol.core.util.toScreenTimeDisplay
import com.mahesh.parentalcontrol.domain.model.AppUsage
import com.mahesh.parentalcontrol.presentation.components.PreferenceItem
import kotlin.time.Duration.Companion.hours

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppUsageScreen(
    viewModel: AppTimeLimitViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit
) {
    val selectedApp = remember { mutableStateOf<AppUsage?>(null) }
    val showTimePickerDialog = remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(is24Hour = true)

    if (showTimePickerDialog.value) {
        AlertDialog(
            onDismissRequest = { showTimePickerDialog.value = false },
            title = {
                PreferenceItem(
                    title = "Set app timer",
                    summary = "This app timer for ${selectedApp.value?.appName} will reset at midnight"
                ) { }
            },
            text = {
                TimePicker(state = timePickerState)
            },
            confirmButton = {
                Button(onClick = {
                    val totalMinutesInMills =
                        ((timePickerState.hour * 60) + timePickerState.minute) * 60 * 1000L

                    if (selectedApp.value!!.dailyLimitMillis == null && totalMinutesInMills != 0.toLong()) {
                        viewModel.addAppTimeLimit(
                            selectedApp.value!!.packageName,
                            totalMinutesInMills
                        )
                    } else if (totalMinutesInMills == 0.toLong()) {
                        viewModel.deleteAppTimeLimit(selectedApp.value!!.packageName)
                    } else {
                        viewModel.updateAppTimeLimit(
                            selectedApp.value!!.packageName,
                            totalMinutesInMills
                        )
                    }
                    selectedApp.value = null
                    showTimePickerDialog.value = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        selectedApp.value = null
                        showTimePickerDialog.value = false
                    }) {
                    Text("Cancel")
                }
            }
        )
    }


    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val appUsages by viewModel.appUsages.collectAsState()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "App Time Limit"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigationIconClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            items(appUsages) { app ->
                AppUsageItem(app) {
                    selectedApp.value = app
                    timePickerState.hour = app.dailyLimitMillis?.toHour() ?: 0
                    timePickerState.minute = app.dailyLimitMillis?.toMinutes() ?: 0
                    showTimePickerDialog.value = true
                }
            }
        }
    }


}

@Composable
fun AppUsageItem(usage: AppUsage, onItemClick: (AppUsage) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable() { onItemClick(usage) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(usage.icon),
            contentDescription = usage.appName,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = usage.appName, fontWeight = FontWeight.Bold)
            val screenTime = usage.screenTimeMillis
            Text(
                text = screenTime.toScreenTimeDisplay(),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Box(
            modifier = Modifier
                .width(1.dp)
                .height(48.dp)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.width(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val appLimit = usage.dailyLimitMillis
            if (appLimit != null && appLimit > 0) {
                // App Limit Time is set
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Localized description"
                )
                Text(text = appLimit.toAppTimeDisplay(), fontSize = 12.sp, color = Color.Gray)
            } else {
                // App Limit Time is not set
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Localized description"
                )
            }
        }
    }
}