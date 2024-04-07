package com.cafeconpalito.chikara.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeconpalito.chikara.domain.useCase.GetLoginUseCase
import com.cafeconpalito.chikara.ui.nakama.NakamaState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class WelcomeViewModel @Inject constructor(private val getLoginUseCase: GetLoginUseCase) : ViewModel(){

    //Como valor inicial le paso el estado de Cargando
    private var _state = MutableStateFlow<WelcomeState>(WelcomeState.Loading)
    //devuelve el estado en el que se encuentra, por eso no es publica
    val state: StateFlow<WelcomeState> = _state

    /**
     * Se ocupa de intentar logear y cambia el estado en concecuencia
     */
    fun tryLogin(user: String, password: String){

        //Esta trabajando en el Hilo principal
        viewModelScope.launch {

            //Cambio el stado a loading.
            _state.value = WelcomeState.Loading

            //Esto lanza un hilo secundario, almaceno la respuesta en result
            val result = withContext(Dispatchers.IO){
                getLoginUseCase(user,password)
            }

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


}