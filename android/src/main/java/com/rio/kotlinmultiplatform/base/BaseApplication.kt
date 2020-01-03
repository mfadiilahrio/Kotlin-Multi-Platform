package com.rio.kotlinmultiplatform.base

import android.app.Application
import com.facebook.stetho.Stetho
import com.rio.kotlinmultiplatform.appModule
import com.rio.kotlinmultiplatform.avenger.dependency.avengerModule
import com.rio.kotlinmultiplatform.xmen.dependency.xmenModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BaseApplication)
            modules(listOf(
                appModule,
                avengerModule,
                xmenModule))
        }

        Stetho.initializeWithDefaults(this)
    }
}