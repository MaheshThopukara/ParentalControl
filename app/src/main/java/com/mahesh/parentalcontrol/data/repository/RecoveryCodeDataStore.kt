package com.mahesh.parentalcontrol.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val DS_NAME = "pin_recovery_code_prefs"
val Context.recoveryDataStore: DataStore<Preferences> by preferencesDataStore(DS_NAME)

internal object RecoveryKeys {
    val HASH = stringPreferencesKey("recovery_hash")
    val SALT = stringPreferencesKey("recovery_salt")
    val ITER = stringPreferencesKey("recovery_iter")
    val SHOWN = booleanPreferencesKey("recovery_shown_once")
}

internal suspend fun storeRecovery(
    ds: DataStore<Preferences>,
    hash: String,
    salt: String,
    iter: Int
) {
    ds.edit {
        it[RecoveryKeys.HASH] = hash
        it[RecoveryKeys.SALT] = salt
        it[RecoveryKeys.ITER] = iter.toString()
    }
}

internal suspend fun markShown(ds: DataStore<Preferences>) {
    ds.edit { it[RecoveryKeys.SHOWN] = true }
}

internal suspend fun isRecoveryCodeShownOnce(ds: DataStore<Preferences>): Boolean {
    return ds.data.first()[RecoveryKeys.SHOWN] == true
}

internal suspend fun hasRecovery(ds: DataStore<Preferences>): Boolean =
    ds.data.map { it[RecoveryKeys.HASH] }.first() != null

internal suspend fun deleteRecoveryCode(ds: DataStore<Preferences>) {
    ds.edit { it.clear() }
}