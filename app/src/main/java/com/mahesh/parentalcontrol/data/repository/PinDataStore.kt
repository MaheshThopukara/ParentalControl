package com.mahesh.parentalcontrol.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.Instant

private const val DS_NAME = "pin_prefs"

val Context.pinDataStore: DataStore<Preferences> by preferencesDataStore(DS_NAME)

internal object PinKeys {
    val HASH = stringPreferencesKey("pin_hash")
    val SALT = stringPreferencesKey("pin_salt")
    val ITER = stringPreferencesKey("pin_iter")
    val CREATED_AT = longPreferencesKey("created_at_epoch")
}

internal suspend fun savePinPrefs(
    ds: DataStore<Preferences>,
    hashB64: String,
    saltB64: String,
    iterations: Int
) {
    ds.edit { p ->
        p[PinKeys.HASH] = hashB64
        p[PinKeys.SALT] = saltB64
        p[PinKeys.ITER] = iterations.toString()
        p[PinKeys.CREATED_AT] = Instant.now().epochSecond
    }
}

internal suspend fun isPinStored(ds: DataStore<Preferences>): Boolean {
    return ds.data.map { it[PinKeys.HASH] }.first() != null
}

internal suspend fun deletePin(ds: DataStore<Preferences>) {
    ds.edit { it.clear() }
}
