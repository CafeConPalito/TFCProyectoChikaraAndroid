package com.cafeconpalito.chikara.data.network.service

import retrofit2.http.GET
import retrofit2.http.Query

interface LoginApiService {

    /**
     * Metodo que intenta loguear, recibe como respuesta 200 y el ApyKey o 400
     */
    @GET("/users/login")
    suspend fun getLogin(@Query("user") user: String, @Query("password") password: String): String

    @GET("/users/search")
    suspend fun checkUser(@Query("user") user: String): Boolean


}