package com.cafeconpalito.chikara.domain.repository

import com.cafeconpalito.chikara.domain.model.UserDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RegisterRepository {

    suspend fun userNameExist(userName:String):Boolean
    suspend fun emailExist(email:String):Boolean
    suspend fun registerUser(userDto:UserDto): Boolean



}