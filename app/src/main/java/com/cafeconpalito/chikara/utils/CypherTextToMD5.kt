package com.cafeconpalito.chikara.utils

import java.security.MessageDigest

class CypherTextToMD5 {

    @OptIn(ExperimentalStdlibApi::class)
    fun invoke(textToCyfer: String): String {
        // Obtener la instancia de MessageDigest para MD5
        val md = MessageDigest.getInstance("MD5")

        // Obtener el arreglo de bytes de la cadena original
        //val bytes = textToCyfer.toByteArray()

        // Actualizar el digest con los bytes de la cadena original y obtendo el array de Bytes
        val hashBytes = md.digest(textToCyfer.toByteArray())

        //Retorna el MD5
        return hashBytes.toHexString()

    }

}