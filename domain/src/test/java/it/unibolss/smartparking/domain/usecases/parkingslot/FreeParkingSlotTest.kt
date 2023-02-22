package it.unibolss.smartparking.domain.usecases.parkingslot

import arrow.core.Either
import io.mockk.Called
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import it.unibolss.smartparking.domain.usecases.common.invoke
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FreeParkingSlotTest {
    @MockK
    lateinit var parkingSlotRepository: ParkingSlotRepository

    lateinit var useCase: FreeParkingSlot

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = FreeParkingSlot(parkingSlotRepository)
    }

    @Test
    fun testHappyCase() = runTest {
        val parkingSlotId = "1"
        val parkingSlot = mockk<ParkingSlot>()

        every { parkingSlot.id } returns parkingSlotId
        coEvery {
            parkingSlotRepository.getCurrentParkingSlot()
        } returns Either.Right(parkingSlot)
        coEvery {
            parkingSlotRepository.freeParkingSlot(parkingSlotId)
        } returns Either.Right(Unit)
        val result = useCase()
        assertEquals(Either.Right(Unit), result)

        coVerify(exactly = 1) {
            parkingSlotRepository.freeParkingSlot(parkingSlotId)
        }
    }

    @Test
    fun testNoParkingSlotToFree() = runTest {
        coEvery {
            parkingSlotRepository.getCurrentParkingSlot()
        } returns Either.Right(null)

        val result = useCase()
        assertEquals(Either.Right(Unit), result)

        coVerify {
            parkingSlotRepository.freeParkingSlot(any()) wasNot Called
        }
    }
}