package com.dothebestmayb.pickpickmovie

import android.app.Application
import com.dothebestmayb.pickpickmovie.BuildConfig
import com.dothebestmayb.pickpickmovie.di.commonModules
import com.dothebestmayb.pickpickmovie.di.platformModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class AndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@AndroidApp)
            modules(commonModules + platformModules())
        }
    }
}