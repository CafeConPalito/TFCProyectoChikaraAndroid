package com.cafeconpalito.chikara.domain.useCase


import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.cafeconpalito.chikara.data.network.NetworkModule
import com.cafeconpalito.chikara.domain.model.ChickContentDto
import com.cafeconpalito.chikara.domain.model.ChickDto
import com.cafeconpalito.chikara.domain.model.ChickTypeDto
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.LinkedList
import javax.inject.Inject

@HiltAndroidTest
class ChickUseCasesTest {

    @Inject
    lateinit var repository: ChickUseCases

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    /**
     * TODO: IMPORTANT NEED TO SETUP A AuthKey
     */
    @Before
    fun setup() {
        hiltRule.inject()
        NetworkModule.AuthKey="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZF91c2VyIjoiZDljMmUxNDQtN2NhZC00MGFhLTkwYzEtZTQ4M2M0Zjg1MzQ2IiwidXNlcl9uYW1lIjoiQHRlc3RVc2VybmFtZSIsImVtYWlsIjoidGVzdEBlbWFpbC5jb20iLCJmaXJzdF9uYW1lIjoiVXN1YXJpbyIsImZpcnN0X2xhc3RfbmFtZSI6IlRlc3QiLCJzZWNvbmRfbGFzdF9uYW1lIjoiTm8gQm9ycmFyIiwiYmlydGhkYXRlIjoiMTk4My0wNC0wOSIsImFjY291bnRfY3JlYXRpb24iOiIyMDI0LTA1LTE1IDA2OjM3OjA3Ljg0NjQ0OSswMDowMCIsImlzX3ByZW1pdW0iOmZhbHNlLCJleHAiOjE3MTU4NDE4NjN9.02JFrOg6WASplJbswwyOQsoX9XC2NAuLR_AImKOmEjA"
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
     * Test for getTopChicks
     */
    @Test
    fun getTopChicks() {

        val result = runBlocking {
            repository.getTopChicks()
        }

        assertNotNull(result)

    }

    @Test
    fun findByAuthor() {
    }

    /**
     * Test for newChick
     * try to insert a new Chick using a ChickDto
     * @exception assertTrue
     */
    @Test
    fun newChick() {

        val test = generateChickDto()

        val result = runBlocking {
            repository.newChick(test)
        }

        assertTrue(result)
    }


    /**
     * Generate Data for testing
     */
    private fun generateChickDto(): ChickDto {

        val content = generateChickContentDto()

        return ChickDto(
            title = "Test Chick2",
            isprivate = false,
            content = content
        )

    }

    /**
     * Generate Data for testing
     */
    private fun generateChickContentDto(): List<ChickContentDto> {

        val list: MutableList<ChickContentDto> = LinkedList()

        val content = listOf(

            ChickContentDto(
                position = 1,
                value = "HOLA MUNDO!",
                type = ChickTypeDto.TYPE_TEXT
            )
            //TODO: No se puede cifrar imagenes en BASE 64 Mediante el test,
            // ya que necesita acceso a los direcctorios y el test corre en un modo encapsulado.
//            ,
//            ChickContentDto(
//                position = 1,
//                value = "/sdcard/HelloWorld2.jpg",
//                type = ChickTypeDto.TYPE_IMG
//            )
//            ,
//            ChickContentDto(
//                position = 2,
//                value = "src/androidTest/java/com/cafeconpalito/chikara/res/HelloWorld2.jpg",
//                type = ChickTypeDto.TYPE_IMG
//            )
        )

        list.addAll(content)
        return list

    }

}