package com.example.clearscoretest.data.persistence

import android.content.SharedPreferences
import com.example.clearscoretest.data.repository.model.CreditScore
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

interface CreditScorePersistence {

    fun saveCreditScore(creditScore: CreditScore)

    fun retrieveCreditScore(): CreditScore

}

/**
 * For simplicity sharedPreferences is used, could be replaced with DB
 */
class CreditScorePersistenceImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : CreditScorePersistence {
    override fun saveCreditScore(creditScore: CreditScore) {
        val editor = sharedPreferences.edit()
        val creditScoreJson = gson.toJson(creditScore)
        editor.putString(CREDIT_SCORE_KEY, creditScoreJson)
        editor.apply()
    }

    @Throws(JsonSyntaxException::class)
    override fun retrieveCreditScore(): CreditScore {
        val creditScoreJson = sharedPreferences.getString(CREDIT_SCORE_KEY, "")
        return gson.fromJson(creditScoreJson, CreditScore::class.java)
    }
}

private const val CREDIT_SCORE_KEY = "credit_score_key"