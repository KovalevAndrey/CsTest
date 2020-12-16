package com.example.clearscoretest.presentation

import android.content.res.Resources
import com.example.clearscoretest.R

interface CreditScoreResourceProvider {

    fun provideCreditScoreTitle(): String

    fun provideCreditScoreSubtitle(): String
}

class CreditScoreResourceProviderImpl(private val resource: Resources) :
    CreditScoreResourceProvider {
    override fun provideCreditScoreTitle(): String {
        return resource.getString(R.string.credit_score_title)
    }

    override fun provideCreditScoreSubtitle(): String {
        return resource.getString(R.string.credit_score_subtitle)
    }

}