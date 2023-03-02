package it.unibolss.smartparking.presentation.screens.parkingslots

import arrow.core.Either
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import it.unibolss.smartparking.common.MainDispatcherRule
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import it.unibolss.smartparking.domain.usecases.parkingslot.FindParkingSlots
import it.unibolss.smartparking.domain.usecases.parkingslot.ViewCurrentParkingSlot
import it.unibolss.smartparking.domain.usecases.user.DeleteUser
import it.unibolss.smartparking.domain.usecases.user.LogoutUser
import it.unibolss.smartparking.presentation.navigation.Router
import it.unibolss.smartparking.presentation.screens.changepassword.ChangePasswordRoute
import it.unibolss.smartparking.presentation.screens.login.LoginRoute
import it.unibolss.smartparking.presentation.screens.parkingslot.ParkingSlotRoute
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ParkingSlotsScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var viewCurrentParkingSlot: ViewCurrentParkingSlot

    @MockK
    lateinit var findParkingSlots: FindParkingSlots

    @MockK
    lateinit var logoutUser: LogoutUser

    @MockK
    lateinit var deleteUser: DeleteUser

    @MockK
    lateinit var router: Router

    lateinit var viewModel: ParkingSlotsScreenViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ParkingSlotsScreenViewModel(
            viewCurrentParkingSlot,
            findParkingSlots,
            logoutUser,
            deleteUser,
            router
        )
    }

    @Test
    fun testViewHappyCase() = runTest {
        val parkingSlot = mockk<ParkingSlot>()
        coEvery {
            viewCurrentParkingSlot(Unit)
        } returns Either.Right(parkingSlot)

        assertEquals(emptyList<ParkingSlot>(), viewModel.uiState.value.parkingSlots)
        assertEquals(null, viewModel.uiState.value.currentParkingSlot)

        advanceUntilIdle()
        assertEquals(parkingSlot, viewModel.uiState.value.currentParkingSlot)
        coVerify(exactly = 1) {
            viewCurrentParkingSlot(Unit)
        }
    }

    @Test
    fun testViewParkingSlot() = runTest {
        val parkingSlotId = "id"
        val parkingSlot = mockk<ParkingSlot>()

        coEvery {
            viewCurrentParkingSlot(Unit)
        } returns Either.Right(parkingSlot)
        every {
            parkingSlot.id
        } returns parkingSlotId
        every {
            router.navigateTo(ParkingSlotRoute(parkingSlotId))
        } just Runs

        advanceUntilIdle()
        viewModel.viewParkingSlot(parkingSlot)

        coVerify(exactly = 1) {
            router.navigateTo(ParkingSlotRoute(parkingSlotId))
        }
    }

    @Test
    fun testChangePassword() = runTest {
        val parkingSlot = mockk<ParkingSlot>()

        coEvery {
            viewCurrentParkingSlot(Unit)
        } returns Either.Right(parkingSlot)
        every {
            router.navigateTo(ChangePasswordRoute)
        } just Runs

        advanceUntilIdle()
        viewModel.changePassword()

        coVerify(exactly = 1) {
            router.navigateTo(ChangePasswordRoute)
        }
    }

    @Test
    fun testLogout() = runTest {
        val parkingSlot = mockk<ParkingSlot>()

        coEvery {
            viewCurrentParkingSlot(Unit)
        } returns Either.Right(parkingSlot)
        coEvery {
            logoutUser(Unit)
        } returns Either.Right(Unit)
        every {
            router.navigateTo(LoginRoute, any())
        } just Runs

        viewModel.logout()
        advanceUntilIdle()

        coVerify(exactly = 1) {
            router.navigateTo(LoginRoute, any())
        }
    }

    @Test
    fun testDeleteUser() = runTest {
        val parkingSlot = mockk<ParkingSlot>()

        coEvery {
            viewCurrentParkingSlot(Unit)
        } returns Either.Right(parkingSlot)
        coEvery {
            deleteUser(Unit)
        } returns Either.Right(Unit)
        every {
            router.navigateTo(LoginRoute, any())
        } just Runs

        viewModel.deleteUser()
        advanceUntilIdle()

        coVerify(exactly = 1) {
            router.navigateTo(LoginRoute, any())
        }
    }
}
