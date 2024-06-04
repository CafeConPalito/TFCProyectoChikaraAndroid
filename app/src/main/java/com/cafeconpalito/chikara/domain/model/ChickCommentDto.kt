package com.cafeconpalito.chikara.domain.model

import java.io.Serializable

data class ChickCommentDto(

    val user: String,
    val comment: String,
    val date: String?

) : Serializable