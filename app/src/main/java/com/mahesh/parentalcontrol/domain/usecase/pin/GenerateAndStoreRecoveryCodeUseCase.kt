package com.mahesh.parentalcontrol.domain.usecase.pin

import com.mahesh.parentalcontrol.domain.repository.RecoveryCodeRepository

class GenerateAndStoreRecoveryCodeUseCase(
    private val repo: RecoveryCodeRepository
) {
    /** Generates code, stores HASH ONLY, returns the plain code to show once. */
    suspend operator fun invoke(): String {
        val code = generateCode()
        repo.saveHashed(code)
        return code
    }

    private fun generateCode(): String {
        // Example: AB12-CD34-EF56 (omit ambiguous chars)
        val alphabet = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"
        fun block() = (1..4).map { alphabet.random() }.joinToString("")
        return "${block()}-${block()}-${block()}"
    }
}