package com.example.clearscoretest.data

import RxJavaTestRule
import com.example.clearscoretest.data.api.CreditScoreApi
import com.example.clearscoretest.data.api.model.CreditScoreInfoResponse
import com.example.clearscoretest.data.api.model.CreditScoreResponse
import com.example.clearscoretest.data.mapper.CreditScoreMapper
import com.example.clearscoretest.data.persistence.CreditScorePersistence
import com.example.clearscoretest.data.repository.CreditScoreRepositoryImpl
import com.example.clearscoretest.data.repository.model.CreditScore
import com.nhaarman.mockitokotlin2.clearInvocations
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class CreditScoreRepositoryTest {

    @get:Rule
    val rxJavaTestRule = RxJavaTestRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var creditScoreApi: CreditScoreApi

    @Mock
    private lateinit var creditScoreMapper: CreditScoreMapper

    @Mock
    private lateinit var creditScorePersistence: CreditScorePersistence


    @Test
    fun `Given successful response from api When getCreditScore called Then returns credit score`() {
        val creditScore = CreditScore(score = 100, minScore = 50, maxScore = 550)
        givenSaveCreditScore(creditScore)
        val response = createResponse()
        givenApiSuccessfulResult(response)
        givenMappedResult(response, creditScore)
        val repository = createRepository()

        val testObserver = repository.getUserCreditScore().test()

        testObserver.assertValue(creditScore)
    }

    @Test
    fun `Given error response from api When  getCreditScore called Then returns credit score from cache`() {
        val creditScore = CreditScore(score = 100, minScore = 50, maxScore = 550)
        givenSaveCreditScore(creditScore)
        givenRetrieveCreditScore(creditScore)
        givenApiErrorResult()
        val repository = createRepository()

        val testObserver = repository.getUserCreditScore().test()

        testObserver.assertValue(creditScore)
    }

    private fun givenMappedResult(response: CreditScoreResponse, creditScore: CreditScore) {
        whenever(creditScoreMapper.mapToCreditScore(response)).thenReturn(creditScore)
    }

    private fun givenApiSuccessfulResult(response: CreditScoreResponse) {
        whenever(creditScoreApi.getCreditScore()).thenReturn(Single.just(response))
    }

    private fun givenApiErrorResult() {
        whenever(creditScoreApi.getCreditScore()).thenReturn(Single.error(Throwable()))
    }

    /* ideally should be random values */
    private fun createResponse() = CreditScoreResponse(CreditScoreInfoResponse(20, 40, 0))

    private fun createRepository() =
        CreditScoreRepositoryImpl(creditScoreApi, creditScoreMapper, creditScorePersistence)

    private fun givenSaveCreditScore(creditScore: CreditScore) {
        doNothing().whenever(creditScorePersistence).saveCreditScore(creditScore)
    }

    private fun givenRetrieveCreditScore(creditScore: CreditScore) {
        whenever(creditScorePersistence.retrieveCreditScore()).thenReturn(creditScore)
    }
}