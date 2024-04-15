package com.cafeconpalito.chikara.ui.login

import com.cafeconpalito.chikara.ui.nakama.NakamaState

sealed class LoginState {

    //Se ocupa del Estado de loading
    data object Loading: LoginState()
    //Al cambiar el estado a error enviara los datos que sean necesarios
    data class Error(val UserFounded:Boolean, val PasswordMatched:Boolean): LoginState()
    //Al cambiar al estado de Success nos enciara los datos que necesitemos.
    data class Success(val success:Boolean): LoginState()

}