package com.mahesh.parentalcontrol.domain.usecase

import com.mahesh.parentalcontrol.data.local.dao.AppBlockListDao
import com.mahesh.parentalcontrol.data.local.dao.AppLimitDao
import com.mahesh.parentalcontrol.data.local.entity.BlockedAppEntity
import com.mahesh.parentalcontrol.domain.model.AppBlockList
import com.mahesh.parentalcontrol.domain.repository.AppUsageRepository
import com.mahesh.parentalcontrol.domain.repository.SuspendedAppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteAppBlockListUseCase(
    private val suspendedAppRepository: SuspendedAppRepository,
    private val appBlockListDao: AppBlockListDao
) {
    suspend operator fun invoke(packageName: String) = withContext(Dispatchers.IO) {
        appBlockListDao.deleteBlocked(BlockedAppEntity(packageName))
        if (appBlockListDao.isBlockListEnabled()) {
            suspendedAppRepository.resumeApp(packageName)
        }
    }
}