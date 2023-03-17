package it.unibolss.smartparking.data.di

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
class DataModuleTest {

    @Test
    fun verifyKoinApp() {
        koinApplication {
            modules(dataModule)
            androidContext(
                RuntimeEnvironment.getApplication().applicationContext
            )

            checkModules()
        }
    }
}
