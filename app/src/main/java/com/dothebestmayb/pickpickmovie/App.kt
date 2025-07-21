package com.dothebestmayb.pickpickmovie

import android.app.Application
import com.dothebestmayb.pickpickmovie.data.BuildConfig
import com.dothebestmayb.pickpickmovie.data.auth.di.apiModule
import com.dothebestmayb.pickpickmovie.data.auth.di.authModule
import com.dothebestmayb.pickpickmovie.data.auth.di.networkModule
import com.dothebestmayb.pickpickmovie.data.auth.di.storageModule
import com.dothebestmayb.pickpickmovie.di.appModule
import com.dothebestmayb.pickpickmovie.di.validationModule
import com.dothebestmayb.pickpickmovie.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                viewModelModule,
                validationModule,
                apiModule,
                authModule,
                networkModule,
                storageModule,
            )
        }
    }
}
