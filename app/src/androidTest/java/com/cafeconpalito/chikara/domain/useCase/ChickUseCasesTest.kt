package com.cafeconpalito.chikara.domain.useCase


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
        NetworkModule.AuthKey="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZF91c2VyIjoiZDljMmUxNDQtN2NhZC00MGFhLTkwYzEtZTQ4M2M0Zjg1MzQ2IiwidXNlcl9uYW1lIjoiQHRlc3RVc2VybmFtZSIsImVtYWlsIjoidGVzdEBlbWFpbC5jb20iLCJmaXJzdF9uYW1lIjoiVXN1YXJpbyIsImZpcnN0X2xhc3RfbmFtZSI6IlRlc3QiLCJzZWNvbmRfbGFzdF9uYW1lIjoiTm8gQm9ycmFyIiwiYmlydGhkYXRlIjoiMTk4My0wNC0wOSIsImFjY291bnRfY3JlYXRpb24iOiIyMDI0LTA1LTE1IDA2OjM3OjA3Ljg0NjQ0OSswMDowMCIsImlzX3ByZW1pdW0iOmZhbHNlLCJleHAiOjE3MTYwMzI4MjJ9.wDqVFTa3occSD8tTs0l0YzTivm73qN8pI8eoeAVkeCA"
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

        //TODO REVISAR!!!!!
        val test = generateChickDto()

        val result = runBlocking {
            //repository.newChick(test)
        }

        //assertTrue(result)
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
                value = value,
                type = ChickTypeDto.TYPE_IMG
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

    private val value = "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"

}