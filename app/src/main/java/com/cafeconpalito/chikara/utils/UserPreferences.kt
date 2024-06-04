package com.cafeconpalito.chikara.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//Nombre de los settings
private const val LAYOUT_PREFERENCES_NAME = "ChikaraSettings"

//Data Store para los settings
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = LAYOUT_PREFERENCES_NAME)

class UserPreferences @Inject constructor(private val context: Context) {

    /**
     * Listado de KEY name and Type of User Preferences.
     *
     */
    companion object {
        val KEY_USER_STR = stringPreferencesKey("User")
        val KEY_PASSWORD_STR = stringPreferencesKey("Password")
        val KEY_USER_UUID_STR = stringPreferencesKey("UserUUID")
    }

    /**
     * Metodo generico para añadir
     */
    suspend fun <T> savePreference(preferenceKey: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preference ->
            preference[preferenceKey] = value
        }
    }

    /**
     * Metodo Generico para borrar
     */
    suspend fun <T> deltePreference(preferenceKey: Preferences.Key<T>) {
        context.dataStore.edit { preference ->
            preference.remove(preferenceKey)
        }
    }

    /**
     * Lee todos los datos y los devuelve,
     * Si no tiene datos por defecto pone  (   ?: ""  ) vacio,
     */
    public fun getUserPreferences(): Flow<UserPreferencesModel?> {
        return context.dataStore.data.map { preferences ->
            UserPreferencesModel(
                userName = preferences[KEY_USER_STR] ?: "",
                password = preferences[KEY_PASSWORD_STR] ?: "",
                userUUID = preferences[KEY_USER_UUID_STR] ?: ""
            )
        }
    }

    /**
     * Delete all user preferences.
     */
    suspend fun deleteAllUserPreferences() {
        deltePreference(KEY_USER_STR)
        deltePreference(KEY_PASSWORD_STR)
        deltePreference(KEY_USER_UUID_STR)
    }

}
