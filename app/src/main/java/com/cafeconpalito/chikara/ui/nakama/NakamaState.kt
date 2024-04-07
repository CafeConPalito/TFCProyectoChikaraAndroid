package com.cafeconpalito.chikara.ui.nakama

sealed class NakamaState {

    //Se ocupa del Estado de loading
    data object Loading: NakamaState()
    //Al cambiar el estado a error enviara los datos que sean necesarios
    data class Error(val error:String): NakamaState()
    //Al cambiar al estado de Success nos enciara los datos que necesitemos.
    data class Success(val success:String): NakamaState()

}