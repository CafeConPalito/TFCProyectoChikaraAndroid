package com.cafeconpalito.chikara.utils

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Base64
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class EncodeBase64 {

    /**
     * Coorutina que retorna un String con la informacion de un file codificado en Base64
     * Recibe como parametro la Uri del fichero y el contexto
     */
    suspend operator fun invoke(context: Context, uri: Uri): String {
        return withContext(Dispatchers.IO) {
            try {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val fileBytes = inputStream.readBytes()
                    Base64.getEncoder().encodeToString(fileBytes)
                } ?: throw IllegalArgumentException("Unable to open InputStream for URI")
            } catch (e: Exception) {
                throw e
            }
        }
    }


}