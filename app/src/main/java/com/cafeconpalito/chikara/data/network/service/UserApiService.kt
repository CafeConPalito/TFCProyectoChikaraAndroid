package com.cafeconpalito.chikara.data.network.service

import com.cafeconpalito.chikara.domain.model.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserApiService {

    @GET("/users/searchuser")
    suspend fun userNameExist(@Query("user") userName: String): Boolean

    @GET("/users/searchemail")
    suspend fun emailExist(@Query("email") email: String): Boolean

    @POST("/users/register")
    suspend fun registerUser(@Body userDto: UserDto): Boolean

    @GET("/users/getuser")
    suspend fun getUserInformation(): UserDto

    @PUT("/users/update")
    suspend fun updateUserInformation(userDto: UserDto): Boolean


}