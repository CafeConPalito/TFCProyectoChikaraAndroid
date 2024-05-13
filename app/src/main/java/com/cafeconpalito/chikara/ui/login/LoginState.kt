package com.cafeconpalito.chikara.ui.login

sealed class LoginState {

    //Se ocupa del Estado de loading
    data object Loading: LoginState()
    //Al cambiar el estado a error enviara los datos que sean necesarios

    /**
     * Si userFounded = true el usuiario existe
     * Si userFounded = false el usuario no existe
     */
    data class Error(val UserFounded:Boolean): LoginState()
    //Al cambiar al estado de Success nos enciara los datos que necesitemos.
    data class Success(val user:String, val password:String): LoginState()
    
}