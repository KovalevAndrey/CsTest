package com.example.clearscoretest.presentation

import com.example.clearscoretest.data.repository.model.CreditScore
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class CreditScoreModelMapperTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var creditScoreResourceProvider: CreditScoreResourceProvider

    @Test
    fun `Given model mapper When convert is called Then converts data properly`() {
        val creditScore = CreditScore(score = 100, minScore = 50, maxScore = 550)
        val mapper = createMapper()
        givenResourceProvideTitle("Your score is")
        givenResourceProvideSubtitle("out of")

        val mappedResult = mapper.convert(creditScore)

        assertThat(mappedResult.percentage, Is.`is`(20))
        assertThat(mappedResult.title, Is.`is`("Your score is"))
        assertThat(mappedResult.subtitle, Is.`is`("out of 550"))
        assertThat(mappedResult.value, Is.`is`("100"))
    }


    private fun givenResourceProvideTitle(title: String) {
        whenever(creditScoreResourceProvider.provideCreditScoreTitle()).thenReturn(title)
    }

    private fun givenResourceProvideSubtitle(subtitle: String) {
        whenever(creditScoreResourceProvider.provideCreditScoreSubtitle()).thenReturn(subtitle)
    }

    private fun createMapper() = CreditScoreModelMapperImpl(creditScoreResourceProvider)
}