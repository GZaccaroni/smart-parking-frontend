package it.unibolss.smartparking.domain.usecases.parkingslot

import arrow.core.Either
import io.mockk.Called
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlotState
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.days

@ExperimentalCoroutinesApi
class IncrementParkingSlotOccupationTest {
    @MockK
    lateinit var parkingSlotRepository: ParkingSlotRepository

    lateinit var useCase: IncrementParkingSlotOccupation

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = IncrementParkingSlotOccupation(parkingSlotRepository)
    }

    @Test
    fun testHappyCase() = runTest {
        val parkingSlotId = "1"
        val parkingSlot = mockk<ParkingSlot>()
        val currentStopEnd = Clock.System.now()
        val newStopEnd = currentStopEnd.plus(5.days)

        every { parkingSlot.id } returns parkingSlotId
        every {
            parkingSlot.state
        } returns ParkingSlotState.Occupied(currentStopEnd, true)
        coEvery {
            parkingSlotRepository.getCurrentParkingSlot()
        } returns Either.Right(parkingSlot)
        coEvery {
            parkingSlotRepository.incrementParkingSlotOccupation(parkingSlotId, newStopEnd)
        } returns Either.Right(Unit)

        val result = useCase(IncrementParkingSlotOccupation.Params(newStopEnd))
        assertEquals(Either.Right(Unit), result)

        coVerify(exactly = 1) {
            parkingSlotRepository.incrementParkingSlotOccupation(parkingSlotId, newStopEnd)
        }
    }

    @Test
    fun testNoParkingSlotToExtend() = runTest {
        coEvery {
            parkingSlotRepository.getCurrentParkingSlot()
        } returns Either.Right(null)

        val result = useCase(IncrementParkingSlotOccupation.Params(mockk()))
        assertEquals(Either.Left(AppError.NoParkingSlotOccupied), result)

        coVerify {
            parkingSlotRepository.incrementParkingSlotOccupation(any(), any()) wasNot Called
        }
    }
}
