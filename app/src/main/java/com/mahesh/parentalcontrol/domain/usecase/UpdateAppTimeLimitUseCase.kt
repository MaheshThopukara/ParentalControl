package com.mahesh.parentalcontrol.domain.usecase

import com.mahesh.parentalcontrol.data.local.dao.AppLimitDao
import com.mahesh.parentalcontrol.domain.repository.AppUsageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateAppTimeLimitUseCase(
    private val appUsageRepository: AppUsageRepository,
    private val appLimitDao: AppLimitDao
) {

    suspend operator fun invoke(packageName: String, dailyLimitMillis: Long) =
        withContext(Dispatchers.IO) {
            val limit = appLimitDao.getLimit(packageName) ?: return@withContext
            appLimitDao.upsertLimit(limit.copy(dailyLimitMillis = dailyLimitMillis))
            appUsageRepository.registerAppUsageObserver(packageName, limit.observerId, dailyLimitMillis)
        }
}