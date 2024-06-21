package com.prativa.analyticssdk.session

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


const val PREFERENCE_NAME = "UserPreferences"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCE_NAME)

/**
 * Add boolean to the session helper
 */

suspend fun Context.writeBoolean(key: String, value: Boolean) {
    dataStore.edit { preferences ->
        preferences[booleanPreferencesKey(key)] = value
    }

}

/**
 * Read boolean from the session helper
 */
suspend fun Context.readBoolean(key: String): Flow<Boolean> {
    return dataStore.data.map { preferences ->
        preferences[booleanPreferencesKey(key)] ?: false
    }
}
/**
 * Add string to the session helper
 */
suspend fun Context.writeString(key: String, value: String) {
    dataStore.edit { preferences ->
        preferences[stringPreferencesKey(key)] = value
    }
}
/**
 * Read string from the session helper
 */
suspend fun Context.readString(key: String): Flow<String> {
    return dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(key)] ?: ""
    }

}

/**
 * Clear all the data from the data store
 */
suspend fun Context.deleteAll() {
    dataStore.edit { preferences ->
        preferences.clear()
    }
}
