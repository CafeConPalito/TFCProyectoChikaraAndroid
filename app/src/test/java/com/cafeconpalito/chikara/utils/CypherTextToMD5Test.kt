package com.cafeconpalito.chikara.utils


import org.junit.Test
import org.junit.Assert.*
class CypherTextToMD5Test {


    /**
     * Test To check Cypher in MD5
     */
    @Test
    fun cypherIsCorrect() {

        val cypherTextToMD5 = CypherTextToMD5()
        val toCypher = "1234"
        val result = cypherTextToMD5.invoke(toCypher)
        val expected = "81dc9bdb52d04dc20036dbd8313ed055"

        assertEquals(expected,result)

    }
}