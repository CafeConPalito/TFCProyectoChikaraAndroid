package com.cafeconpalito.chikara.domain.useCase

import android.content.Context
import android.net.Uri
import com.cafeconpalito.chikara.domain.model.ChickDto
import com.cafeconpalito.chikara.domain.model.ChickTypeDto
import com.cafeconpalito.chikara.domain.repository.ChickRepository
import com.cafeconpalito.chikara.utils.EncodeBase64
import javax.inject.Inject

class ChickUseCases @Inject constructor(private val repository: ChickRepository) {


    /**
     * Busca el TOP de chicks por numero de likes
     * si lo consigue devuelve la lista. (puede estar vacia)
     * en caso de error devuelve una lista vacia.
     */
    suspend fun getTopChicks() = repository.getTopChicks()

    /**
     * Busca los Chicks por el Id del Author
     * si lo consigue devuelve la lista. (puede estar vacia)
     * en caso de error devuelve una lista vacia.
     */
    suspend fun getUserChicks() = repository.getUserChicks()

    /**
     * Add new Chick
     */
    suspend fun newChick(context: Context, chickDto: ChickDto): Boolean {

        val encodeBase64 = EncodeBase64()

        //For Each Element in the Dto check if is Image and Encode to Base64
        chickDto.content.forEach { cont ->
            if (cont.type == ChickTypeDto.TYPE_IMG) {
                val uri: Uri = Uri.parse(cont.value)
                cont.value = encodeBase64(context, uri)
            }
        }

        return repository.newChick(chickDto)

    }
}