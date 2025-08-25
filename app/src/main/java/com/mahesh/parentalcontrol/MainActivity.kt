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
import com.mahesh.parentalcontrol.presentation.pin.forgot.ForgotPinOptionsScreen
import com.mahesh.parentalcontrol.presentation.pin.forgot.RecoverPinByCodeScreen
import com.mahesh.parentalcontrol.presentation.pin.forgot.RecoverPinBySecurityQuestionsScreen
import com.mahesh.parentalcontrol.presentation.pin.login.PinLoginScreen
import com.mahesh.parentalcontrol.presentation.pin.register.PinSetupScreen
import com.mahesh.parentalcontrol.presentation.pin.questions.SecurityQuestionsSetupScreen
import com.mahesh.parentalcontrol.presentation.pin.recoverycode.RecoveryCodeScreen
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
    val backStack = remember { mutableStateListOf<Screen>(Screen.PinSetup) }
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { route ->
            when (route) {
                Screen.PinSetup -> NavEntry(route) {
                    PinSetupScreen(
                        onPinAlreadySetup = {
                            backStack.removeLastOrNull()
                            backStack.add(Screen.PinLogin)
                        },
                        onProceedNext = { backStack.add(Screen.SecurityQuestionsSetup) },
                        onNavigationIconClick = {
                            onCloseActivity()
                        }
                    )
                }

                Screen.SecurityQuestionsSetup -> NavEntry(route) {
                    SecurityQuestionsSetupScreen(
                        onProceedNext = { backStack.add(Screen.GenerateRecoveryCode) },
                        onNavigationIconClick = { backStack.removeLastOrNull() }
                    )
                }

                Screen.GenerateRecoveryCode -> NavEntry(route) {
                    RecoveryCodeScreen(
                        onProceedNext = { backStack.add(Screen.PinLogin) },
                        onNavigationIconClick = { backStack.removeLastOrNull() }
                    )
                }

                Screen.PinLogin -> NavEntry(route) {
                    PinLoginScreen(
                        onProceedNext = {
                            backStack.removeLastOrNull()
                            backStack.add(Screen.Dashboard)
                        },
                        onForgotPassword = { backStack.add(Screen.ForgotPinOptions) },
                        onNavigationIconClick = { onCloseActivity() },
                    )
                }

                Screen.ForgotPinOptions -> NavEntry(route) {
                    ForgotPinOptionsScreen(
                        onRecoverWithQuestions = {
                            backStack.add(Screen.RecoverWithQuestions)
                        },
                        onRecoverWithCode = {
                            backStack.add(Screen.RecoverWithCode)
                        },
                        onNavigationIconClick = { backStack.removeLastOrNull() }
                    )
                }

                Screen.RecoverWithQuestions -> NavEntry(route) {
                    RecoverPinBySecurityQuestionsScreen(
                        onProceedNext = { backStack.add(Screen.PinSetup) },
                        onNavigationIconClick = { backStack.removeLastOrNull() }
                    )
                }

                Screen.RecoverWithCode -> NavEntry(route) {
                    RecoverPinByCodeScreen(
                        onProceedNext = { backStack.add(Screen.PinSetup) },
                        onNavigationIconClick = { backStack.removeLastOrNull() }
                    )
                }

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