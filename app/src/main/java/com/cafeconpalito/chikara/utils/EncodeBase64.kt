package com.cafeconpalito.chikara.utils

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.util.Base64
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class EncodeBase64 {

    /**
     * Coorutina que retorna un String con la informacion de un file codificado en Base64
     * Recibe como parametro la ruta del fichero.
     */
    suspend operator fun invoke(filePath: String): String = suspendCoroutine { continuation ->
        GlobalScope.launch {
            try {
                val file = File(filePath)
                val fileBytes = file.readBytes()
                val base64EncodedString = Base64.getEncoder().encodeToString(fileBytes)
                continuation.resume(base64EncodedString)
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

}