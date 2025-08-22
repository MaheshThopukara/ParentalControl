package com.mahesh.parentalcontrol.presentation.appblock

import androidx.compose.foundation.Image
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.mahesh.parentalcontrol.domain.model.AppBlockList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBlockListScreen(
    viewModel: AppBlockListViewModel = hiltViewModel(),
    onNavigationIconClick: () -> Unit
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val appBlockListings by viewModel.appBlockListUiState.collectAsState()
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "App Block Listing"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigationIconClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
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
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            item {
                FilledTonalButton(
                    modifier = Modifier
                        .padding(16.dp),
                    onClick = {
                        viewModel.setBlockListEnabled(!appBlockListings.isBlockListEnabled)
                    }
                ) {
                    Text(
                        text = if (appBlockListings.isBlockListEnabled) "Turn Off now" else "Turn On now",
                    )
                }
            }


            items(appBlockListings.appBlockList) { blockList ->
                AppBlockListItem(blockList) { appBlockList ->
                    if (appBlockList.isBlockListed) {
                        viewModel.deleteFromBlockList(appBlockList.packageName)
                    } else {
                        viewModel.addToBlockList(appBlockList.packageName)
                    }
                }
            }
        }
    }
}

@Composable
fun AppBlockListItem(appBlockList: AppBlockList, onItemClick: (AppBlockList) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(appBlockList.icon),
            contentDescription = appBlockList.appName,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = appBlockList.appName,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f) // take up remaining space
        )


        Checkbox(
            checked = appBlockList.isBlockListed,
            onCheckedChange = { onItemClick(appBlockList) }
        )
    }
}
