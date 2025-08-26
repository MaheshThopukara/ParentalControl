package com.mahesh.parentalcontrol.domain.usecase

import com.mahesh.parentalcontrol.data.local.dao.AppBlockListDao
import com.mahesh.parentalcontrol.data.local.dao.AppLimitDao
import com.mahesh.parentalcontrol.domain.repository.AppUsageRepository
import com.mahesh.parentalcontrol.domain.repository.SuspendedAppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MidnightRolloverUseCase(
    private val appUsageRepository: AppUsageRepository,
    private val suspendedAppRepository: SuspendedAppRepository,
    private val appLimitDao: AppLimitDao,
    private val appBlockListDao: AppBlockListDao
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        val isBlockListEnabled = appBlockListDao.isBlockListEnabled()
        val blockListPkgs = appBlockListDao.getAllAppBlockList().map { it.pkg }

        val appTimeLimits = appLimitDao.getAllLimits()
        appTimeLimits.forEach { limit ->
            if (!blockListPkgs.contains(limit.packageName) && !isBlockListEnabled) {
                suspendedAppRepository.resumeApp(packageName = limit.packageName)
            }
            appUsageRepository.unRegisterAppUsageObserver(observerId = limit.observerId)
            appUsageRepository.registerAppUsageObserver(
                limit.packageName,
                limit.observerId,
                limit.dailyLimitMillis ?: 0L
            )
        }
    }
}