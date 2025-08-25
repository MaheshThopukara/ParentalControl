package com.mahesh.parentalcontrol.domain.usecase.pin

import com.mahesh.parentalcontrol.domain.repository.RecoveryCodeRepository

class MarkRecoveryCodeShownUseCase(
    private val repo: RecoveryCodeRepository
) {
    suspend operator fun invoke() = repo.markShownOnce()
}