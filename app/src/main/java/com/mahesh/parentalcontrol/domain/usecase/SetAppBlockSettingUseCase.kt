package com.mahesh.parentalcontrol.domain.usecase

import com.mahesh.parentalcontrol.data.local.dao.AppBlockListDao
import com.mahesh.parentalcontrol.data.local.entity.AppBlockSettingEntity
import com.mahesh.parentalcontrol.domain.repository.SuspendedAppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SetAppBlockSettingUseCase(
    private val suspendedAppRepository: SuspendedAppRepository,
    private val appBlockListDao: AppBlockListDao
) {
    suspend operator fun invoke(isBlocked: Boolean) = withContext(Dispatchers.IO) {
        appBlockListDao.upsertSettings(AppBlockSettingEntity(0, isBlocked))
        val suspendedApps = appBlockListDao.getAllAppBlockList()
        suspendedApps.forEach { appBlockListDao ->
            if (isBlocked) {
                suspendedAppRepository.suspendApp(appBlockListDao.pkg)
            } else {
                suspendedAppRepository.resumeApp(appBlockListDao.pkg)
            }

        }

    }
}