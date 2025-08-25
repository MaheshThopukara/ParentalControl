package com.mahesh.parentalcontrol.presentation.dashboard

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mahesh.parentalcontrol.core.util.toDashBoardTotalScreenTimeDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardRoute(
    viewModel: DashboardViewModel = hiltViewModel(),
    onDeviceLimitClick: () -> Unit,
    onAppLimitClick: () -> Unit,
    onBlocklistClick: () -> Unit,
    onNavigationIconClick: () -> Unit
) {
    val ui by viewModel.topAppUiState.collectAsStateWithLifecycle()

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)

    // Precompute any formatting that’s a bit expensive
    val totalTimeText by remember(ui.totalScreenTime) {
        derivedStateOf { ui.totalScreenTime.toDashBoardTotalScreenTimeDisplay() }
    }

    DashboardScreen(
        totalTimeText = totalTimeText,
        apps = ui.topApps,
        onDeviceLimitClick = onDeviceLimitClick,
        onAppLimitClick = onAppLimitClick,
        onBlocklistClick = onBlocklistClick,
        onNavigationIconClick = onNavigationIconClick,
        scrollBehavior = scrollBehavior
    )
}