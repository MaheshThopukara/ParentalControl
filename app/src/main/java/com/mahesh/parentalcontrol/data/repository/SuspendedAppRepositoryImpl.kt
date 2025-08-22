package com.mahesh.parentalcontrol.data.repository

import android.app.admin.DevicePolicyManager
import android.content.Context
import com.mahesh.parentalcontrol.domain.repository.SuspendedAppRepository

class SuspendedAppRepositoryImpl(private val context: Context) : SuspendedAppRepository {
    override suspend fun suspendApp(packageName: String) {
        val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        // dpm.setPackagesSuspended()
    }

    override suspend fun resumeApp(packageName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getSuspendedApps(): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun isAppSuspended(packageName: String): Boolean {
        TODO("Not yet implemented")
    }
}