package com.cafeconpalito.chikara.domain.repository

import com.cafeconpalito.chikara.domain.model.ChickDto

interface ChickRepository {

    /**
     * Busca el TOP de chicks por numero de likes
     * si lo consigue devuelve la lista. (puede estar vacia)
     * en caso de error devuelve una lista vacia.
     */
    suspend fun getTopChicks(): List<ChickDto>

    /**
     * Busca los Chicks por el Id del Author
     * si lo consigue devuelve la lista. (puede estar vacia)
     * en caso de error devuelve una lista vacia.
     */
    suspend fun getUserChicks(): List<ChickDto>

    /**
     * Add new Chick
     */
    suspend fun createChick(chickDto: ChickDto): Boolean

    /**
     * Delete Chick using chickId
     * return True if is Success
     */
    suspend fun deleteChick(chickId: String): Boolean

}