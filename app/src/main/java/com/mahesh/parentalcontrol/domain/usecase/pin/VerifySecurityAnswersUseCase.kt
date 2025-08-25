package com.mahesh.parentalcontrol.domain.usecase.pin

import com.mahesh.parentalcontrol.domain.repository.PinRepository
import com.mahesh.parentalcontrol.domain.repository.RecoveryCodeRepository
import com.mahesh.parentalcontrol.domain.repository.SecurityQuestionsRepository

class VerifySecurityAnswersUseCase(
    private val repo: SecurityQuestionsRepository,
    private val pinRepo: PinRepository,
    private val recoverRepo: RecoveryCodeRepository
) {
    /**
     * @return true if at least [minCorrect] answers match.
     */
    suspend operator fun invoke(
        answers: Map<String, String>,
        minCorrect: Int = 3
    ): Boolean {
        return if (repo.verifyAnswers(answers) >= minCorrect) {
            pinRepo.deletePin()
            recoverRepo.deleteRecoveryCode()
            true
        } else {
            false
        }
    }
}