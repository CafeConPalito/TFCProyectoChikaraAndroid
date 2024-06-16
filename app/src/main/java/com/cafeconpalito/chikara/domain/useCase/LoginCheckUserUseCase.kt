package com.cafeconpalito.chikara.domain.useCase

import com.cafeconpalito.chikara.domain.repository.LoginRepository
import javax.inject.Inject

class LoginCheckUserUseCase @Inject constructor(private val repository: LoginRepository) {

    /**
     * Find if username or email is valid
     * @param user String Username or Email
     * @return true if exists
     */
    suspend operator fun invoke(user:String) = repository.checkUser(user)
    
}