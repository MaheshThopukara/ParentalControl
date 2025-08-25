package com.mahesh.parentalcontrol.presentation.pin.questions

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
fun SecurityQuestionsSetupRoute(
    viewModel: SecurityQuestionsSetupViewModel = hiltViewModel(),
    onContinue: () -> Unit,
    onNavigationIconClick: () -> Unit
) {
    val state by viewModel.ui.collectAsStateWithLifecycle()

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)

    // Fire navigation exactly once
    var navigated by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(state.`continue`) {
        if (state.`continue` && !navigated) {
            navigated = true
            onContinue()
        }
    }

    SecurityQuestionsSetupScreen(
        state = state,
        onAnswerChanged = viewModel::onAnswerChanged,
        onSave = viewModel::onSave,
        onNavigationIconClick = onNavigationIconClick,
        scrollBehavior = scrollBehavior
    )
}