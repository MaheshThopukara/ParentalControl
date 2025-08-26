package com.mahesh.parentalcontrol.domain.usecase

import com.mahesh.parentalcontrol.data.local.dao.AppBlockListDao
import com.mahesh.parentalcontrol.domain.model.AppBlockList
import com.mahesh.parentalcontrol.domain.repository.AppUsageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAppBlockListUseCase(
    private val repository: AppUsageRepository,
    private val appBlockListDao: AppBlockListDao
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        val usageList = repository.getAppUsage()
        val appBlockList = appBlockListDao.getAllAppBlockList()
        usageList.map { usage ->
            val isBlockListed = appBlockList.any { it.pkg == usage.packageName }
            AppBlockList(usage.packageName, usage.appName, usage.icon, isBlockListed)
        }
    }
}