package it.unibolss.smartparking.presentation.screens.parkingslot.parkingslot

import app.cash.turbine.testIn
import arrow.core.Either
import io.mockk.MockKAnnotations
import io.mockk.Ordering
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import it.unibolss.smartparking.common.MainDispatcherRule
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.geo.GeoPosition
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlotState
import it.unibolss.smartparking.domain.usecases.parkingslot.FreeParkingSlot
import it.unibolss.smartparking.domain.usecases.parkingslot.IncrementParkingSlotOccupation
import it.unibolss.smartparking.domain.usecases.parkingslot.OccupyParkingSlot
import it.unibolss.smartparking.domain.usecases.parkingslot.ViewParkingSlot
import it.unibolss.smartparking.presentation.R
import it.unibolss.smartparking.presentation.common.appalert.AppAlert
import it.unibolss.smartparking.presentation.common.appalert.AppAlertState
import it.unibolss.smartparking.presentation.navigation.Router
import it.unibolss.smartparking.presentation.screens.parkingslot.parkingslot.ParkingSlotScreenViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ParkingSlotScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var viewParkingSlot: ViewParkingSlot

    @MockK
    lateinit var occupyParkingSlot: OccupyParkingSlot

    @MockK
    lateinit var incrementParkingSlotOccupation: IncrementParkingSlotOccupation

    @MockK
    lateinit var freeParkingSlot: FreeParkingSlot

    @MockK
    lateinit var router: Router

    lateinit var viewModel: ParkingSlotScreenViewModel

    private val parkingSlotId = "id"
    private val parkingSlotFree =
        ParkingSlot(
            id = parkingSlotId,
            position = GeoPosition(0.0, 0.0),
            state = ParkingSlotState.Free,
        )
    private val parkingSlotOccupied =
        ParkingSlot(
            id = parkingSlotId,
            position = GeoPosition(0.0, 0.0),
            state = ParkingSlotState.Occupied(Instant.DISTANT_FUTURE, currentUser = false),
        )
    private val currentParkingSlotOccupied =
        ParkingSlot(
            id = parkingSlotId,
            position = GeoPosition(0.0, 0.0),
            state = ParkingSlotState.Occupied(Instant.DISTANT_FUTURE, currentUser = true),
        )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ParkingSlotScreenViewModel(
            parkingSlotId,
            viewParkingSlot,
            occupyParkingSlot,
            incrementParkingSlotOccupation,
            freeParkingSlot,
            router
        )
    }

    @Test
    fun testViewHappyCase() = runTest {
        val parkingSlot = mockk<ParkingSlot>()
        coEvery {
            viewParkingSlot(ViewParkingSlot.Params(parkingSlotId))
        } returns Either.Right(parkingSlot)

        assertEquals(null, viewModel.uiState.value.parkingSlot)

        advanceUntilIdle()
        assertEquals(parkingSlot, viewModel.uiState.value.parkingSlot)
        coVerify(exactly = 1) {
            viewParkingSlot(ViewParkingSlot.Params(parkingSlotId))
        }
    }

    @Test
    fun testViewFailureCase() = runTest {
        val alertTurbine = viewModel.alertState.testIn(backgroundScope)

        val appError = mockk<AppError>()
        coEvery {
            viewParkingSlot(ViewParkingSlot.Params(parkingSlotId))
        } returns Either.Left(appError)
        every {
            router.popBackStack()
        } just Runs

        assertEquals(null, viewModel.uiState.value.parkingSlot)

        advanceUntilIdle()
        alertTurbine.skipItems(1)
        assertEquals(AppAlertState.Some(AppAlert.Error(appError)), alertTurbine.awaitItem())

        coVerify(exactly = 1) {
            viewParkingSlot(ViewParkingSlot.Params(parkingSlotId))
        }
    }

    @Test
    fun testOccupy() = runTest {
        val alertTurbine = viewModel.alertState.testIn(backgroundScope)

        val stopEnd = LocalDateTime(2000, 10, 1, 0, 0)
        val stopEndInstant = stopEnd.toInstant(TimeZone.currentSystemDefault())
        coEvery {
            occupyParkingSlot(
                OccupyParkingSlot.Params(
                    parkingSlotId,
                    stopEndInstant
                )
            )
        } returns Either.Right(Unit)

        preloadParkingSlot(parkingSlotFree)
        viewModel.occupy(stopEnd)

        advanceUntilIdle()
        alertTurbine.skipItems(1)
        assertEquals(
            AppAlertState.Some(AppAlert.Text(R.string.app_success_parking_slot_occupy)),
            alertTurbine.awaitItem()
        )

        coVerify(ordering = Ordering.SEQUENCE) {
            viewParkingSlot(ViewParkingSlot.Params(parkingSlotId))
            occupyParkingSlot(OccupyParkingSlot.Params(parkingSlotId, stopEndInstant))
        }
    }

    @Test(expected = IllegalStateException::class)
    fun testCurrentOccupy() = runTest {
        preloadParkingSlot(parkingSlotOccupied)
        viewModel.occupy(mockk())
    }

    @Test
    fun testIncrementOccupation() = runTest {
        val alertTurbine = viewModel.alertState.testIn(backgroundScope)

        val stopEnd = LocalDateTime(2000, 10, 1, 0, 0)
        val stopEndInstant = stopEnd.toInstant(TimeZone.currentSystemDefault())
        coEvery {
            incrementParkingSlotOccupation(
                IncrementParkingSlotOccupation.Params(
                    stopEndInstant
                )
            )
        } returns Either.Right(Unit)

        preloadParkingSlot(currentParkingSlotOccupied)
        viewModel.incrementOccupation(stopEnd)

        advanceUntilIdle()
        alertTurbine.skipItems(1)
        assertEquals(
            AppAlertState.Some(AppAlert.Text(R.string.app_success_parking_slot_increment_occupation)),
            alertTurbine.awaitItem()
        )

        coVerify(ordering = Ordering.SEQUENCE) {
            viewParkingSlot(ViewParkingSlot.Params(parkingSlotId))
            incrementParkingSlotOccupation(
                IncrementParkingSlotOccupation.Params(
                    stopEndInstant
                )
            )
        }
    }

    @Test(expected = IllegalStateException::class)
    fun testCurrentIncrementOccupation() = runTest {
        preloadParkingSlot(parkingSlotOccupied)
        viewModel.incrementOccupation(mockk())
    }

    @Test
    fun testFree() = runTest {
        val alertTurbine = viewModel.alertState.testIn(backgroundScope)

        coEvery {
            freeParkingSlot(Unit)
        } returns Either.Right(Unit)

        preloadParkingSlot(currentParkingSlotOccupied)
        viewModel.free()

        advanceUntilIdle()
        alertTurbine.skipItems(1)
        assertEquals(
            AppAlertState.Some(AppAlert.Text(R.string.app_success_parking_slot_free)),
            alertTurbine.awaitItem()
        )

        coVerify(ordering = Ordering.SEQUENCE) {
            viewParkingSlot(ViewParkingSlot.Params(parkingSlotId))
            freeParkingSlot(Unit)
        }
    }

    @Test(expected = IllegalStateException::class)
    fun testNonCurrentOccupy() = runTest {
        preloadParkingSlot(parkingSlotOccupied)
        viewModel.occupy(mockk())
    }

    @Test(expected = IllegalStateException::class)
    fun testNonCurrentIncrementOccupation() = runTest {
        preloadParkingSlot(parkingSlotOccupied)
        viewModel.incrementOccupation(mockk())
    }

    @Test(expected = IllegalStateException::class)
    fun testNonCurrentFree() = runTest {
        preloadParkingSlot(parkingSlotOccupied)
        viewModel.free()
    }
    private fun TestScope.preloadParkingSlot(parkingSlot: ParkingSlot) {
        coEvery {
            viewParkingSlot(ViewParkingSlot.Params(parkingSlotId))
        } returns Either.Right(parkingSlot)
        assertEquals(null, viewModel.uiState.value.parkingSlot)

        advanceUntilIdle()
        assertEquals(parkingSlot, viewModel.uiState.value.parkingSlot)
    }
}
