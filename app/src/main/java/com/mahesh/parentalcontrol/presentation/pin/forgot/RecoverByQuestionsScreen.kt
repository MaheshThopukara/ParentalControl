package com.mahesh.parentalcontrol.presentation.pin.forgot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mahesh.parentalcontrol.domain.model.SecurityQuestion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoverByQuestionsScreen(
    minRequired: Int,
    questions: List<SecurityQuestion>,              // data class with stable key field
    answers: Map<String, String>,
    isSubmitting: Boolean,
    error: String?,
    onAnswerChanged: (key: String, value: String) -> Unit,
    onSubmit: () -> Unit,
    onNavigationIconClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text("Recover with Security Questions") },
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
            item("intro") {
                Text(
                    "Answer at least $minRequired questions correctly to reset your PIN.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            items(
                items = questions,
                key = { it.key } // keeps rows stable; unchanged ones skip recomposition
            ) { q ->
                Column(Modifier.fillMaxWidth()) {
                    Text(q.text, style = MaterialTheme.typography.titleSmall)
                    OutlinedTextField(
                        value = answers[q.key].orEmpty(),
                        onValueChange = { onAnswerChanged(q.key, it) },
                        singleLine = true,
                        placeholder = { Text("Enter answer") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction =
                                if (q == questions.last()) ImeAction.Done else ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) },
                            onDone = {
                                if (!isSubmitting) {
                                    focusManager.clearFocus(force = true)
                                    onSubmit()
                                }
                            }
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            item("error") {
                error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            }

            item("submit") {
                Button(
                    onClick = {
                        focusManager.clearFocus(force = true)
                        onSubmit()
                    },
                    enabled = !isSubmitting,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            modifier = Modifier
                                .size(18.dp)
                                .padding(end = 8.dp)
                        )
                    }
                    Text("Verify & Continue")
                }
            }

            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}