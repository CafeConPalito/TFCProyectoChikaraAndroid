package com.cafeconpalito.chikara.domain.useCase

import com.cafeconpalito.chikara.domain.model.UserDto
import com.cafeconpalito.chikara.domain.repository.LoginRepository
import com.cafeconpalito.chikara.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: RegisterRepository) {

    suspend fun userNameExist(userName:String) = repository.userNameExist(userName)
    suspend fun emailExist(email:String) = repository.emailExist(email)
    fun registerUser(userDto: UserDto) = repository.registerUser(userDto)

}