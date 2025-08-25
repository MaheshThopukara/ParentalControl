package com.mahesh.parentalcontrol.presentation.appblock

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mahesh.parentalcontrol.domain.model.AppBlockList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBlockListScreen(
    isEnabled: Boolean,
    items: List<AppBlockList>,
    onToggleEnabled: (Boolean) -> Unit,
    onToggleItem: (packageName: String, checked: Boolean) -> Unit,
    onNavigationIconClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text("App Block Listing") },
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
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item("toggle") {
                val btnLabel by remember(isEnabled) {
                    mutableStateOf(if (isEnabled) "Turn Off now" else "Turn On now")
                }
                FilledTonalButton(
                    onClick = { onToggleEnabled(!isEnabled) },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) { Text(btnLabel) }
            }

            items(
                items = items,
                key = { it.packageName } // stable key avoids unnecessary recompositions
            ) { app ->
                AppBlockListRow(
                    appName = app.appName,
                    packageName = app.packageName,
                    icon = app.icon,
                    checked = app.isBlockListed,
                    onCheckedChange = { checked -> onToggleItem(app.packageName, checked) }
                )
                Divider()
            }
        }
    }
}

// ---------- Row (stateless leaf) ----------
@Composable
private fun AppBlockListRow(
    appName: String,
    packageName: String,
    icon: Any, // replace with your actual type for image source
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(icon),
            contentDescription = appName,
            modifier = Modifier.size(40.dp)
        )

        Spacer(Modifier.width(12.dp))

        Column(Modifier.weight(1f)) {
            Text(text = appName, fontWeight = FontWeight.Bold)
            // Optional helper text:
            // Text(text = packageName, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}