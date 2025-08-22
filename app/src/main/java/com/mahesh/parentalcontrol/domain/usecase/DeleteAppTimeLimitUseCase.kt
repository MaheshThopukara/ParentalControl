package com.mahesh.parentalcontrol.domain.usecase

import com.mahesh.parentalcontrol.data.local.dao.AppLimitDao
import com.mahesh.parentalcontrol.data.local.entity.AppLimitEntity
import com.mahesh.parentalcontrol.domain.model.AppUsage
import com.mahesh.parentalcontrol.domain.repository.AppUsageRepository
import com.mahesh.parentalcontrol.domain.repository.SuspendedAppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteAppTimeLimitUseCase(
    private val appUsageRepository: AppUsageRepository,
    private val suspendedAppRepository: SuspendedAppRepository,
    private val appLimitDao: AppLimitDao
) {

    suspend operator fun invoke(packageName: String) =
        withContext(Dispatchers.IO) {
            val limit = appLimitDao.getLimit(packageName) ?: return@withContext
            appLimitDao.deleteLimit(limit)
            appUsageRepository.unRegisterAppUsageObserver(limit.observerId)
            suspendedAppRepository.resumeApp(packageName)
        }
}