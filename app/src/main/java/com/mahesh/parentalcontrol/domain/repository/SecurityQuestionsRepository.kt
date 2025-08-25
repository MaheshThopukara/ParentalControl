package com.mahesh.parentalcontrol.domain.repository

import com.mahesh.parentalcontrol.domain.model.SecurityQuestion

interface SecurityQuestionsRepository {
    suspend fun getPredefinedQuestions(): List<SecurityQuestion>

    /** Save hashed answers (per question) */
    suspend fun saveAnswers(answers: Map<String, String>)

    /**
     * Verify answers; returns how many answers matched (case-insensitive trim).
     * Useful later for Forgot PIN flow (need >= 3 correct).
     */
    suspend fun verifyAnswers(answers: Map<String, String>): Int
}