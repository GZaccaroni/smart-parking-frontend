package it.unibolss.smartparking.domain.usecases.parkingslot

import arrow.core.Either
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.unibolss.smartparking.domain.entities.geo.GeoPosition
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FindParkingSlotsTest {
    @MockK
    lateinit var parkingSlotRepository: ParkingSlotRepository

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun testHappyCase() = runTest {
        val center = mockk<GeoPosition>()
        val radius = 10.0
        val parkingSlot = mockk<ParkingSlot>()

        val useCase = FindParkingSlots(parkingSlotRepository)

        coEvery {
            parkingSlotRepository.getParkingSlots(center, radius)
        } returns Either.Right(listOf(parkingSlot))
        val result = useCase.run(
            FindParkingSlots.Params(center, radius)
        )
        assertEquals(result, Either.Right(listOf(parkingSlot)))

        coVerify(exactly = 1) {
            parkingSlotRepository.getParkingSlots(center, radius)
        }

    }
    @Test
    fun testZeroRadius() = runTest {
        val center = mockk<GeoPosition>()
        val radius = 0.0

        val useCase = FindParkingSlots(parkingSlotRepository)

        val result = useCase.run(
            FindParkingSlots.Params(center, radius)
        )
        assertEquals(result, Either.Right(emptyList<ParkingSlot>()))

        coVerify(exactly = 0) {
            parkingSlotRepository.getParkingSlots(center, radius)
        }

    }
}