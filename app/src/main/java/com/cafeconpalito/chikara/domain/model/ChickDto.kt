package com.cafeconpalito.chikara.domain.model

import java.io.Serializable

data class ChickDto(

    var _id: String = "",
    val title: String,
    val author: String? = null,
    val author_name: String? = null,
    val date: String? = null,
    var likes: Long = 0,
    val isprivate: Boolean,
    var content: List<ChickContentDto> = emptyList(),
    var comments: List<ChickCommentDto> = emptyList(),
    var mencions: List<String> = emptyList()

) : Serializable