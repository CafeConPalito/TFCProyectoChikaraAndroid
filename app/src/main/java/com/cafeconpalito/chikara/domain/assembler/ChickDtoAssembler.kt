package com.cafeconpalito.chikara.domain.assembler

import android.net.Uri
import com.cafeconpalito.chikara.domain.model.ChickContentDto
import com.cafeconpalito.chikara.domain.model.ChickDto

class ChickDtoAssembler {
    fun buildDto(title: String, isPrivate: Boolean, contentElements: List<Uri>): ChickDto {

        val content: List<ChickContentDto>
        if (contentElements.isEmpty()) {
            content = emptyList()
        } else {
            val contentAssembler = ChickContentDtoAssembler()
            content = contentAssembler.buildDto(contentElements)
        }
        return ChickDto(
            title = title,
            isprivate = isPrivate,
            content = content
        )
    }

}