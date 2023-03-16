package it.unibolss.smartparking

import it.unibolss.smartparking.data.di.dataModule
import it.unibolss.smartparking.domain.di.domainModule
import it.unibolss.smartparking.presentation.di.presentationModule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class DependencyInjectionModulesTest {

    @Test
    fun verifyKoinApp() {
        koinApplication {
            modules(domainModule)
            modules(dataModule)
            modules(presentationModule)
            androidContext(
                RuntimeEnvironment.getApplication().applicationContext
            )

            checkModules()
        }
    }
}