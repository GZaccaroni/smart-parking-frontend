package it.unibolss.smartparking

import android.app.Application
import it.unibolss.smartparking.data.di.dataModule
import it.unibolss.smartparking.domain.di.domainModule
import it.unibolss.smartparking.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(domainModule + dataModule + presentationModule)
            androidContext(this@MainApplication)
        }
    }
}
