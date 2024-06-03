package com.cafeconpalito.chikara.domain.model

import java.io.Serializable

data class ChickCommentDto(

    val user: String,
    val comment: String,
    val date: String?

) : Serializable

//            "user": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
//            "comment": "string",
//            "date": "2024-04-28"