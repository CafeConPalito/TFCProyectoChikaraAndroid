package com.cafeconpalito.chikara.data.network.repositoryImpl

import android.util.Log
import com.cafeconpalito.chikara.data.network.service.ChickApiService
import com.cafeconpalito.chikara.domain.model.ChickDto
import com.cafeconpalito.chikara.domain.repository.ChickRepository
import javax.inject.Inject

class ChickRepositoryImpl @Inject constructor(private val apiService: ChickApiService) :
    ChickRepository {


    /**
     * Busca el TOP de chicks por numero de likes
     * si lo consigue devuelve la lista. (puede estar vacia)
     * en caso de error devuelve una lista vacia.
     */
    override suspend fun getTopChicks(): List<ChickDto> {
        runCatching {

            apiService.getTopChicks()
        }
            .onSuccess {
                Log.i("ChickRepository: ", "getTopChicks API SUCCESS")
                return it
            }
            .onFailure {
                Log.i("ChickRepository: ", "getTopChicks API FAIL $it")
            }

        return emptyList()
    }

    /**
     * Busca los Chicks por el Id del Author
     * si lo consigue devuelve la lista. (puede estar vacia)
     * en caso de error devuelve una lista vacia.
     */
    override suspend fun findByAuthor(userId: String): List<ChickDto> {
        runCatching {

            apiService.findByAuthor(userId)

        }
            .onSuccess {
                Log.i("ChickRepository: ", "findByAuthor API SUCCESS")
                return it
            }
            .onFailure {
                Log.i("ChickRepository: ", "findByAuthor API FAIL $it")
            }

        return emptyList()
    }


}