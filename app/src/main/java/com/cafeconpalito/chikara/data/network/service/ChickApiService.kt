package com.cafeconpalito.chikara.data.network.service

import com.cafeconpalito.chikara.domain.model.ChickDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ChickApiService {

    @GET("/chiks/top")
    suspend fun getTopChicks():List<ChickDto>

    @GET("/chiks/findByAuthor")
    suspend fun findByAuthor(@Query("id") userId:String):List<ChickDto>

}