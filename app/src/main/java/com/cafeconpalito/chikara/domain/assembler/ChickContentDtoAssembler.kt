package com.cafeconpalito.chikara.domain.assembler

import android.net.Uri
import com.cafeconpalito.chikara.domain.model.ChickContentDto
import com.cafeconpalito.chikara.domain.model.ChickTypeDto
import java.util.LinkedList

class ChickContentDtoAssembler {

    fun buildChickContentDto(contentElements: List<Uri>): List<ChickContentDto> {

        //List to return of Content
        val list: MutableList<ChickContentDto> = LinkedList()
        //Position of element
        var pos: Long = 0
        //For each element add to list
        for (uri in contentElements) {
            //position
            pos++
            //TODO FALTARIA VER COMO GESTIONAMOS OTRO TIPO DE ELEMENTOS,
            // De momento solo gestionamos imagenes.
            // para gestionar distintos tipos podriamos:
            // gestionar una lista que almacene un elemento con value y tipe y setearlo en el if
            // La Clase Chick UseCase Ya contempla la codificacion a Base 64 si es TYPE_IMG
            val content = ChickContentDto(
                position = pos,
                value = uri.toString(),
                type = ChickTypeDto.TYPE_IMG
            )
            list.add(content)
        }
        return list

    }

}