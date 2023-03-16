package it.unibolss.smartparking.data.repositories.parkingslot

import arrow.core.Either
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import it.unibolss.smartparking.data.datasources.parkingslot.ParkingSlotDataSource
import it.unibolss.smartparking.data.datasources.user.AuthenticationDataSource
import it.unibolss.smartparking.data.models.common.AppErrorDto
import it.unibolss.smartparking.data.models.parkingslot.GeoPositionDto
import it.unibolss.smartparking.data.models.parkingslot.ParkingSlotDto
import it.unibolss.smartparking.data.models.user.AuthenticationInfo
import it.unibolss.smartparking.data.models.user.IncrementParkingSlotOccupationBody
import it.unibolss.smartparking.data.models.user.OccupyParkingSlotBody
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.geo.GeoPosition
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlotState
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.time.Duration.Companion.hours

@OptIn(ExperimentalCoroutinesApi::class)
class ParkingSlotRepositoryImplTest {
    @MockK(relaxUnitFun = true)
    private lateinit var parkingSlotDataSource: ParkingSlotDataSource

    @MockK(relaxUnitFun = true)
    private lateinit var authenticationDataSource: AuthenticationDataSource

    private lateinit var parkingSlotRepository: ParkingSlotRepository

    private val currentUserId = "123"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every {
            authenticationDataSource.getCurrentAuthInfo()
        } returns AuthenticationInfo(
            userId = currentUserId,
            authToken = "test"
        )
        parkingSlotRepository = ParkingSlotRepositoryImpl(
            authenticationDataSource,
            parkingSlotDataSource
        )
    }

    @Test
    fun getParkingSlots() = runTest {
        val parkingSlotId1 = "testId1"
        val parkingSlotId2 = "testId2"
        val parkingSlotId3 = "testId3"
        val parkingSlotDto1 = sampleParkingSlotDto(parkingSlotId1, currentUserId)
        val parkingSlotDto2 = sampleParkingSlotDto(parkingSlotId2, "abc")
        val parkingSlotDto3 = sampleParkingSlotDto(parkingSlotId3, null)

        val parkingSlotsDto = listOf(
            parkingSlotDto1,
            parkingSlotDto2,
            parkingSlotDto3
        )
        val position = GeoPosition(0.0, 0.0)
        val radius = 50.0
        coEvery {
            parkingSlotDataSource.getParkingSlots(
                latitude = position.latitude,
                longitude = position.longitude,
                radius = radius,
            )
        } returns parkingSlotsDto

        val result = parkingSlotRepository.getParkingSlots(position, radius)

        assertIs<Either.Right<List<ParkingSlot>>>(result)
        assertEquals(result.value.size, 3)

        assertEntityCorrect(
            dto = parkingSlotDto1,
            entity = result.value[0]
        )
        assertEntityCorrect(
            dto = parkingSlotDto2,
            entity = result.value[1]
        )
        assertEntityCorrect(
            dto = parkingSlotDto3,
            entity = result.value[2]
        )
    }

    @Test
    fun getParkingSlotsFailure() = runTest {
        val position = GeoPosition(0.0, 0.0)
        val radius = 50.0
        coEvery {
            parkingSlotDataSource.getParkingSlots(
                latitude = position.latitude,
                longitude = position.longitude,
                radius = radius,
            )
        } throws sampleHTTPException(AppErrorDto.Unauthorized)

        val result = parkingSlotRepository.getParkingSlots(position, radius)

        assertEquals(Either.Left(AppError.Unauthorized), result)
    }

    @Test
    fun getParkingSlot() = runTest {
        val parkingSlotId = "testId1"
        val parkingSlotDto = sampleParkingSlotDto(parkingSlotId, null)

        coEvery {
            parkingSlotDataSource.getParkingSlot(parkingSlotId)
        } returns parkingSlotDto

        val result = parkingSlotRepository.getParkingSlot(parkingSlotId)

        assertIs<Either.Right<ParkingSlot>>(result)

        assertEntityCorrect(
            dto = parkingSlotDto,
            entity = result.value,
        )
    }

    @Test
    fun getParkingSlotFailure() = runTest {
        val parkingSlotId = "testId1"

        coEvery {
            parkingSlotDataSource.getParkingSlot(parkingSlotId)
        } throws sampleHTTPException(AppErrorDto.Unauthorized)

        val result = parkingSlotRepository.getParkingSlot(parkingSlotId)

        assertEquals(Either.Left(AppError.Unauthorized), result)
    }

    @Test
    fun getCurrentParkingSlot() = runTest {
        val parkingSlotId = "testId"
        val parkingSlotDto = sampleParkingSlotDto(parkingSlotId, null)

        coEvery {
            parkingSlotDataSource.getCurrentParkingSlot()
        } returns parkingSlotDto

        val result = parkingSlotRepository.getCurrentParkingSlot()

        assertIs<Either.Right<ParkingSlot>>(result)

        assertEntityCorrect(
            dto = parkingSlotDto,
            entity = result.value,
        )
    }

    @Test
    fun getCurrentParkingSlotWhenNoneOccupied() = runTest {
        coEvery {
            parkingSlotDataSource.getCurrentParkingSlot()
        } throws sampleHTTPException(AppErrorDto.ParkingSlotNotFound)

        val result = parkingSlotRepository.getCurrentParkingSlot()

        assertEquals(
            Either.Right(null),
            result
        )
    }

    @Test
    fun occupyParkingSlot() = runTest {
        val parkingSlotId = "testId"
        val stopEnd = Clock.System.now().plus(1.hours)
        val result = parkingSlotRepository.occupyParkingSlot(
            parkingSlotId,
            stopEnd,
        )

        assertEquals(Either.Right(Unit), result)

        coVerify {
            parkingSlotDataSource.occupyParkingSlot(
                parkingSlotId,
                OccupyParkingSlotBody(
                    stopEnd = stopEnd
                )
            )
        }
    }

    @Test
    fun occupyParkingSlotFailure() = runTest {
        val parkingSlotId = "testId"
        val stopEnd = Clock.System.now().plus(1.hours)

        coEvery {
            parkingSlotDataSource.occupyParkingSlot(parkingSlotId, any())
        } throws sampleHTTPException(AppErrorDto.ParkingSlotNotFound)
        val result = parkingSlotRepository.occupyParkingSlot(
            parkingSlotId,
            stopEnd,
        )

        assertEquals(Either.Left(AppError.ParkingSlotNotFound), result)
    }

    private fun sampleHTTPException(errorCode: AppErrorDto): HttpException {
        val sampleResponse =
            """
                {
                    "errorCode": "${errorCode.name}"
                }
            """
        val responseMediaType = MediaType.get("application/json")
        val responseBody = ResponseBody.create(responseMediaType, sampleResponse)

        return HttpException(
            Response.error<String>(500, responseBody)
        )
    }
}
