package com.cafeconpalito.chikara.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeconpalito.chikara.domain.useCase.LoginCheckUserUseCase
import com.cafeconpalito.chikara.domain.useCase.GetLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getLoginUseCase: GetLoginUseCase,
    private val loginCheckUserUseCase: LoginCheckUserUseCase
) : ViewModel() {

    //Como valor inicial le paso el estado de Cargando
    private var _state = MutableStateFlow<LoginState>(LoginState.Loading)
    //devuelve el estado en el que se encuentra, por eso no es publica
    val state: StateFlow<LoginState> = _state

    /**
     * Inicializa el Flow para la ventana de carga y lanza el Loggin esperando respuesta.
     */
    fun launchLoginFlow(user: String, password: String) {

        //Esta trabajando en el Hilo principal
        viewModelScope.launch {
            //Cambio el stado a loading.
            _state.value = LoginState.Loading

            //Espererando la respuesta

            val result = loginCheckUserUseCase(user)

            //si la respuesta es correcta
            if (result) {

                val result2 = getLoginUseCase(user, password)

                if(result2){
                    _state.value = LoginState.Success(user,password)
                //Paso el esdado a Success y le paso el texto del horoscopo
                //SE PUEDE DEVOLVER EL OBJETO ENTERO O LO QUE NECESITEMOS.
                } else {
                    _state.value = LoginState.Error(true)
                }

            } else {
                _state.value = LoginState.Error(false)
            }

        }
    }

}