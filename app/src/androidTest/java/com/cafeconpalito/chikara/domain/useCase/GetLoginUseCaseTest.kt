package com.cafeconpalito.chikara.domain.useCase

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class GetLoginUseCaseTest {

    @Inject
    lateinit var repository: GetLoginUseCase

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
     * Test for GetLogin
     * try to login a user using Email and Password
     * @exception assertTrue
     */
    @Test
    fun validLoginWhitEmail() {

        val email = "test@email.com"
        val pass = "81dc9bdb52d04dc20036dbd8313ed055"

        val result = runBlocking {
            repository.invoke(email,pass)
        }

        assertTrue(result)

    }

    /**
     * Test for GetLogin
     * try to login a user using Username and Password
     * @exception assertTrue
     */
    @Test
    fun validLoginWhitUsername() {

        val userName = "@testUsername"
        val pass = "81dc9bdb52d04dc20036dbd8313ed055"

        val result = runBlocking {
            repository.invoke(userName,pass)
        }

        assertTrue(result)

    }

    /**
     * Test for GetLogin
     * try to login a user using Email and Password
     * @exception assertFalse
     */
    @Test
    fun invalidLoginWhitEmail() {

        val email = "thisEmailIsNotValid@email.com"
        val pass = "81dc9bdb52d04dc20036dbd8313ed055"

        val result = runBlocking {
            repository.invoke(email,pass)
        }

        assertFalse(result)

    }

    /**
     * Test for GetLogin
     * try to login a user using Username and Password
     * @exception assertFalse
     */
    @Test
    fun invalidLoginWhitUsername() {

        val userName = "@thisUsernameNotExist"
        val pass = "81dc9bdb52d04dc20036dbd8313ed055"

        val result = runBlocking {
            repository.invoke(userName,pass)
        }

        assertFalse(result)

    }


}