package com.cafeconpalito.chikara.domain.useCase

import com.cafeconpalito.chikara.domain.repository.LoginRepository
import javax.inject.Inject

class GetLoginUseCase@Inject constructor(private val repository: LoginRepository) {


    /**
     * Lanza la consulta para realizar el Login
     */
    suspend operator fun invoke(user:String , password:String) = repository.getLogin(user,password)

    /**
     * Es Lo Mismo pero picadito :D
     */
    suspend fun getLogin(user: String,password: String):Boolean{

        val x = repository.getLogin(user,password)

        return  x

    }

}