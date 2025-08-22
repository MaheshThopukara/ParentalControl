package com.mahesh.parentalcontrol.domain.usecase

import com.mahesh.parentalcontrol.data.local.dao.AppBlockListDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAppBlockSettingUseCase(private val appBlockListDao: AppBlockListDao) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        appBlockListDao.isBlockListEnabled()
    }
}