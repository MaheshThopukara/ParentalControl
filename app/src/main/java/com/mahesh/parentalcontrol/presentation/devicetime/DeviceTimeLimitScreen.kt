package com.mahesh.parentalcontrol.presentation.devicetime

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahesh.parentalcontrol.core.util.toHour
import com.mahesh.parentalcontrol.core.util.toMinutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceTimeLimitScreen(
    viewModel: DeviceTimeLimitViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit
) {

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val deviceTimeLimit by viewModel.deviceTimeLimit.collectAsState()
    val timePickerState = rememberTimePickerState(
        initialHour = deviceTimeLimit.timeLimitMillis.toHour(),
        initialMinute = deviceTimeLimit.timeLimitMillis.toMinutes(),
        is24Hour = true
    )

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "Device Time Limit"
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
                .padding(16.dp)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {

            item {
                FilledTonalButton(
                    onClick = {
                        if (deviceTimeLimit.isTurnedOn) {
                            viewModel.updateDeviceTimeLimit(
                                false,
                                deviceTimeLimit.timeLimitMillis
                            )
                        } else {
                            viewModel.updateDeviceTimeLimit(
                                true,
                                timePickerState.hour * 60 * 60 * 1000L + timePickerState.minute * 60 * 1000L
                            )
                        }
                    }
                ) {
                    Text(
                        text = if (deviceTimeLimit.isTurnedOn) "Turn Off now" else "Turn On now",
                    )
                }
            }


            item {
                TimePicker(state = timePickerState)
            }
        }
    }


}