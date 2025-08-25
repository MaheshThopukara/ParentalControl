package com.mahesh.parentalcontrol.domain.usecase.pin

import com.mahesh.parentalcontrol.domain.repository.PinRepository
import com.mahesh.parentalcontrol.domain.repository.RecoveryCodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VerifyRecoveryCodeUseCase(
    private val repo: RecoveryCodeRepository,
    private val pinRepo: PinRepository,
    private val recoverRepo: RecoveryCodeRepository
) {
    suspend operator fun invoke(input: String): Boolean {
        return withContext(Dispatchers.IO) {
            if (repo.verify(input)) {
                pinRepo.deletePin()
                recoverRepo.deleteRecoveryCode()
                true
            } else {
                false
            }
        }
    }
}