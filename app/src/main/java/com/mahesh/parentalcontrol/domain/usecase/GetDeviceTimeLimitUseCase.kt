package com.mahesh.parentalcontrol.domain.usecase

import com.mahesh.parentalcontrol.data.local.dao.DeviceTimeLimitDao
import com.mahesh.parentalcontrol.domain.model.DeviceTimeLimit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetDeviceTimeLimitUseCase(private val deviceTimeLimitDao: DeviceTimeLimitDao) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        val timeLimit = deviceTimeLimitDao.getDeviceTimeLimit()
        DeviceTimeLimit(timeLimit?.isTurnedOn ?: false, timeLimit?.timeLimitMillis ?: 0L)
    }
}