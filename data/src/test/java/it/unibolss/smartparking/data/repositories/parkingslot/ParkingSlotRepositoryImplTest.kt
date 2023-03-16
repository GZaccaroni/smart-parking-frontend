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
