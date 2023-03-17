package it.unibolss.smartparking.domain.di

import io.mockk.junit4.MockKRule
import io.mockk.mockk
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules

class DomainModuleTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @Test
    fun verifyKoinApp() {
        koinApplication {
            modules(domainModule)

            checkModules {
                withInstance<UserRepository>(mockk())
                withInstance<ParkingSlotRepository>(mockk())
            }
        }
    }
}
