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
                //Log.d("ChickRepository: ", "getTopChicks API SUCCESS")
                Log.d(
                    this.javaClass.simpleName,
                    "Method: ${this.javaClass.enclosingMethod?.name} -> API SUCCESS"
                )
                return it
            }
            .onFailure {
                //Log.d("ChickRepository: ", "getTopChicks API FAIL $it")
                Log.d(
                    this.javaClass.simpleName,
                    "Method: ${this.javaClass.enclosingMethod?.name} -> API FAIL: $it"
                )
            }

        return emptyList()
    }

    /**
     * Busca los Chicks por el Id del Author
     * si lo consigue devuelve la lista. (puede estar vacia)
     * en caso de error devuelve una lista vacia.
     */
    override suspend fun getUserChicks(): List<ChickDto> {
        runCatching {
            apiService.getUserChicks()
        }
            .onSuccess {
                //Log.d("ChickRepository: ", "findByAuthor API SUCCESS")
                Log.d(
                    this.javaClass.simpleName,
                    "Method: ${this.javaClass.enclosingMethod?.name} -> API SUCCESS"
                )
                return it
            }
            .onFailure {
                //Log.d("ChickRepository: ", "findByAuthor API FAIL $it")
                Log.d(
                    this.javaClass.simpleName,
                    "Method: ${this.javaClass.enclosingMethod?.name} -> API FAIL: $it"
                )
            }

        return emptyList()
    }

    /**
     * Add NewChick
     * if the chick is inserted correctly return true
     *
     */
    override suspend fun newChick(chickDto: ChickDto): Boolean {
        runCatching {

            //Log.d("ChickRepository: ", "DTO Send:\n $chickDto")
            Log.d(
                this.javaClass.simpleName,
                "Method: ${this.javaClass.enclosingMethod?.name} -> DTO Send:\n$chickDto"
            )

            apiService.newChick(chickDto)

        }
            .onSuccess {
                //Log.d("ChickRepository: ", "newChick API SUCCESS")
                Log.d(
                    this.javaClass.simpleName,
                    "Method: ${this.javaClass.enclosingMethod?.name} -> API SUCCESS"
                )
                return true
            }
            .onFailure {
                //Log.d("ChickRepository: ", "newChick API FAIL $it")
                Log.d(
                    this.javaClass.simpleName,
                    "Method: ${this.javaClass.enclosingMethod?.name} -> API FAIL: $it"
                )
                return false
            }

        return false
    }


}