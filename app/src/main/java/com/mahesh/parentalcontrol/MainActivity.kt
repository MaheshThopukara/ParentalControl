package com.mahesh.parentalcontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.mahesh.parentalcontrol.presentation.appblock.AppBlockListScreen
import com.mahesh.parentalcontrol.presentation.apptime.AppUsageScreen
import com.mahesh.parentalcontrol.presentation.dashboard.DashboardScreen
import com.mahesh.parentalcontrol.presentation.devicetime.DeviceTimeLimitScreen
import com.mahesh.parentalcontrol.presentation.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                AppNavGraph() {
                    finish()
                }
            }
        }
    }
}

@Composable
fun AppNavGraph(onCloseActivity: () -> Unit) {
    val backStack = remember { mutableStateListOf<Screen>(Screen.Dashboard) }
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { route ->
            when (route) {
                Screen.Dashboard -> NavEntry(route) {
                    DashboardScreen(
                        onDeviceLimitClick = {
                            backStack.add(Screen.DeviceLimit)
                        },
                        onAppLimitClick = {
                            backStack.add(Screen.AppLimit)
                        },
                        onBlocklistClick = {
                            backStack.add(Screen.AppBlocklist)
                        },
                        onNavigationIconClick = {
                            onCloseActivity()
                        }
                    )
                }

                Screen.AppBlocklist -> NavEntry(route) {
                    AppBlockListScreen(
                        onNavigationIconClick = { backStack.removeLastOrNull() }
                    )
                }
                Screen.AppLimit -> NavEntry(route) {
                    AppUsageScreen(
                        onNavigationIconClick = { backStack.removeLastOrNull() }
                    )
                }

                Screen.DeviceLimit -> NavEntry(route) {
                    DeviceTimeLimitScreen(
                        onNavigationIconClick = { backStack.removeLastOrNull() }
                    )
                }
            }
        }
    )
}