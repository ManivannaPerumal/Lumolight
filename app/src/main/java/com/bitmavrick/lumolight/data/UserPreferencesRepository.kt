package com.bitmavrick.lumolight.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES_NAME = "user_preferences"
val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES_NAME)

class UserPreferencesRepository(context: Context) {
    companion object {
        private val APP_LOADING_KEY = booleanPreferencesKey("appLoading")
        private val SAVE_QUICK_ACTION_KEY = booleanPreferencesKey("saveQuickAction")
        private val SEGMENTED_BUTTON_VALUE_KEY = intPreferencesKey("segmentedButtonValue")
    }

    private val dataStore = context.dataStore

    val appLoading: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[APP_LOADING_KEY] ?: true
        }

    val saveQuickAction: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[SAVE_QUICK_ACTION_KEY] ?: true
        }

    val segmentedButtonValue: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[SEGMENTED_BUTTON_VALUE_KEY] ?: 0
        }

    suspend fun updateQuickActionPreference(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[SAVE_QUICK_ACTION_KEY] = value
        }
    }

    suspend fun saveSegmentedButtonValue(value: Int) {
        dataStore.edit { preferences ->
            preferences[SEGMENTED_BUTTON_VALUE_KEY] = value
        }
    }
}

