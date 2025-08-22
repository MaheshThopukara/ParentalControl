package com.mahesh.parentalcontrol.data.repository

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.mahesh.parentalcontrol.domain.model.AppUsage
import com.mahesh.parentalcontrol.domain.repository.AppUsageRepository
import java.util.Calendar

class AppUsageRepositoryImpl(private val context: Context) : AppUsageRepository {

    override suspend fun getAppUsage(): List<AppUsage> {
        val pm = context.packageManager
        val installedApps = getAllInstalledApps()
        val usageStats = getTodayAppUsageStats().associateBy { it.packageName }
        return installedApps.map { appInfo ->
            val usage = usageStats[appInfo.packageName]
            AppUsage(
                packageName = appInfo.packageName,
                appName = pm.getApplicationLabel(appInfo).toString(),
                icon = pm.getApplicationIcon(appInfo),
                screenTimeMillis = usage?.totalTimeInForeground ?: 0L,
                dailyLimitMillis = 0
            )
        }.sortedByDescending { it.screenTimeMillis }
    }

    override suspend fun registerAppUsageObserver(
        packageName: String,
        observerId: Long,
        timeLimitMillis: Long
    ) {
        val usageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        // TODO: Register observer
        // usageStatsManager.registerAppUsageObserver()
    }

    override suspend fun unRegisterAppUsageObserver(observerId: Long) {
        val usageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        // usageStatsManager.registerAppUsageObserver()
    }

    private fun getAllInstalledApps(): List<ApplicationInfo> {
        val pm = context.packageManager
        return pm.getInstalledApplications(PackageManager.GET_META_DATA).filter { appInfo ->
            pm.getLaunchIntentForPackage(appInfo.packageName) != null
        }
    }

    private fun getTodayAppUsageStats(): List<UsageStats> {
        val usageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val startTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis
        val endTime = System.currentTimeMillis()
        return usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )
    }
}