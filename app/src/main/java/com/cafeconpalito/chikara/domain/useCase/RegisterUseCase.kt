package com.cafeconpalito.chikara.domain.useCase

import com.cafeconpalito.chikara.domain.model.UserDto
import com.cafeconpalito.chikara.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: RegisterRepository) {

    /**
     * Find if user name is register
     * @param userName String User Name to check
     * @return true if already exist
     */
    suspend fun userNameExist(userName:String) = repository.userNameExist(userName)

    /**
     * Find if email is register
     * @param email String email to check
     * @return true if already exist
     */
    suspend fun emailExist(email:String) = repository.emailExist(email)

    /**
     * Register a user.
     * @param userDto information from user to register.
     * @return true if register succeed
     */
    suspend fun registerUser(userDto: UserDto) = repository.registerUser(userDto)

}