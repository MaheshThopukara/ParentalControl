package com.mahesh.parentalcontrol.presentation.pin.forgot

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoverByCodeRoute(
    viewModel: RecoverByCodeViewModel = hiltViewModel(),
    onContinue: () -> Unit,
    onNavigationIconClick: () -> Unit
) {
    val uiState by viewModel.ui.collectAsStateWithLifecycle()

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)
    val scrollState = rememberScrollState()

    // One-shot navigation
    var navigated by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(uiState.proceedNext) {
        if (uiState.proceedNext && !navigated) {
            navigated = true
            onContinue()
        }
    }

    RecoverByCodeScreen(
        code = uiState.code,
        isSubmitting = uiState.isSubmitting,
        error = uiState.error,
        onCodeChanged = viewModel::onCodeChanged,
        onSubmit = viewModel::onSubmit,
        onNavigationIconClick = onNavigationIconClick,
        scrollBehavior = scrollBehavior,
        scrollState = scrollState
    )
}