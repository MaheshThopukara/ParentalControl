package com.mahesh.parentalcontrol.presentation.navigation

sealed class Screen(val route: String) {
    object PinLogin : Screen("pin_login")
    object PinSetup : Screen("pin_setup")
    object SecurityQuestionsSetup: Screen("security_questions_setup")

    object GenerateRecoveryCode: Screen("generate_recovery_code")
    object ForgotPinOptions: Screen("forgot_pin_options")
    object RecoverWithQuestions: Screen("recover_with_questions")
    object RecoverWithCode: Screen("recover_with_code")
    object Dashboard : Screen("dashboard")
    object DeviceLimit : Screen("device_limit")
    object AppLimit : Screen("app_limit")
    object AppBlocklist : Screen("app_block_list")
}