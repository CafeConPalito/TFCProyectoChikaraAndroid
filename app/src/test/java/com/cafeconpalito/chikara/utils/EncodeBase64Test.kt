package com.cafeconpalito.chikara.utils

import android.content.Context
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.File


@RunWith(RobolectricTestRunner::class)
class EncodeBase64Test {

    private val context: Context = ApplicationProvider.getApplicationContext()

    /**
     * Test for EncodeBase64
     * Given a file path (String) encode the file in base 64 and return de String
     * @exception assertEquals
     */
    @Test
    fun encodedFileMatches() {

        val encodeBase64 = EncodeBase64()

        val filePath = "src/test/java/com/cafeconpalito/chikara/res/testBase64.txt"
        val file = File(filePath)
        val uri = Uri.fromFile(file)

        val expect = "ZmljaGVybyBkZSBwcnVlYmFzIHBhcmEgcHJvYmFyIGVuY3JpcHRhZG8gZW4gYmFzZTY0"

        val result = runBlocking {
            encodeBase64.invoke(context, uri)
        }

        println(result)

        assertEquals(expect, result)

    }


    /**
     * Test for EncodeBase64
     * Given a file path (String) encode the file in base 64 and return de String
     * @exception assertNotEquals
     */
    @Test
    fun encodedFileNotMatches() {

        val encodeBase64 = EncodeBase64()

        val filePath = "src/test/java/com/cafeconpalito/chikara/res/testBase64.txt"
        val file = File(filePath)
        val uri = Uri.fromFile(file)

        val expect = "encodeFail"

        val result = runBlocking {
            encodeBase64.invoke(context,uri)
        }

        assertNotEquals(expect, result)

    }

}