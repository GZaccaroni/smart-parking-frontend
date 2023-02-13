package it.unibolss.smartparking.domain.usecases.parkingslot

import arrow.core.Either
import io.mockk.Called
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import it.unibolss.smartparking.domain.entities.user.User
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import it.unibolss.smartparking.domain.usecases.common.invoke
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ViewParkingSlotTest {
    @MockK
    lateinit var parkingSlotRepository: ParkingSlotRepository

    lateinit var useCase: ViewParkingSlot

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = ViewParkingSlot(parkingSlotRepository)
    }

    @Test
    fun testHappyCase() = runTest {
        val parkingSlotId = "1"
        val parkingSlot = mockk<ParkingSlot>()

        coEvery {
            parkingSlotRepository.getParkingSlot(parkingSlotId)
        } returns Either.Right(parkingSlot)

        val result = useCase(ViewParkingSlot.Params(parkingSlotId))
        assertEquals(Either.Right(parkingSlot), result)

        coVerify(exactly = 1) {
            parkingSlotRepository.getParkingSlot(parkingSlotId)
        }
    }
}