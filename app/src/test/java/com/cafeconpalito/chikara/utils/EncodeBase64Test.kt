package com.cafeconpalito.chikara.utils

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test


class EncodeBase64Test {

    /**
     * Test for EncodeBase64
     * Given a file path (String) encode the file in base 64 and return de String
     * @exception assertEquals
     */
    @Test
    fun encodedFileMatches() {

        val encodeBase64 = EncodeBase64()

        val filePath = "src/test/java/com/cafeconpalito/chikara/res/testBase64.txt"
        val expect = "ZmljaGVybyBkZSBwcnVlYmFzIHBhcmEgcHJvYmFyIGVuY3JpcHRhZG8gZW4gYmFzZTY0"

        val result = runBlocking {
            encodeBase64.invoke(filePath)
        }

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
        val expect = "encodeFail"

        val result = runBlocking {
            encodeBase64.invoke(filePath)
        }

        assertNotEquals(expect, result)

    }

}