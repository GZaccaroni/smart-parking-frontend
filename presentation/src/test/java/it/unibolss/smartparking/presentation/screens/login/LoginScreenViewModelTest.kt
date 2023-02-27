package it.unibolss.smartparking.presentation.screens.login

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
import it.unibolss.smartparking.domain.usecases.user.LoginUser
import it.unibolss.smartparking.domain.usecases.user.ValidateUserEmail
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
internal class LoginScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var loginUser: LoginUser

    @MockK
    lateinit var validateUserEmail: ValidateUserEmail

    @MockK
    lateinit var validateUserPassword: ValidateUserPassword

    @MockK
    lateinit var router: Router

    lateinit var viewModel: LoginScreenViewModel

    private val successEmail = "email"
    private val successPassword = "password"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockCommon()
        viewModel = LoginScreenViewModel(
            loginUser,
            validateUserEmail,
            validateUserPassword,
            router
        )
    }

    @Test
    fun testHappyCase() = runTest {
        every {
            router.navigateTo(ParkingSlotsRoute)
        } returns Unit

        advanceUntilIdle()
        assertFalse(viewModel.uiState.value.submitEnabled)

        viewModel.setEmail(successEmail)

        advanceUntilIdle()
        assertFalse(viewModel.uiState.value.isEmailError)
        assertFalse(viewModel.uiState.value.submitEnabled)

        viewModel.setPassword(successPassword)

        advanceUntilIdle()
        assertFalse(viewModel.uiState.value.isPasswordError)
        assertTrue(viewModel.uiState.value.submitEnabled)

        coEvery {
            loginUser.invoke(LoginUser.Params(successEmail, successPassword))
        } returns Either.Right(Unit)

        viewModel.submit()

        advanceUntilIdle()
        coVerify(exactly = 1) {
            router.navigateTo(ParkingSlotsRoute, any())
        }
    }

    @Test
    fun testFailedValidationCase() = runTest {
        advanceUntilIdle()

        viewModel.setEmail("invalidemail")

        advanceUntilIdle()
        assertTrue(viewModel.uiState.value.isEmailError)

        viewModel.setPassword("invalidpw")

        advanceUntilIdle()
        assertTrue(viewModel.uiState.value.isPasswordError)

        advanceUntilIdle()
        assertFalse(viewModel.uiState.value.submitEnabled)
        assertTrue(viewModel.uiState.value.isEmailError)
    }

    @Test
    fun testFailingCase() = runTest {
        val appError = mockk<AppError>()
        val alertTurbine = viewModel.alertState.testIn(backgroundScope)

        viewModel.setEmail(successEmail)
        viewModel.setPassword(successPassword)
        advanceUntilIdle()

        coEvery {
            loginUser.invoke(LoginUser.Params(successEmail, successPassword))
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
            validateUserEmail.invoke(eq(ValidateUserEmail.Params(successEmail)))
        } returns true
        every {
            validateUserEmail.invoke(neq(ValidateUserEmail.Params(successEmail)))
        } returns false
        every {
            validateUserPassword.invoke(ValidateUserPassword.Params(successPassword))
        } returns true
        every {
            validateUserPassword.invoke(neq(ValidateUserPassword.Params(successPassword)))
        } returns false
        every {
            router.navigateTo(ParkingSlotsRoute, any())
        } just Runs
    }
}
