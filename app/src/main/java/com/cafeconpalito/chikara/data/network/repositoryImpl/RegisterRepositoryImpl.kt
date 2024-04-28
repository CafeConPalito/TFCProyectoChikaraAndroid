package com.cafeconpalito.chikara.data.network.repositoryImpl

import android.util.Log
import com.cafeconpalito.chikara.data.network.service.LoginApiService
import com.cafeconpalito.chikara.data.network.service.RegisterApiService
import com.cafeconpalito.chikara.domain.model.UserDto
import com.cafeconpalito.chikara.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(private val apiService: RegisterApiService):RegisterRepository {


    /**
     * Comprueba que el UserName existe
     * True si existe
     */
    override suspend fun userNameExist(userName: String): Boolean {
        runCatching {

            apiService.userNameExist(userName)
        }
            .onSuccess {
                Log.i("RegistroUsuario: ", "API Usuario Existe = " + it)
                return  true }
            .onFailure {
                Log.i("RegistroUsuario: ", "API Usuario Existe = " + it)
            }

        return false
    }

    /**
     * Comprueba que el Email existe
     * True si existe
     */
    override suspend fun emailExist(email: String): Boolean {
        runCatching {

            apiService.emailExist(email)
        }
            .onSuccess {
                return  true }
            .onFailure {  }

        return false
    }

    /**
     * Intenta registrar un Nuevo Usuario.
     * Devuelve el DTO
     */
    override suspend fun registerUser(userDto: UserDto):Boolean {
        runCatching {

            apiService.registerUser(userDto)
        }
            .onSuccess {
                Log.i("RegistroUsuario: ", "Registro de usuario satisfactorio " + it)
                return true }
            .onFailure {
                Log.i("RegistroUsuario: ", "Error registro de usuario " + it.message)
            }

        return false
    }


}