package com.cafeconpalito.chikara.domain.model

import java.io.Serializable

data class ChickContentDto(

    val position: Long,
    var value: String,
    val type: ChickTypeDto

) : Serializable
