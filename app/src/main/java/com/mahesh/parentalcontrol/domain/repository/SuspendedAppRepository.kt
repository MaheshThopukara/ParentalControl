package com.mahesh.parentalcontrol.domain.repository

interface SuspendedAppRepository {
    suspend fun suspendApp(packageName: String)
    suspend fun resumeApp(packageName: String)
    suspend fun getSuspendedApps(): List<String>
    suspend fun isAppSuspended(packageName: String): Boolean
}