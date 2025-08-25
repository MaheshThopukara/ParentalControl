package com.mahesh.parentalcontrol.domain.usecase.pin

import com.mahesh.parentalcontrol.domain.repository.SecurityQuestionsRepository

class SaveSecurityAnswersUseCase(
    private val repo: SecurityQuestionsRepository
) {
    suspend operator fun invoke(answers: Map<String, String>) = repo.saveAnswers(answers)
}