package it.unibo.smart_parking

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import it.unibo.smart_parking.data.di.dataModule
import it.unibo.smart_parking.domain.di.domainModule

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(dataModule + domainModule)
            androidContext(this@MainApplication)
        }
    }
}