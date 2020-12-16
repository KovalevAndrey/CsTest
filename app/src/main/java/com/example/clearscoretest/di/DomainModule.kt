package com.example.clearscoretest.di

import com.example.clearscoretest.domain.GetUserCreditScoreUseCase
import com.example.clearscoretest.domain.GetUserCreditScoreUseCaseImpl
import org.koin.dsl.module

val domainModule = module {

    factory<GetUserCreditScoreUseCase> {
        GetUserCreditScoreUseCaseImpl(creditScoreRepository = get())
    }
}