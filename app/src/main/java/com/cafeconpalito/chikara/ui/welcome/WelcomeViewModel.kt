package com.cafeconpalito.chikara.ui.welcome

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeconpalito.chikara.domain.useCase.GetLoginUseCase
import com.cafeconpalito.chikara.utils.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class WelcomeViewModel @Inject constructor(private val getLoginUseCase: GetLoginUseCase) : ViewModel(){

    //Como valor inicial le paso el estado de Cargando
    private var _state = MutableStateFlow<WelcomeState>(WelcomeState.Loading)
    //devuelve el estado en el que se encuentra, por eso no es publica
    val state: StateFlow<WelcomeState> = _state

    lateinit var userPreferences:UserPreferences

    /**
     * Inicializa el Flow para la ventana de carga y lanza el Loggin esperando respuesta.
     */
    fun launchLoginFlow(context: Context){

        userPreferences = UserPreferences(context)
        //Esta trabajando en el Hilo principal
        viewModelScope.launch {

            //Cambio el stado a loading.
            _state.value = WelcomeState.Loading

            //Espererando la respuesta
            val result = tryToLoggin()

            //si la respuesta es correcta
            if (result){
                //Paso el esdado a Success y le paso el texto del horoscopo
                //SE PUEDE DEVOLVER EL OBJETO ENTERO O LO QUE NECESITEMOS.
                _state.value = WelcomeState.Success( true)
            } else{
                _state.value = WelcomeState.Error(false)
            }

        }
    }

    /**
     * Intenta logear obteniendo los datos de userPreferences.
     * devuelve True si lo Logra.
     * Si no tiene datos o no lo consigue devuelve false
     */
    suspend fun tryToLoggin():Boolean {

        return withContext(Dispatchers.IO) {
            val userPreferenceModel = userPreferences.getSettings().first()

            //Comprueba que los UserPreference no es null para leer los datos
            if (userPreferenceModel != null) {
                val user = userPreferenceModel.user
                val password = userPreferenceModel.password

                //Comprueba que los datos obtenidos no son falsos.
                if (user.isNotBlank() && password.isNotBlank()){
                    return@withContext getLoginUseCase(user, password)
                }else{
                    return@withContext false
                }

            } else {

                return@withContext false
            }
        }

    }

}