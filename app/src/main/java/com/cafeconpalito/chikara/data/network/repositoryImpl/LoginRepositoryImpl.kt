package com.cafeconpalito.chikara.data.network.repositoryImpl

import android.util.Log
import com.cafeconpalito.chikara.data.network.NetworkModule
import com.cafeconpalito.chikara.data.network.service.LoginApiService
import com.cafeconpalito.chikara.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val apiService: LoginApiService):LoginRepository{


    /**
     * Metodo para realizar el login
     * La respuesta esperada es un 200 con una Api Key
     */
    override suspend fun getLogin(user: String, password: String): Boolean {

        runCatching {
            apiService.getLogin(user,password)
        }
            .onSuccess {
                NetworkModule.AuthKey = it
                Log.i ("getLogin", "LOG OK: ${it.toString()}")
                return  true }
            .onFailure { Log.i ("getLogin", "IMPOSIBLE LOGUEAR: ${it.toString()}") }

        return false

    }


}