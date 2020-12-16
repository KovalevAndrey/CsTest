package com.example.clearscoretest.presentation

import com.example.clearscoretest.data.repository.model.CreditScore

interface CreditScoreModelMapper {
    fun convert(creditScore: CreditScore): CreditScoreModel
}

class CreditScoreModelMapperImpl(private val creditScoreResourceProvider: CreditScoreResourceProvider) :
    CreditScoreModelMapper {

    override fun convert(creditScore: CreditScore): CreditScoreModel {
        return CreditScoreModel(
            value = creditScore.score.toString(),
            title = creditScoreResourceProvider.provideCreditScoreTitle(),
            subtitle = creditScoreResourceProvider.provideCreditScoreSubtitle()
                .plus(" ")
                .plus(creditScore.maxScore),
            percentage = calculatePercentage(creditScore)
        )
    }

    private fun calculatePercentage(creditScore: CreditScore): Int {
        return (creditScore.score.toDouble() / (creditScore.maxScore - creditScore.minScore) * 100).toInt()
    }
}