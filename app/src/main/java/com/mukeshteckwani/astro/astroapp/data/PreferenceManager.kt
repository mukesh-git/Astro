package com.mukeshteckwani.astro.astroapp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "settings")

@Singleton
class PreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val SORT_ORDER = intPreferencesKey("sort_order")

    val sortOrder: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[SORT_ORDER] ?: 0
    }

    suspend fun setSortOrder(order: Int) {
        context.dataStore.edit { preferences ->
            preferences[SORT_ORDER] = order
        }
    }
}
