package it.unibolss.smartparking.presentation.di

import io.mockk.junit4.MockKRule
import io.mockk.mockk
import it.unibolss.smartparking.common.MainDispatcherRule
import it.unibolss.smartparking.domain.di.domainModule
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules

class PresentationModuleTest {
    @get:Rule
    val mockkRule = MockKRule(this)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun verifyKoinApp() {
        koinApplication {
            modules(domainModule)
            modules(presentationModule)
            checkModules {
                withInstance<UserRepository>(mockk())
                withInstance<ParkingSlotRepository>(mockk())
            }
        }
    }
}
