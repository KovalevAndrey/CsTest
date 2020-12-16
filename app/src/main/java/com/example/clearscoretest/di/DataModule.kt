package com.example.clearscoretest.di

import android.content.Context
import android.content.SharedPreferences
import com.example.clearscoretest.data.api.CreditScoreApi
import com.example.clearscoretest.data.mapper.CreditScoreMapper
import com.example.clearscoretest.data.mapper.CreditScoreMapperImpl
import com.example.clearscoretest.data.persistence.CreditScorePersistence
import com.example.clearscoretest.data.persistence.CreditScorePersistenceImpl
import com.example.clearscoretest.data.repository.CreditScoreRepository
import com.example.clearscoretest.data.repository.CreditScoreRepositoryImpl
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single<CreditScoreApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CreditScoreApi::class.java)
    }

    single<Gson> {
        Gson()
    }

    single<OkHttpClient> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(loggingInterceptor)
        clientBuilder.build()
    }

    single<CreditScoreRepository> {
        CreditScoreRepositoryImpl(get(), get(), get())
    }

    single<CreditScoreMapper> {
        CreditScoreMapperImpl()
    }

    single<CreditScorePersistence> {
        CreditScorePersistenceImpl(sharedPreferences = get(), gson = get())
    }

    single<SharedPreferences> {
        androidApplication().getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE)
    }

}

private const val BASE_URL = "https://android-interview.s3.eu-west-2.amazonaws.com/"
private const val SHARED_PREFS_KEY = "shared_prefs"