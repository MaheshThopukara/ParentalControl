package com.mahesh.parentalcontrol.domain.usecase

import com.mahesh.parentalcontrol.domain.repository.SuspendedAppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SuspendAppUseCase(
    private val suspendedAppRepository: SuspendedAppRepository
) {
    suspend operator fun invoke(packageName: String) = withContext(Dispatchers.IO) {
        suspendedAppRepository.suspendApp(packageName)
    }
}