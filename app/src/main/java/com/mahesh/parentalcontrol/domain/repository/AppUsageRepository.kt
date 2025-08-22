package com.mahesh.parentalcontrol.domain.repository

import com.mahesh.parentalcontrol.domain.model.AppUsage

interface AppUsageRepository {
    suspend fun getAppUsage(): List<AppUsage>

    suspend fun registerAppUsageObserver(
        packageName: String,
        observerId: Long,
        timeLimitMillis: Long
    )

    suspend fun unRegisterAppUsageObserver(observerId: Long)
}