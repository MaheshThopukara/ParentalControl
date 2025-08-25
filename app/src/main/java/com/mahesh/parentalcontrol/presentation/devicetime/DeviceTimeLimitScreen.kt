package com.mahesh.parentalcontrol.presentation.devicetime

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.mahesh.parentalcontrol.core.util.toHour
import com.mahesh.parentalcontrol.core.util.toMinutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceTimeLimitScreen(
    isOn: Boolean,
    pickedMillis: Long,
    timePickerState: TimePickerState,
    onToggle: (Boolean) -> Unit,
    onNavigationIconClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text("Device Time Limit") },
                navigationIcon = {
                    IconButton(onClick = onNavigationIconClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item("toggle") {
                FilledTonalButton(
                    onClick = { onToggle(!isOn) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isOn) "Turn Off now" else "Turn On now")
                }
            }

            item("picker") {
                // You can disable the picker when OFF if preferred:
                // CompositionLocalProvider(LocalContentColor provides ..., LocalContentAlpha provides ...) { ... }
                TimePicker(state = timePickerState)
            }

            // (Optional) show current picked time for clarity
            item("summary") {
                Text(
                    text = "Selected: ${pickedMillis.toHour()}h ${
                        pickedMillis.toMinutes().mod(60)
                    }m",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}