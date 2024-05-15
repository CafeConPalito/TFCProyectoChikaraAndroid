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
    lateinit var registerUseCase: RegisterUseCase

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    /**
     * Check If de dependencies injection is OK
     */
    @Test
    fun testInjection(){
        assertNotNull(registerUseCase)
    }

    /**
     * Try to connect to Api in case of fail check Connection information or Api is online
     * @exception assertTrue
     */
    @Test
    fun checkApiConnection(){



    }

    /**
     * Test for userNameExist
     * Consult if given Username is already registered in the System
     * @exception assertFalse
     */
    @Test
    fun userNameNotExist(){

        val test = "thisUserNotExistInTheDataBase"

        val result = runBlocking {
            registerUseCase.userNameExist(test)
        }

        assertFalse(result)

    }

    /**
     * Test for emailExist
     * Consult if given Email is already registered in the System
     * @exception assertFalse
     */
    @Test
    fun emailNotExist(){

        val test = "test@email.com"

        val result = runBlocking {
            registerUseCase.emailExist(test)

        }

        assertFalse(result)

    }

}