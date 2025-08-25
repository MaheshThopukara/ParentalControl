package com.mahesh.parentalcontrol.data.repository

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mahesh.parentalcontrol.core.security.PinCrypto
import com.mahesh.parentalcontrol.domain.repository.RecoveryCodeRepository
import kotlinx.coroutines.flow.first

class RecoveryCodeRepositoryImpl(private val context: Context) : RecoveryCodeRepository {

    private val ds get() = context.recoveryDataStore

    override suspend fun saveHashed(codePlain: String) {
        val h = PinCrypto.hash(codePlain)
        storeRecovery(ds, h.hashB64, h.saltB64, h.iterations)
    }

    override suspend fun markShownOnce() = markShown(ds)

    override suspend fun isRecoveryCodeShownOnce(): Boolean {
        return isRecoveryCodeShownOnce(ds)
    }

    override suspend fun alreadyExists(): Boolean = hasRecovery(ds)

    override suspend fun verify(input: String): Boolean {
        val prefs = ds.data.first()
        val hash = prefs[stringPreferencesKey("recovery_hash")] ?: return false
        val salt = prefs[stringPreferencesKey("recovery_salt")] ?: return false
        val iter = prefs[stringPreferencesKey("recovery_iter")]?.toIntOrNull() ?: 10000
        return PinCrypto.verify(input, PinCrypto.PinHash(hash, salt, iter))
    }

    override suspend fun deleteRecoveryCode() {
        deleteRecoveryCode(ds)
    }
}