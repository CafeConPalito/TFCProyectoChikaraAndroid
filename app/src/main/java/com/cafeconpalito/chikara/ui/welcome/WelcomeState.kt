package com.cafeconpalito.chikara.ui.welcome

import com.cafeconpalito.chikara.ui.nakama.NakamaState

sealed class WelcomeState {

    //Se ocupa del Estado de loading
    data object Loading: WelcomeState()
    //Al cambiar el estado a error enviara los datos que sean necesarios
    data class Error(val error:Boolean): WelcomeState()
    //Al cambiar al estado de Success nos enciara los datos que necesitemos.
    data class Success(val success:Boolean): WelcomeState()

}