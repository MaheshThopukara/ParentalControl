package com.mahesh.parentalcontrol.presentation.apptime

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.mahesh.parentalcontrol.core.util.toAppTimeDisplay
import com.mahesh.parentalcontrol.core.util.toScreenTimeDisplay
import com.mahesh.parentalcontrol.domain.model.AppUsage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTimeLimitScreen(
    appUsages: List<AppUsage>,
    onItemClick: (AppUsage) -> Unit,
    showDialog: Boolean,
    selectedAppName: String?,
    timePickerState: TimePickerState,
    onDialogConfirm: () -> Unit,
    onDialogDismiss: () -> Unit,
    onNavigationIconClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDialogDismiss,
            title = {
                Text("Set app timer")
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "This timer for ${selectedAppName ?: "the app"} resets at midnight.",
                        style = MaterialTheme.typography.bodySmall
                    )
                    TimePicker(state = timePickerState)
                }
            },
            confirmButton = {
                TextButton(onClick = onDialogConfirm) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = onDialogDismiss) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text("App Time Limit") },
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
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            items(
                items = appUsages,
                key = { it.packageName } // stable key
            ) { app ->
                AppTimeLimitItem(
                    usage = app,
                    onItemClick = onItemClick
                )
                Divider()
            }
        }
    }
}

@Composable
fun AppTimeLimitItem(
    usage: AppUsage,
    onItemClick: (AppUsage) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(role = Role.Button) { onItemClick(usage) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(usage.icon),
            contentDescription = usage.appName,
            modifier = Modifier.size(40.dp)
        )

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(usage.appName, fontWeight = FontWeight.Bold)
            Text(
                text = usage.screenTimeMillis.toScreenTimeDisplay(),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(
            modifier = Modifier.widthIn(min = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val appLimit = usage.dailyLimitMillis
            if (appLimit != null && appLimit > 0) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                Text(
                    text = appLimit.toAppTimeDisplay(),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            } else {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
            }
        }
    }
}