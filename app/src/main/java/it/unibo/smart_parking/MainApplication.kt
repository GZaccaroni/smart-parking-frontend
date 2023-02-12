package it.unibo.smart_parking

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import it.unibo.smart_parking.domain.di.domainModule
import it.unibo.smart_parking.data.di.dataModule
import it.unibo.smart_parking.presentation.di.presentationModule

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(domainModule + dataModule + presentationModule)
            androidContext(this@MainApplication)
        }
    }
}