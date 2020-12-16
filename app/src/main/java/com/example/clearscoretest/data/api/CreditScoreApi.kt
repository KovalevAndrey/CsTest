package com.example.clearscoretest.data.api

import com.example.clearscoretest.data.api.model.CreditScoreResponse
import io.reactivex.Single
import retrofit2.http.GET

interface CreditScoreApi {

    @GET("endpoint.json")
    fun getCreditScore(): Single<CreditScoreResponse>

}