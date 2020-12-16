package com.example.clearscoretest.domain

import com.example.clearscoretest.data.repository.CreditScoreRepository
import com.example.clearscoretest.data.repository.model.CreditScore
import io.reactivex.Single

interface GetUserCreditScoreUseCase {
    operator fun invoke(): Single<CreditScore>
}

class GetUserCreditScoreUseCaseImpl(private val creditScoreRepository: CreditScoreRepository) :
    GetUserCreditScoreUseCase {

    override operator fun invoke(): Single<CreditScore> {
        return creditScoreRepository.getUserCreditScore()
    }
}