package com.mahesh.parentalcontrol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.mahesh.parentalcontrol.presentation.Route
import com.mahesh.parentalcontrol.presentation.appblock.AppBlockListRoute
import com.mahesh.parentalcontrol.presentation.apptime.AppTimeLimitRoute
import com.mahesh.parentalcontrol.presentation.dashboard.DashboardRoute
import com.mahesh.parentalcontrol.presentation.devicetime.DeviceTimeLimitRoute
import com.mahesh.parentalcontrol.presentation.pin.forgot.ForgotPinOptionsScreen
import com.mahesh.parentalcontrol.presentation.pin.forgot.RecoverByCodeRoute
import com.mahesh.parentalcontrol.presentation.pin.forgot.RecoverByQuestionsRoute
import com.mahesh.parentalcontrol.presentation.pin.login.PinLoginRoute
import com.mahesh.parentalcontrol.presentation.pin.questions.SecurityQuestionsSetupRoute
import com.mahesh.parentalcontrol.presentation.pin.recoverycode.RecoveryCodeRoute
import com.mahesh.parentalcontrol.presentation.pin.register.RegisterPinRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val state = vm.authState.collectAsState()
            MaterialTheme {
                if (state.value.hasLoginDetails != null) {
                    AppNav(state.value.hasLoginDetails == true) {
                        finish()
                    }
                }
            }
        }
    }
}

@Composable
fun AppNav(
    hasLoginDetails: Boolean,
    finish: () -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.AuthGraph.value,
    ) {
        // Auth flow
        navigation(
            startDestination = if (hasLoginDetails) Route.EnterLogin.value else Route.Register.value,
            route = Route.AuthGraph.value
        ) {
            // LOGIN
            composable(Route.EnterLogin.value) {
                PinLoginRoute(
                    onLoginSuccess = {
                        navController.navigate(Route.MainGraph.value) {
                            popUpTo(Route.AuthGraph.value) {
                                inclusive = true
                            }
                        }
                    },
                    onForgotPassword = {
                        navController.navigate(Route.RecoveryOptions.value)
                    },
                    onNavigationIconClick = { finish() }
                )
            }

            // REGISTER first-time flow
            composable(Route.Register.value) {
                RegisterPinRoute(
                    onContinue = {
                        navController.navigate(Route.SecurityQuestions.value)
                    },
                    onNavigationIconClick = { finish() }
                )
            }
            composable(Route.SecurityQuestions.value) {
                SecurityQuestionsSetupRoute(
                    onContinue = {
                        navController.navigate(Route.RecoveryPassword.value)
                    },
                    onNavigationIconClick = { navController.popBackStack() }
                )
            }
            composable(Route.RecoveryPassword.value) {
                RecoveryCodeRoute(
                    onContinue = {
                        navController.navigate(Route.EnterLogin.value) {
                            popUpTo(Route.AuthGraph.value) {
                                inclusive = false
                            }
                        }
                    },
                    onNavigationIconClick = { navController.popBackStack() }
                )
            }

            // RECOVERY flow
            composable(Route.RecoveryOptions.value) {
                ForgotPinOptionsScreen(
                    onRecoverWithQuestions = {
                        navController.navigate(Route.RecoverBySecurityQuestions.value)
                    },
                    onRecoverWithCode = {
                        navController.navigate(Route.RecoverByCode.value)
                    },
                    onNavigationIconClick = { navController.popBackStack() }
                )
            }
            composable(Route.RecoverBySecurityQuestions.value) {
                RecoverByQuestionsRoute(
                    onContinue = {
                        navController.navigate(Route.Register.value) {
                            popUpTo(Route.AuthGraph.value) {
                                inclusive = false
                            }
                        }
                    },
                    onNavigationIconClick = { navController.popBackStack() }
                )
            }
            composable(Route.RecoverByCode.value) {
                RecoverByCodeRoute(
                    onContinue = {
                        navController.navigate(Route.Register.value) {
                            popUpTo(Route.AuthGraph.value) {
                                inclusive = false
                            }
                        }
                    },
                    onNavigationIconClick = { navController.popBackStack() }
                )
            }

        }

        // Main flow
        navigation(
            startDestination = Route.Dashboard.value,
            route = Route.MainGraph.value
        ) {
            composable(Route.Dashboard.value) {
                DashboardRoute(
                    onDeviceLimitClick = {
                        navController.navigate(Route.DeviceLimit.value)
                    },
                    onAppLimitClick = {
                        navController.navigate(Route.AppLimit.value)
                    },
                    onBlocklistClick = {
                        navController.navigate(Route.AppBlocklist.value)
                    },
                    onNavigationIconClick = { finish() }
                )
            }
            composable(Route.DeviceLimit.value) {
                DeviceTimeLimitRoute(
                    onNavigationIconClick = { navController.popBackStack() }
                )
            }
            composable(Route.AppLimit.value) {
                AppTimeLimitRoute(
                    onNavigationIconClick = { navController.popBackStack() }
                )
            }
            composable(Route.AppBlocklist.value) {
                AppBlockListRoute(
                    onNavigationIconClick = { navController.popBackStack() }
                )
            }
        }
    }
}