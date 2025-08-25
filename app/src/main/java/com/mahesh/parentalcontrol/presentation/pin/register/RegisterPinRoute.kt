package com.mahesh.parentalcontrol.presentation.pin.register

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
fun RegisterPinRoute(
    viewModel: RegisterPinViewModel = hiltViewModel(),
    onContinue: () -> Unit,
    onNavigationIconClick: () -> Unit
) {
    val state by viewModel.ui.collectAsStateWithLifecycle()

    // Keep these stable
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)
    val scrollState = rememberScrollState()

    // Fire navigation exactly once even if state re-emits
    var navigated by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(state.`continue`) {
        if (state.`continue` && !navigated) {
            navigated = true
            onContinue()
        }
    }

    RegisterPinScreen(
        state = state,
        onPinChanged = viewModel::onPinChanged,
        onConfirmChanged = viewModel::onConfirmChanged,
        onSaveClicked = viewModel::onSaveClicked,
        onNavigationIconClick = onNavigationIconClick,
        scrollBehavior = scrollBehavior,
        scrollState = scrollState
    )
}