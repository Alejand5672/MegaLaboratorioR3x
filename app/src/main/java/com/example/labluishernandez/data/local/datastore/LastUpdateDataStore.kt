package com.example.labluishernandez.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.firstOrNull


val Context.cryptoPrefsDataStore by preferencesDataStore(name = "crypto_prefs")

class LastUpdateDataStore(
    private val dataStore: androidx.datastore.core.DataStore<Preferences>
) {

    companion object {
        private val LAST_UPDATE_KEY = stringPreferencesKey("last_update")
    }

    val lastUpdateFlow: Flow<String?> = dataStore.data.map { prefs ->
        prefs[LAST_UPDATE_KEY]
    }

    suspend fun saveLastUpdate(text: String) {
        dataStore.edit { prefs ->
            prefs[LAST_UPDATE_KEY] = text
        }
    }

    suspend fun getLastUpdateOnce(): String? {
        return dataStore.data.map { it[LAST_UPDATE_KEY] }.firstOrNull()
    }
}

