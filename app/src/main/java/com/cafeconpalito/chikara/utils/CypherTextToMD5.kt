package com.cafeconpalito.chikara.utils

import java.security.MessageDigest

class CypherTextToMD5 {

    /**
     * Cypher String into MD5
     * @param textToCypher String
     * @return Cypher String
     */
    @OptIn(ExperimentalStdlibApi::class)
    operator fun invoke(textToCypher: String): String {
        // Obtener la instancia de MessageDigest para MD5
        val md = MessageDigest.getInstance("MD5")

        // Actualizar el digest con los bytes de la cadena original y obtendo el array de Bytes
        val hashBytes = md.digest(textToCypher.toByteArray())

        //Retorna el MD5
        return hashBytes.toHexString()

    }

}