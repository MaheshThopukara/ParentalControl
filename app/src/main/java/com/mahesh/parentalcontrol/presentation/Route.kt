package com.mahesh.parentalcontrol.presentation

sealed interface Route {
    val value: String

    // Root-level graphs
    data object AuthGraph : Route { override val value = "auth" }
    data object MainGraph : Route { override val value = "main" }

    // Auth destinations
    data object EnterLogin : Route { override val value = "enter_login" }
    data object Register : Route { override val value = "register" }
    data object SecurityQuestions : Route { override val value = "security_questions" }
    data object RecoveryPassword : Route { override val value = "recovery_password" }
    data object ForgotPassword : Route { override val value = "forgot_password" }
    data object RecoveryOptions : Route { override val value = "recovery_options" }
    data object RecoverBySecurityQuestions : Route { override val value = "recover_by_security_questions" }
    data object RecoverByCode : Route { override val value = "recover_by_code" }

    // Main destinations
    data object Dashboard : Route { override val value = "dashboard" }
    data object AppBlocklist : Route { override val value = "app_blocklist" }
    data object AppLimit : Route { override val value = "app_limit" }
    data object DeviceLimit : Route { override val value = "device_limit" }
}