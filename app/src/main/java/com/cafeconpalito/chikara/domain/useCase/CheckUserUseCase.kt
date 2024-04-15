package com.cafeconpalito.chikara.domain.useCase

import com.cafeconpalito.chikara.domain.repository.LoginRepository
import javax.inject.Inject

class CheckUserUseCase @Inject constructor(private val repository: LoginRepository) {
    suspend operator fun invoke(user:String) = repository.userExist(user)
    
}