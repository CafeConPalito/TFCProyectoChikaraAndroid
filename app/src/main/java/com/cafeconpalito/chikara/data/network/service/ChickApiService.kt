package com.cafeconpalito.chikara.data.network.service

import com.cafeconpalito.chikara.domain.model.ChickDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChickApiService {

    @GET("/chiks/top")
    suspend fun getTopChicks(): List<ChickDto>

    @GET("/chiks/findbyauthor")
    suspend fun getUserChicks(): List<ChickDto>

    @POST("/chiks/create")
    suspend fun createChick(@Body chickDto: ChickDto): ChickDto

    @DELETE("/chiks/delete/{id}")
    suspend fun deleteChick(@Path("id") chickId: String): Boolean

}