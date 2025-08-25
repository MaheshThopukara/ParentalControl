package com.mahesh.parentalcontrol.presentation.pin.recoverycode

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoveryCodeRoute(
    viewModel: RecoveryCodeViewModel = hiltViewModel(),
    onContinue: () -> Unit,
    onNavigationIconClick: () -> Unit
) {
    val state by viewModel.ui.collectAsStateWithLifecycle()

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)
    val scrollState = rememberScrollState()

    // One-shot navigation guard
    var navigated by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(state.`continue`) {
        if (state.`continue` && !navigated) {
            navigated = true
            onContinue()
        }
    }

    RecoveryCodeScreen(
        isLoading = state.isLoading,
        code = state.code,
        error = state.error,
        onCopy = {  },            // optional VM hook if you want analytics
        onAcknowledge = viewModel::markShown,
        onNavigationIconClick = onNavigationIconClick,
        scrollBehavior = scrollBehavior,
        scrollState = scrollState
    )
}