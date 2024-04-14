package com.cafeconpalito.chikara.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import javax.inject.Inject

//Nombre de los settings
private const val LAYOUT_PREFERENCES_NAME = "ChikaraSettings"

//Data Store para los settings
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = LAYOUT_PREFERENCES_NAME)

class UserPreferences (private val context: Context) {

    /**
     * Listado de KEY name and Type of User Preferences.
     *
     */
    companion object {
        val KEY_USER_STR = stringPreferencesKey("User")
        val KEY_PASSWORD_STR = stringPreferencesKey("Password")
    }


//    //NO BORRAR COORUTINA PARA GUARDAR DATOS DESDE UN ACTIVITY O FRAME
//    private fun initUI() {
//        binding.rsVolume.addOnChangeListener { _, value, _ ->
//            Log.i("Aris", "El valor es $value")
//            CoroutineScope(Dispatchers.IO).launch {
//                saveVolume(value.toInt())
//            }
//        }
//    }

    /**
     * Metodo generico para añadir
     */
    suspend fun <T> savePreference(preferenceKey: Preferences.Key<T>, value:T) {
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
    public fun getSettings(): Flow<UserPreferencesModel?> {
        return context.dataStore.data.map { preferences ->
            UserPreferencesModel(
                user = preferences[KEY_USER_STR] ?: "",
                password = preferences[KEY_PASSWORD_STR] ?: ""
            )
        }
    }



//    //Tipos especificos. me paso al generico :D
//
//    /**
//     * Guarda un Boolean en la data store,
//     * recibe como parametro preferenceKey y un valor
//     */
//    suspend fun savePreferenceBoolean(preferenceKey: String, value: Boolean) {
//        context.dataStore.edit { settings ->
//            settings[booleanPreferencesKey(preferenceKey)] = value
//        }
//    }
//
//    /**
//     * Guarda un Int en la data store,
//     * recibe como parametro preferenceKey y un valor
//     */
//    suspend fun savePreferenceInt(preferenceKey: String, value: Int) {
//        context.dataStore.edit { settings ->
//            settings[intPreferencesKey(preferenceKey)] = value
//        }
//    }
//
//    /**
//     * Guarda un String en la data store,
//     * recibe como parametro preferenceKey y un valor
//     */
//    suspend fun savePreferenceString(preferenceKey: String, value: String) {
//        context.dataStore.edit { settings ->
//            settings[stringPreferencesKey(preferenceKey)] = value
//        }
//    }


//    //Antiguo
//
//    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//
//    private val editor = sharedPreferences.edit();
//
//    /**
//     * Guarda un Boleano en las preferencias.
//     */
//    fun putPreferenceBoolean(preference: String , value: Boolean){
//        editor.putBoolean(preference,value)
//        editor.commit()
//    }
//
//    /**
//     * Rescata un Boleano de las preferencias.
//     */
//    fun getPreferenceBoolean(preference: String):Boolean{
//        return getPreferenceBoolean(preference);
//    }
//
//    /**
//     * Elimina una preferencia
//     */
//    fun removePrerence(preference: String){
//        editor.remove(preference)
//        editor.commit()
//    }


}
