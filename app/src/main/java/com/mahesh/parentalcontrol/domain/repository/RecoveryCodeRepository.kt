package com.mahesh.parentalcontrol.domain.repository

interface RecoveryCodeRepository {
    suspend fun saveHashed(codePlain: String)
    suspend fun markShownOnce()
    suspend fun isRecoveryCodeShownOnce(): Boolean
    suspend fun alreadyExists(): Boolean
    suspend fun verify(input: String): Boolean
    suspend fun deleteRecoveryCode()
}