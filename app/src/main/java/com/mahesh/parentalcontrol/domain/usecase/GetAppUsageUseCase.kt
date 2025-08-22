package com.mahesh.parentalcontrol.domain.usecase

import com.mahesh.parentalcontrol.data.local.dao.AppLimitDao
import com.mahesh.parentalcontrol.domain.repository.AppUsageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAppUsageUseCase(
    private val repository: AppUsageRepository,
    private val limitDao: AppLimitDao
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        val usageList = repository.getAppUsage()
        val limits = limitDao.getAllLimits().associateBy { it.packageName }
        usageList.map { usage ->
            val limit = limits[usage.packageName]
            usage.copy(dailyLimitMillis = limit?.dailyLimitMillis)
        }.sortedByDescending { it.screenTimeMillis }
    }
}