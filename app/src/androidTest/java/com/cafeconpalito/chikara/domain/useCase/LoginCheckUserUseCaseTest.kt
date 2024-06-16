package com.cafeconpalito.chikara.domain.useCase

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
class LoginCheckUserUseCaseTest {

    @Inject
    lateinit var repository: LoginCheckUserUseCase

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    /**
     * Check If de dependencies injection is OK
     * @exception assertNotNull
     */
    @Test
    fun testInjection() {
        assertNotNull(repository)
    }


    /**
     * Test for LoginCheckUser
     * Whit given userName check if User exist in the System
     * @exception assertTrue
     */
    @Test
    fun checkValidUserName() {

        val userName = "@testUsername"

        val result = runBlocking {
            repository.invoke(userName)
        }

        assertTrue(result)

    }

    /**
     * Test for LoginCheckUser
     * Whit given userName check if User exist in the System
     * @exception assertFalse
     */
    @Test
    fun checkInvalidUserName() {

        val userName = "@thisUsernameNotExist"

        val result = runBlocking {
            repository.invoke(userName)
        }

        assertFalse(result)

    }


    /**
     * Test for LoginCheckUser
     * Whit given email check if User exist in the System
     * @exception assertTrue
     */
    @Test
    fun checkValidEmail() {

        val email = "test@email.com"

        val result = runBlocking {
            repository.invoke(email)
        }

        assertTrue(result)

    }

    /**
     * Test for LoginCheckUser
     * Whit given email check if User exist in the System
     * @exception assertFalse
     */
    @Test
    fun checkInvalidEmail() {

        val email = "thisEmailIsNotValid@email.com"

        val result = runBlocking {
            repository.invoke(email)
        }

        assertFalse(result)

    }


}