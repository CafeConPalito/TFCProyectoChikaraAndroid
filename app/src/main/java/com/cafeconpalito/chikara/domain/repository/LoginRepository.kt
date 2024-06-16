package com.cafeconpalito.chikara.domain.repository

interface LoginRepository {

    /**
     * Try Login
     * return True if success
     */
    suspend fun getLogin(user:String,password:String):Boolean

    /**
     * Check if register user exist by username or email
     * return True if exist
     */
    suspend fun checkUser(user:String):Boolean



}