package com.appninjas.eventscalendartspc.presentation.application

import android.app.Application
import com.appninjas.eventscalendartspc.presentation.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@Application)
            modules(listOf(
                networkModule,
                storageModule,
                repositoryModule,
                domainModule,
                appModule
            ))
        }
    }

}