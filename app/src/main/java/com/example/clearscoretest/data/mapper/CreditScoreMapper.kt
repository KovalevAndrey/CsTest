package com.example.clearscoretest.data.mapper

import com.example.clearscoretest.data.api.model.CreditScoreResponse
import com.example.clearscoretest.data.repository.model.CreditScore

interface CreditScoreMapper {
    fun mapToCreditScore(response: CreditScoreResponse): CreditScore
}

class CreditScoreMapperImpl : CreditScoreMapper {

    override fun mapToCreditScore(response: CreditScoreResponse): CreditScore {
        with(response.creditScoreInfoResponse) {
            return CreditScore(
                score = score,
                minScore = minScoreValue,
                maxScore = maxScoreValue
            )
        }
    }
}