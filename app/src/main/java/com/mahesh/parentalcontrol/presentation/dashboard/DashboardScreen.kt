package com.mahesh.parentalcontrol.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mahesh.parentalcontrol.core.util.toDashBoardTotalScreenTimeDisplay
import com.mahesh.parentalcontrol.presentation.components.PreferenceItem
import com.mahesh.parentalcontrol.presentation.dashboard.DashboardViewModel.App

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    totalTimeText: String,
    apps: List<App>,
    onDeviceLimitClick: () -> Unit,
    onAppLimitClick: () -> Unit,
    onBlocklistClick: () -> Unit,
    onNavigationIconClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text("Parental controls") },
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
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item("total-time") {
                Text(
                    text = totalTimeText,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                )
            }

            item("usage-bar") {
                StackedUsageBar(apps = apps)
            }

            item("legend-title-spacer") { Spacer(Modifier.height(4.dp)) }

            // Legend rows (stable keys so unchanged items skip recomposition)
            items(
                items = apps,
                key = { it.appName } // use a stable unique id if you have one
            ) { app ->
                UsageLegendRow(app = app)
            }

            item("prefs-device") {
                PreferenceItem(
                    title = "Device Time Limit",
                    summary = "Set a daily usage limit for the device",
                    onClick = onDeviceLimitClick
                )
            }
            item("prefs-app") {
                PreferenceItem(
                    title = "App Time Limit",
                    summary = "Set usage limits per app",
                    onClick = onAppLimitClick
                )
            }
            item("prefs-block") {
                PreferenceItem(
                    title = "Blocked Apps",
                    summary = "Choose apps to block during focus mode",
                    onClick = onBlocklistClick
                )
            }

            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

@Composable
private fun StackedUsageBar(apps: List<App>) {
    val total = remember(apps) { apps.sumOf { it.percentage.toLong() }.coerceAtLeast(1) }
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .height(30.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        apps.forEach { app ->
            // Avoid division by zero and keep weights stable
            val w = (app.percentage.coerceAtLeast(0)).toFloat() / total.toFloat()
            if (w > 0f) {
                Box(
                    modifier = Modifier
                        .weight(w)
                        .fillMaxHeight()
                        .background(app.color)
                )
            }
        }
    }
}

@Composable
private fun UsageLegendRow(app: App) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 4.dp)
            .fillMaxWidth(),
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