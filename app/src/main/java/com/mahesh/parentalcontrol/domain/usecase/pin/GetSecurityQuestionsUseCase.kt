package com.mahesh.parentalcontrol.domain.usecase.pin

import com.mahesh.parentalcontrol.domain.model.SecurityQuestion
import com.mahesh.parentalcontrol.domain.repository.SecurityQuestionsRepository

class GetSecurityQuestionsUseCase(
    private val repo: SecurityQuestionsRepository
) {
    suspend operator fun invoke(): List<SecurityQuestion> = repo.getPredefinedQuestions()
}