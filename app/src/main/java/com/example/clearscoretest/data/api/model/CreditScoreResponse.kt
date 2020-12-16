package com.example.clearscoretest.data.api.model

import com.google.gson.annotations.SerializedName

class CreditScoreResponse(
    @SerializedName("creditReportInfo") val creditScoreInfoResponse: CreditScoreInfoResponse
)