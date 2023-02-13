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
class OccupyParkingSlotTest {
    @MockK
    lateinit var userRepository: UserRepository
    @MockK
    lateinit var parkingSlotRepository: ParkingSlotRepository

    lateinit var useCase: OccupyParkingSlot

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = OccupyParkingSlot(userRepository, parkingSlotRepository)
    }

    @Test
    fun testHappyCase() = runTest {
        val parkingSlotId = "1"
        val user = mockk<User>()

        every { user.currentParkingSlot } returns null
        coEvery {
            userRepository.getUser()
        } returns Either.Right(user)
        coEvery {
            parkingSlotRepository.occupyParkingSlot(parkingSlotId)
        } returns Either.Right(Unit)
        val result = useCase(OccupyParkingSlot.Params(parkingSlotId))
        assertEquals(result, Either.Right(Unit))

        coVerify(exactly = 1) {
            parkingSlotRepository.occupyParkingSlot(parkingSlotId)
        }
    }
    @Test
    fun testAlreadyParked() = runTest {
        val parkingSlotId = "1"
        val newParkingSlotId = "1"
        val user = mockk<User>()

        every { user.currentParkingSlot?.id } returns parkingSlotId
        coEvery {
            userRepository.getUser()
        } returns Either.Right(user)

        val result = useCase(OccupyParkingSlot.Params(newParkingSlotId))
        assertEquals(result, Either.Left(AppError.AlreadyParking))

        verify {
            parkingSlotRepository wasNot Called
        }
    }
}