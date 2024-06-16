package com.cafeconpalito.chikara.domain.repository

import com.cafeconpalito.chikara.domain.model.UserDto

interface UserRepository {

    /**
     * Check if userName is already registered
     * return True if exist
     */
    suspend fun userNameExist(userName: String): Boolean

    /**
     * Check if Email is already registered
     * return True if exist
     */
    suspend fun emailExist(email: String): Boolean

    /**
     * Try to register a User
     * return True if success
     */
    suspend fun registerUser(userDto: UserDto): Boolean

    /**
     * Try to obtain a User Information
     * return UserDto if success or Null
     */
    suspend fun getUserInformation(): UserDto?

    /**
     * Try to update a User Information
     * return True if success
     */
    suspend fun updateUserInformation(userDto: UserDto): Boolean

}