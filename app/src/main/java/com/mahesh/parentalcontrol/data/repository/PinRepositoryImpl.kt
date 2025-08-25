package com.mahesh.parentalcontrol.data.repository

import android.content.Context
import com.mahesh.parentalcontrol.core.security.PinCrypto
import com.mahesh.parentalcontrol.domain.repository.PinRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

class PinRepositoryImpl(private val context: Context) : PinRepository {

    private val ds get() = context.pinDataStore

    override suspend fun savePin(pin: String) {
        val hashed = PinCrypto.hash(pin)
        savePinPrefs(
            ds = ds,
            hashB64 = hashed.hashB64,
            saltB64 = hashed.saltB64,
            iterations = hashed.iterations
        )
    }


    override suspend fun validatePin(pin: String): Boolean {
        val prefs = ds.data.first()
        val hash = prefs[PinKeys.HASH] ?: return false
        val salt = prefs[PinKeys.SALT] ?: return false
        val iter = prefs[PinKeys.ITER]?.toIntOrNull() ?: return false
        val crypto = PinCrypto.PinHash(hash, salt, iter)
        return PinCrypto.verify(pin, crypto)
    }

    override suspend fun deletePin() {
        deletePin(ds)
    }
}
