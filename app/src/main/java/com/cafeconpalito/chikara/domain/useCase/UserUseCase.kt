package com.cafeconpalito.chikara.domain.useCase

import android.util.Log
import com.cafeconpalito.chikara.domain.model.UserDto
import com.cafeconpalito.chikara.domain.repository.UserRepository
import com.cafeconpalito.chikara.utils.UserSession
import javax.inject.Inject

class UserUseCase @Inject constructor(private val repository: UserRepository) {

    /**
     * Find if user name is register
     * @param userName String User Name to check
     * @return true if already exist
     */
    suspend fun userNameExist(userName: String) = repository.userNameExist(userName)

    /**
     * Find if email is register
     * @param email String email to check
     * @return true if already exist
     */
    suspend fun emailExist(email: String) = repository.emailExist(email)

    /**
     * Register a user.
     * @param userDto information from user to register.
     * @return true if register succeed
     */
    suspend fun registerUser(userDto: UserDto) = repository.registerUser(userDto)

    /**
     * Get User Information from Api
     * @param AuthKey use the autkey to get the user information
     * @return null in case of error
     */
    suspend fun getUserInformation() = repository.getUserInformation()

    /**
     * Update user information
     * @param userDto information from user to register.
     * @return true if register succeed
     */
    suspend fun updateUserInformation(userDto: UserDto) = repository.updateUserInformation(userDto)

    suspend fun getSessionUserUUID() {
        val method = object {}.javaClass.enclosingMethod?.name
        val userDto = repository.getUserInformation()
        if (userDto != null) {
            Log.d(
                this.javaClass.simpleName,
                "Method: $method -> SUCCESS"
            )
            UserSession.userUUID = userDto.id
        } else {
            Log.d(
                this.javaClass.simpleName,
                "Method: $method -> FAIL"
            )
        }
    }

}