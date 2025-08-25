package com.mahesh.parentalcontrol.core.security

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object PinCrypto {
    data class PinHash(val hashB64: String, val saltB64: String, val iterations: Int)

    private const val KEY_LENGTH_BITS = 256
    private const val DEFAULT_ITER = 10000

    fun hash(pin: String, salt: ByteArray = genSalt(), iterations: Int = DEFAULT_ITER): PinHash {
        val spec = PBEKeySpec(pin.toCharArray(), salt, iterations, KEY_LENGTH_BITS)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val hash = factory.generateSecret(spec).encoded
        return PinHash(
            hashB64 = Base64.encodeToString(hash, Base64.NO_WRAP),
            saltB64 = Base64.encodeToString(salt, Base64.NO_WRAP),
            iterations = iterations
        )
    }

    fun verify(pin: String, stored: PinHash): Boolean {
        val salt = Base64.decode(stored.saltB64, Base64.NO_WRAP)
        val recalculated = hash(pin, salt, stored.iterations)
        return stored.hashB64 == recalculated.hashB64
    }

    private fun genSalt(size: Int = 16): ByteArray {
        val salt = ByteArray(size)
        SecureRandom().nextBytes(salt)
        return salt
    }
}
