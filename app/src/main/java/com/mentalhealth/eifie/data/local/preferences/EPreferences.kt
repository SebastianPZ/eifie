package com.mentalhealth.eifie.data.local.preferences

import androidx.datastore.preferences.core.Preferences

interface EPreferences {
    suspend fun <I> readPreference(key: Preferences.Key<I>): I?
    suspend fun <I> savePreference(key: Preferences.Key<I>, value: I)
}