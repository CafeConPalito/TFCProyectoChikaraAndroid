package com.cafeconpalito.chikara.utils

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ValidateFieldsTest {

    @Inject
    lateinit var validateFields: ValidateFields

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    /**
     * Test for validEmail
     * Given a email check if is valid format
     * @exception assertTrue
     */
    @Test
    fun isValidEmail() {

        val test = "pruebaDeEmail@Correcto.com"
        val result = validateFields.validEmail(test)

        assertTrue(result)

    }

    /**
     * Test for validEmail
     * Given a email check if is valid format
     * @exception assertTrue
     */
    @Test
    fun isNotValidEmail() {

        val test = "emailIncorrecto"
        val result = validateFields.validEmail(test)

        assertFalse(result)

    }


}