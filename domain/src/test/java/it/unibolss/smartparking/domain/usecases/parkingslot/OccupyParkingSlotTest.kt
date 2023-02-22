package it.unibolss.smartparking.domain.usecases.parkingslot

import arrow.core.Either
import io.mockk.Called
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class OccupyParkingSlotTest {
    @MockK
    lateinit var parkingSlotRepository: ParkingSlotRepository

    lateinit var useCase: OccupyParkingSlot

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = OccupyParkingSlot(parkingSlotRepository)
    }

    @Test
    fun testHappyCase() = runTest {
        val parkingSlotId = "1"

        coEvery {
            parkingSlotRepository.getCurrentParkingSlot()
        } returns Either.Right(null)
        coEvery {
            parkingSlotRepository.occupyParkingSlot(parkingSlotId)
        } returns Either.Right(Unit)
        val result = useCase(OccupyParkingSlot.Params(parkingSlotId))
        assertEquals(Either.Right(Unit), result)

        coVerify(exactly = 1) {
            parkingSlotRepository.occupyParkingSlot(parkingSlotId)
        }
    }

    @Test
    fun testAlreadyParked() = runTest {
        val newParkingSlotId = "1"
        val parkingSlot = mockk<ParkingSlot>()

        coEvery {
            parkingSlotRepository.getCurrentParkingSlot()
        } returns Either.Right(parkingSlot)

        val result = useCase(OccupyParkingSlot.Params(newParkingSlotId))
        assertEquals(Either.Left(AppError.AlreadyParking), result)

        coVerify {
            parkingSlotRepository.occupyParkingSlot(any()) wasNot Called
        }
    }
}
