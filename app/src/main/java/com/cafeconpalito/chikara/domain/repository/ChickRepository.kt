package com.cafeconpalito.chikara.domain.repository

import com.cafeconpalito.chikara.domain.model.ChickDto

interface ChickRepository {

    /**
     * Busca el TOP de chicks por numero de likes
     * si lo consigue devuelve la lista. (puede estar vacia)
     * en caso de error devuelve una lista vacia.
     */
    suspend fun getTopChicks():List<ChickDto>

    /**
     * Busca los Chicks por el Id del Author
     * si lo consigue devuelve la lista. (puede estar vacia)
     * en caso de error devuelve una lista vacia.
     */
    suspend fun findByAuthor(userId:String):List<ChickDto>

    /**
     * Add new Chick
     */
    suspend fun newChick(chickDto: ChickDto):Boolean

    /**
     *
     */

}