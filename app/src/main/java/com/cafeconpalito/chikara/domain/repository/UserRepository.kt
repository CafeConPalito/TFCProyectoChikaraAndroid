package com.cafeconpalito.chikara.domain.repository

import com.cafeconpalito.chikara.domain.model.UserDto

interface UserRepository {

    suspend fun userNameExist(userName: String): Boolean
    suspend fun emailExist(email: String): Boolean
    suspend fun registerUser(userDto: UserDto): Boolean
    suspend fun getUserInformation(): UserDto?
    suspend fun updateUserInformation(userDto: UserDto): Boolean

}