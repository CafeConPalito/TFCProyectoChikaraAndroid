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
class RegisterUseCaseTest {

    @Inject
    lateinit var repository: RegisterUseCase

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
     * Test for userNameExist
     * Consult if given Username is already registered in the System
     * @exception assertFalse
     */
    @Test
    fun userNameNotExist() {

        val test = "@thisUserNotExistInTheDataBase"

        val result = runBlocking {
            repository.userNameExist(test)
        }

        assertFalse(result)

    }

    /**
     * Test for userNameExist
     * Consult if given Username is already registered in the System
     * @exception assertTrue
     */
    @Test
    fun userNameExist() {

        val test = "@testUsername"

        val result = runBlocking {
            repository.userNameExist(test)
        }

        assertTrue(result)

    }


    /**
     * Test for emailExist
     * Consult if given Email is already registered in the System
     * @exception assertFalse
     */
    @Test
    fun emailNotExist() {

        val test = "thisUserNotExistInTheDataBase@email.com"

        val result = runBlocking {
            repository.emailExist(test)

        }

        assertFalse(result)

    }

    /**
     * Test for emailExist
     * Consult if given Email is already registered in the System
     * @exception assertTrue
     */
    @Test
    fun emailExist() {

        val test = "test@email.com"

        val result = runBlocking {
            repository.emailExist(test)

        }

        assertTrue(result)

    }

}