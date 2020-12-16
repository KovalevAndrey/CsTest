package com.example.clearscoretest

import android.app.Application
import com.example.clearscoretest.di.dataModule
import com.example.clearscoretest.di.domainModule
import com.example.clearscoretest.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ClearScoreApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            koin.loadModules(listOf(dataModule, domainModule, presentationModule))
            koin.createRootScope()
        }
    }
}