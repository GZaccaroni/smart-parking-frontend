package it.unibolss.smartparking.presentation.screens.changepassword

import app.cash.turbine.testIn
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
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.usecases.user.ChangeUserPassword
import it.unibolss.smartparking.domain.usecases.user.ValidateUserPassword
import it.unibolss.smartparking.presentation.common.appalert.AppAlert
import it.unibolss.smartparking.presentation.common.appalert.AppAlertState
import it.unibolss.smartparking.presentation.navigation.Router
import it.unibolss.smartparking.presentation.screens.parkingslots.ParkingSlotsRoute
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class ChangePasswordScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var changeUserPassword: ChangeUserPassword

    @MockK
    lateinit var validateUserPassword: ValidateUserPassword

    @MockK
    lateinit var router: Router

    lateinit var viewModel: ChangePasswordScreenViewModel

    private val validPassword = "password"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockCommon()
        viewModel = ChangePasswordScreenViewModel(
            changeUserPassword,
            validateUserPassword,
            router
        )
    }

    @Test
    fun testHappyCase() = runTest {
        every {
            router.popBackStack()
        } returns Unit

        advanceUntilIdle()
        assertFalse(viewModel.uiState.value.submitEnabled)

        viewModel.setCurrentPassword(validPassword)

        advanceUntilIdle()
        assertFalse(viewModel.uiState.value.isCurrentPasswordError)
        assertFalse(viewModel.uiState.value.submitEnabled)

        viewModel.setNewPassword(validPassword)

        advanceUntilIdle()
        assertFalse(viewModel.uiState.value.isNewPasswordError)
        assertTrue(viewModel.uiState.value.submitEnabled)

        coEvery {
            changeUserPassword(
                ChangeUserPassword.Params(
                    currentPassword = validPassword,
                    newPassword = validPassword,
                )
            )
        } returns Either.Right(Unit)

        viewModel.submit()

        advanceUntilIdle()
        coVerify(exactly = 1) {
            router.popBackStack()
        }
    }

    @Test
    fun testFailedValidationCase() = runTest {
        advanceUntilIdle()

        viewModel.setCurrentPassword("invalidpw1")

        advanceUntilIdle()
        assertTrue(viewModel.uiState.value.isCurrentPasswordError)

        viewModel.setNewPassword("invalidpw2")

        advanceUntilIdle()
        assertTrue(viewModel.uiState.value.isNewPasswordError)

        advanceUntilIdle()
        assertFalse(viewModel.uiState.value.submitEnabled)
    }

    @Test
    fun testFailingCase() = runTest {
        val appError = mockk<AppError>()
        val alertTurbine = viewModel.alertState.testIn(backgroundScope)
        viewModel.setCurrentPassword(validPassword)
        viewModel.setNewPassword(validPassword)

        advanceUntilIdle()
        coEvery {
            changeUserPassword(
                ChangeUserPassword.Params(
                    currentPassword = validPassword,
                    newPassword = validPassword,
                )
            )
        } returns Either.Left(appError)

        viewModel.submit()
        advanceUntilIdle()

        alertTurbine.skipItems(1)
        assertEquals(AppAlertState.Some(AppAlert.Error(appError)), alertTurbine.awaitItem())
    }

    @Test(expected = IllegalStateException::class)
    fun testSubmitWithEmptyFields() = runTest {
        viewModel.submit()
    }

    private fun mockCommon() {
        every {
            validateUserPassword.invoke(ValidateUserPassword.Params(validPassword))
        } returns true
        every {
            validateUserPassword.invoke(neq(ValidateUserPassword.Params(validPassword)))
        } returns false
        every {
            router.navigateTo(ParkingSlotsRoute, any())
        } just Runs
    }
}
