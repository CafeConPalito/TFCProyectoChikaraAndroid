package com.cafeconpalito.chikara.data.network.repositoryImpl

import android.util.Log
import com.cafeconpalito.chikara.data.network.service.UserApiService
import com.cafeconpalito.chikara.domain.model.UserDto
import com.cafeconpalito.chikara.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: UserApiService) :
    UserRepository {

    /**
     * Comprueba que el UserName existe
     * True si existe
     */
    override suspend fun userNameExist(userName: String): Boolean {
        runCatching {
            apiService.userNameExist(userName)
        }
            .onSuccess {
                //Log.i("RegistroUsuario: ", "API Usuario Existe = $it")
                Log.d(
                    this.javaClass.simpleName,
                    "Method: ${this.javaClass.enclosingMethod?.name} -> API SUCCESS: $it"
                )
                return true
            }
            .onFailure {
                //Log.i("RegistroUsuario: ", "API Usuario Existe = $it")
                Log.d(
                    this.javaClass.simpleName,
                    "Method: ${this.javaClass.enclosingMethod?.name} -> API FAIL: $it"
                )
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
                Log.d(
                    this.javaClass.simpleName,
                    "Method: ${this.javaClass.enclosingMethod?.name} -> API SUCCESS: $it"
                )
                return true
            }
            .onFailure {
                Log.d(
                    this.javaClass.simpleName,
                    "Method: ${this.javaClass.enclosingMethod?.name} -> API FAIL: $it"
                )
            }

        return false
    }

    /**
     * Intenta registrar un Nuevo Usuario.
     * Devuelve el DTO
     */
    override suspend fun registerUser(userDto: UserDto): Boolean {
        runCatching {
            apiService.registerUser(userDto)
        }
            .onSuccess {
                //Log.i("RegistroUsuario: ", "Registro de usuario satisfactorio $it")
                Log.d(
                    this.javaClass.simpleName,
                    "Method: ${this.javaClass.enclosingMethod?.name} -> API SUCCESS: $it"
                )
                return true
            }
            .onFailure {
                //Log.i("RegistroUsuario: ", "Error registro de usuario " + it.message)
                Log.d(
                    this.javaClass.simpleName,
                    "Method: ${this.javaClass.enclosingMethod?.name} -> API FAIL: $it"
                )
            }
        return false
    }

    /**
     * Get User Information from Api
     */
    override suspend fun getUserInformation(): UserDto? {
        runCatching {
            apiService.getUserInformation()
        }
            .onSuccess {
                Log.d(
                    this.javaClass.simpleName,
                    "Method: ${this.javaClass.enclosingMethod?.name} -> API SUCCESS: $it"
                )
                return it
            }
            .onFailure {
                Log.d(
                    this.javaClass.simpleName,
                    "Method: ${this.javaClass.enclosingMethod?.name} -> API FAIL: $it"
                )
            }
        return null
    }

    /**
     * Update user information
     */
    override suspend fun updateUserInformation(userDto: UserDto): Boolean {
        runCatching {
            apiService.updateUserInformation(userDto)
        }
            .onSuccess {
                Log.d(
                    this.javaClass.simpleName,
                    "Method: ${this.javaClass.enclosingMethod?.name} -> API SUCCESS: $it"
                )
                return true
            }
            .onFailure {
                Log.d(
                    this.javaClass.simpleName,
                    "Method: ${this.javaClass.enclosingMethod?.name} -> API FAIL: $it"
                )
            }
        return false
    }


}