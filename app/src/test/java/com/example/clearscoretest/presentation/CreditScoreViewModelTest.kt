package com.example.clearscoretest.presentation

import RxJavaTestRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.clearscoretest.data.repository.model.CreditScore
import com.example.clearscoretest.domain.GetUserCreditScoreUseCase
import com.example.clearscoretest.presentation.CreditScoreViewEvent.OnViewReloadClicked
import com.nhaarman.mockitokotlin2.clearInvocations
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import getOrAwaitValue
import io.reactivex.Single
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class CreditScoreViewModelTest {

    @get:Rule
    val rxJavaTestRule = RxJavaTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var getUserCreditScoreUseCase: GetUserCreditScoreUseCase

    @Mock
    private lateinit var creditScoreModelMapper: CreditScoreModelMapper

    @Test
    fun `Given successful response from use case When VM is initialised Then sets success state`() {
        val creditScore = CreditScore(100, 100, 100)
        val mappedModel = CreditScoreModel("title", "subtitle", "67", 56)
        givenUseCaseSuccessfulResult(creditScore)
        givenMappedResult(creditScore, mappedModel)

        val vm = createVM()

        assertThat(vm.userScoreViewState.getOrAwaitValue(), Is.`is`(Result.Success(mappedModel)))
    }

    @Test
    fun `Given error response from use case When VM is initialised Then sets error state`() {
        givenUseCaseErrorResult()

        val vm = createVM()

        assertThat(vm.userScoreViewState.getOrAwaitValue(), IsInstanceOf(Result.Error::class.java))
    }

    @Test
    fun `Given VM in error state When on reload event received Then calls use case`() {
        givenUseCaseErrorResult()
        val vm = createVM()
        clearInvocations(getUserCreditScoreUseCase)

        vm.onViewEvent(OnViewReloadClicked)

        verify(getUserCreditScoreUseCase).invoke()
    }

    private fun givenUseCaseSuccessfulResult(creditScore: CreditScore) {
        whenever(getUserCreditScoreUseCase()).thenReturn(Single.just(creditScore))
    }

    private fun givenUseCaseErrorResult() {
        whenever(getUserCreditScoreUseCase()).thenReturn(Single.error(Throwable()))
    }

    private fun givenMappedResult(creditScore: CreditScore, creditScoreModel: CreditScoreModel) {
        whenever(creditScoreModelMapper.convert(creditScore)).thenReturn(creditScoreModel)
    }

    private fun createVM() = CreditScoreViewModel(getUserCreditScoreUseCase, creditScoreModelMapper)
}