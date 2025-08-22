package com.mahesh.parentalcontrol.domain.usecase

import com.mahesh.parentalcontrol.data.local.dao.AppLimitDao
import com.mahesh.parentalcontrol.data.local.entity.AppLimitEntity
import com.mahesh.parentalcontrol.domain.model.AppUsage
import com.mahesh.parentalcontrol.domain.repository.AppUsageRepository
import com.mahesh.parentalcontrol.domain.repository.SuspendedAppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddAppTimeLimitUseCase(
    private val appUsageRepository: AppUsageRepository,
    private val appLimitDao: AppLimitDao
) {

    suspend operator fun invoke(packageName: String, dailyLimitMillis: Long) =
        withContext(Dispatchers.IO) {
            val observerId = packageName.hashCode().toLong()
            appLimitDao.upsertLimit(AppLimitEntity(packageName, dailyLimitMillis, observerId))
            appUsageRepository.registerAppUsageObserver(packageName, observerId, dailyLimitMillis)
        }
}