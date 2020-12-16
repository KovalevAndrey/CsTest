package com.example.clearscoretest.data.repository

import com.example.clearscoretest.data.api.CreditScoreApi
import com.example.clearscoretest.data.mapper.CreditScoreMapper
import com.example.clearscoretest.data.persistence.CreditScorePersistence
import com.example.clearscoretest.data.repository.model.CreditScore
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

interface CreditScoreRepository {
    fun getUserCreditScore(): Single<CreditScore>
}

class CreditScoreRepositoryImpl(
    private val creditScoreApi: CreditScoreApi,
    private val creditScoreMapper: CreditScoreMapper,
    private val creditScorePersistence: CreditScorePersistence
) : CreditScoreRepository {

    override fun getUserCreditScore(): Single<CreditScore> {
        return creditScoreApi.getCreditScore()
            .map { creditScoreMapper.mapToCreditScore(it) }
            .doOnSuccess { creditScorePersistence.saveCreditScore(it) }
            .onErrorReturn { creditScorePersistence.retrieveCreditScore() }
            .subscribeOn(Schedulers.io())
    }
}
