package com.cafeconpalito.chikara.domain.repository

interface LoginRepository {

    suspend fun getLogin(user:String,password:String):Boolean
    suspend fun userExist(user:String):Boolean



}