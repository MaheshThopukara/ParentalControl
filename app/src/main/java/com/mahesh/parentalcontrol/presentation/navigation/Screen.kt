package com.mahesh.parentalcontrol.presentation.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object DeviceLimit : Screen("device_limit")
    object AppLimit : Screen("app_limit")
    object AppBlocklist : Screen("app_block_list")
}