package com.example.clearscoretest.data.api.model

import com.google.gson.annotations.SerializedName

data class CreditScoreInfoResponse(
    @SerializedName("score") val score: Int,
    @SerializedName("maxScoreValue") val maxScoreValue: Int,
    @SerializedName("minScoreValue") val minScoreValue: Int
)

