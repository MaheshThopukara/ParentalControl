package com.mahesh.parentalcontrol.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahesh.parentalcontrol.core.util.toAppTimeDisplay
import com.mahesh.parentalcontrol.core.util.toDashBoardTotalScreenTimeDisplay
import com.mahesh.parentalcontrol.core.util.toMinutes
import com.mahesh.parentalcontrol.presentation.components.PreferenceItem
import com.mahesh.parentalcontrol.presentation.dashboard.DashboardViewModel.App

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onDeviceLimitClick: () -> Unit,
    onAppLimitClick: () -> Unit,
    onBlocklistClick: () -> Unit,
    onNavigationIconClick: () -> Unit
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val topAppUiState by viewModel.topAppUiState.collectAsState()
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "Parental controls"
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
            item {
                Text(
                    text = topAppUiState.totalScreenTime.toDashBoardTotalScreenTimeDisplay(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                )
            }

            item {
                StackedUsageBar(apps = topAppUiState.topApps)
            }

            item {
                UsageLegend(apps = topAppUiState.topApps)
            }

            item {
                PreferenceItem(
                    title = "Device Time Limit",
                    summary = "Set a daily usage limit for the device",
                    onClick = onDeviceLimitClick
                )
            }

            item {
                PreferenceItem(
                    title = "App Time Limit",
                    summary = "Set usage limits per app",
                    onClick = onAppLimitClick
                )
            }

            item {
                PreferenceItem(
                    title = "Blocked Apps",
                    summary = "Choose apps to block during focus mode",
                    onClick = onBlocklistClick
                )
            }
        }
    }
}

@Composable
fun StackedUsageBar(apps: List<App>) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .height(30.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        apps.forEach { app ->
            Box(
                modifier = Modifier
                    .weight(app.percentage.toFloat() / 100)
                    .fillMaxHeight()
                    .background(app.color)
            )
        }
    }
}

@Composable
fun UsageLegend(apps: List<App>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        apps.forEach { app ->
            Row(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(app.color, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    app.appName,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    app.screenTime.toDashBoardTotalScreenTimeDisplay(),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}