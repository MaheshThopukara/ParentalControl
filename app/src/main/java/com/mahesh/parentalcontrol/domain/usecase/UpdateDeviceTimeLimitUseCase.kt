package com.mahesh.parentalcontrol.domain.usecase

import com.mahesh.parentalcontrol.data.local.dao.DeviceTimeLimitDao
import com.mahesh.parentalcontrol.data.local.entity.DeviceTimeLimitEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateDeviceTimeLimitUseCase(private val deviceTimeLimitDao: DeviceTimeLimitDao) {
    suspend operator fun invoke(isTurnedOn: Boolean, timeLimitMillis: Long) =
        withContext(Dispatchers.IO) {
            deviceTimeLimitDao.upsertDeviceTimeLimit(
                DeviceTimeLimitEntity(
                    0,
                    isTurnedOn,
                    timeLimitMillis
                )
            )

            // TODO: Start monitoring device time usage
        }
}