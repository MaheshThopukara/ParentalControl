package com.mahesh.parentalcontrol.domain.repository

interface PinRepository {
    suspend fun savePin(pin: String)
    suspend fun validatePin(pin: String): Boolean
    suspend fun deletePin()
}