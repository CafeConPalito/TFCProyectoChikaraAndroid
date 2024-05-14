package com.cafeconpalito.chikara.utils

//import androidx.test.platform.app.InstrumentationRegistry
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import org.junit.runner.RunWith

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

//@HiltAndroidTest
//@RunWith(AndroidJUnit5::class)
class ValidateFieldsTest {

//    @Inject
//    lateinit var validateFields: ValidateFields
//
//    @get:Rule
//    var hiltRule = HiltAndroidRule(this)
//
//    @Before
//    fun setUp(){
//        hiltRule.inject()
//    }

    lateinit var validateFields: ValidateFields

    @Before
    fun setUp() {
        validateFields = ValidateFields()
    }

//    @Test
//    fun validEmail() {
//        //TODO: NO FUNCIONA REVISAR
//        val validateFields = ValidateFields()
//
//        val testA = "pruebaDeEmail@Correcto.com"
//        val testB = "emailIncorrecto"
//
////        val expectedA = true
////        val expectedB = false
//
//        val resultA = validateFields.validEmail(testA)
//        val resultB = validateFields.validEmail(testB)
//
//        assertTrue(resultA)
//        assertFalse(resultB)
//
//    }

    /**
     * Test for a valid UserName
     * Correct username must have: at least 3 characters and not start whit '@'
     * @exception assertTrue
     */
    @Test
    fun isValidUserName() {

        val test = "userName"
        val result = validateFields.validUserName(test)

        assertTrue(result)

    }

    /**
     * Test for a not valid UserName
     * Correct username must have: at least 3 characters and not start whit '@'
     *
     * @exception assertFalse
     */
    @Test
    fun notValidUserName() {

        val testA = "un"
        val testB = "@userName"

        assertFalse(validateFields.validUserName(testA))
        assertFalse(validateFields.validUserName(testB))

    }

    /**
     * Test for a valid password
     * Correct password must have: minLength 8 characters and at least one UpperCase, LowerCase and a Digit
     * @exception assertTrue
     */
    @Test
    fun isValidPassword() {

        val test = "asdFGH12"
        assertTrue(validateFields.validatePassword(test))

    }

    /**
     * Test for a valid password
     * Correct password must have: minLength 8 characters and at least one UpperCase, LowerCase and a Digit
     * @exception assertFalse
     *
     */
    @Test
    fun notValidPassword() {

        val testA = "fT1"
        val testB = "failtest"
        assertFalse(validateFields.validatePassword(testA))
        assertFalse(validateFields.validatePassword(testB))

    }

    /**
     * Test for validatePasswordMatches
     * Password Match must have: Two strings equals
     * @exception assertTrue
     */
    @Test
    fun passwordsMatches() {

        val valueA = "assertTest1"
        val valueB = "assertTest1"

        assertTrue(validateFields.validatePasswordsMatches(valueA, valueB))

    }

    /**
     * Test for validatePasswordMatches
     * Password Match must have: Two strings equals
     * @exception assertFalse
     */
    @Test
    fun passwordsNotMatches() {

        val valueA = "assertFalse1"
        val valueB = "assertTest1"

        assertFalse(validateFields.validatePasswordsMatches(valueA, valueB))

    }


    /**
     * Test for validateHaveBlankSpaces
     * @exception assertFalse
     */
    @Test
    fun stringNoContainsBlankSpaces() {

        val test = "assertTest"

        assertFalse(validateFields.validateHaveBlankSpaces(test))

    }

    /**
     * Test for validateHaveBlankSpaces
     * @exception assertTrue
     */
    @Test
    fun stringContainsBlankSpaces() {

        val testA = "assert Test"
        val testB = " "

        assertTrue(validateFields.validateHaveBlankSpaces(testA))
        assertTrue(validateFields.validateHaveBlankSpaces(testB))

    }

    /**
     * Test for completeUserName
     * Given a string check if start whit '@' if not, fix the string and return it
     * @exception assertEquals
     */
    @Test
    fun completeUserName() {

        val testA = "test"
        val testB = "@test"

        val except = "@test"

        val resultA = validateFields.completeUserName(testA)
        val resultB = validateFields.completeUserName(testB)

        assertEquals(except, resultA)
        assertEquals(except, resultB)
    }
}