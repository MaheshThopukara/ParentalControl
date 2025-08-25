package com.mahesh.parentalcontrol.presentation.appblock

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBlockListRoute(
    viewModel: AppBlockListViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit
) {
    val ui by viewModel.appBlockListUiState.collectAsStateWithLifecycle()

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)

    AppBlockListScreen(
        isEnabled = ui.isBlockListEnabled,
        items = ui.appBlockList,
        onToggleEnabled = { enable -> viewModel.setBlockListEnabled(enable) },
        onToggleItem = { pkg, checked ->
            if (checked) viewModel.addToBlockList(pkg) else viewModel.deleteFromBlockList(pkg)
        },
        onNavigationIconClick = onNavigationIconClick,
        scrollBehavior = scrollBehavior
    )
}