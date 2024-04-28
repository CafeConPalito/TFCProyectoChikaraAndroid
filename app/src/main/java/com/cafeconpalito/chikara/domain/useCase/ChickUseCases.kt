package com.cafeconpalito.chikara.domain.useCase

import com.cafeconpalito.chikara.domain.repository.ChickRepository
import javax.inject.Inject

class ChickUseCases @Inject constructor(private val repository: ChickRepository){


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
    suspend fun findByAuthor(userId:String) = repository.findByAuthor(userId)

}