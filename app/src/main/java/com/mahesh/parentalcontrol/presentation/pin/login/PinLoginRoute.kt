package com.mahesh.parentalcontrol.presentation.pin.login

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
fun PinLoginRoute(
    viewModel: PinLoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onForgotPassword: () -> Unit,
    onNavigationIconClick: () -> Unit
) {
    // Lifecycle-aware collection helps avoid extra emissions when not in RESUMED state
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Keep these stable across recompositions
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val scrollState = rememberScrollState()

    // One-shot navigation guard
    var navigated by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(uiState.success) {
        if (uiState.success && !navigated) {
            navigated = true
            onLoginSuccess()
        }
    }

    PinLoginScreen(
        uiState = uiState,
        onPinChange = viewModel::onPinChange,
        onSubmit = viewModel::onSubmitPin,
        onForgotPassword = onForgotPassword,
        onNavigationIconClick = onNavigationIconClick,
        scrollBehavior = scrollBehavior,
        scrollState = scrollState
    )
}