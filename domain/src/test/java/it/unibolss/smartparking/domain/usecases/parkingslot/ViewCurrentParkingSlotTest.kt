package it.unibolss.smartparking.domain.usecases.parkingslot

import arrow.core.Either
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ViewCurrentParkingSlotTest {
    @MockK
    lateinit var parkingSlotRepository: ParkingSlotRepository

    lateinit var useCase: ViewCurrentParkingSlot

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = ViewCurrentParkingSlot(parkingSlotRepository)
    }

    @Test
    fun testHappyCase() = runTest {
        val parkingSlot = mockk<ParkingSlot>()

        coEvery {
            parkingSlotRepository.getCurrentParkingSlot()
        } returns Either.Right(parkingSlot)

        val result = useCase(Unit)
        assertEquals(Either.Right(parkingSlot), result)

        coVerify(exactly = 1) {
            parkingSlotRepository.getCurrentParkingSlot()
        }
    }
}
