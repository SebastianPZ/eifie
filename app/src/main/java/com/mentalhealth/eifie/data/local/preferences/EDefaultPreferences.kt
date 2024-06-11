package com.mentalhealth.eifie.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class EDefaultPreferences(
    private val context: Context
): EPreferences {

    override suspend fun <I> readPreference(key: Preferences.Key<I>): I? {
        try {
            return context.dataStore.data
                .map { preferences ->
                    preferences[key]
                }.firstOrNull()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun <I> savePreference(key: Preferences.Key<I>, value: I) {
        context.dataStore.edit { settings ->
            settings[key] = value
        }
    }

}