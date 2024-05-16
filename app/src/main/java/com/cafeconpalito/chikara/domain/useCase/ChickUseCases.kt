package com.cafeconpalito.chikara.domain.useCase

import com.cafeconpalito.chikara.domain.model.ChickDto
import com.cafeconpalito.chikara.domain.model.ChickTypeDto
import com.cafeconpalito.chikara.domain.repository.ChickRepository
import com.cafeconpalito.chikara.utils.CypherTextToMD5
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
    suspend fun findByAuthor(userId: String) = repository.findByAuthor(userId)

    /**
     * Add new Chick
     */
    suspend fun newChick(chickDto: ChickDto): Boolean {

        val encodeBase64 = EncodeBase64()
        chickDto.content.forEach { cont ->
            if (cont.type == ChickTypeDto.TYPE_IMG){
                cont.value = encodeBase64(cont.value)
            }
        }

        return repository.newChick(chickDto)

    }
}