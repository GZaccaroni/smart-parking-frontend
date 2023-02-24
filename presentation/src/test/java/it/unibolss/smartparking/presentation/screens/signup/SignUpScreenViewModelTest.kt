package it.unibolss.smartparking.presentation.screens.signup

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
import it.unibolss.smartparking.domain.usecases.user.SignUpUser
import it.unibolss.smartparking.domain.usecases.user.ValidateUserEmail
import it.unibolss.smartparking.domain.usecases.user.ValidateUserName
import it.unibolss.smartparking.domain.usecases.user.ValidateUserPassword
import it.unibolss.smartparking.presentation.common.appalert.AppAlert
import it.unibolss.smartparking.presentation.navigation.Router
import it.unibolss.smartparking.presentation.screens.parkingslots.ParkingSlotsRoute
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class SignUpScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    lateinit var signUpUser: SignUpUser

    @MockK
    lateinit var validateUserName: ValidateUserName

    @MockK
    lateinit var validateUserEmail: ValidateUserEmail

    @MockK
    lateinit var validateUserPassword: ValidateUserPassword

    @MockK
    lateinit var router: Router

    lateinit var viewModel: SignUpScreenViewModel

    private val successName = "name"
    private val successEmail = "email"
    private val successPassword = "password"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockCommon()
        viewModel = SignUpScreenViewModel(
            signUpUser,
            validateUserName,
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
        assertFalse(viewModel.submitButtonEnabled.value)

        viewModel.setName(successName)

        advanceUntilIdle()
        assertFalse(viewModel.nameError.value)
        assertFalse(viewModel.submitButtonEnabled.value)

        viewModel.setEmail(successEmail)

        advanceUntilIdle()
        assertFalse(viewModel.emailError.value)
        assertFalse(viewModel.submitButtonEnabled.value)

        viewModel.setPassword(successPassword)

        advanceUntilIdle()
        assertFalse(viewModel.passwordError.value)
        assertTrue(viewModel.submitButtonEnabled.value)

        coEvery {
            signUpUser.invoke(SignUpUser.Params(successName, successEmail, successPassword))
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

        viewModel.setName("invalidname")

        advanceUntilIdle()
        assertTrue(viewModel.nameError.value)

        viewModel.setEmail("invalidemail")

        advanceUntilIdle()
        assertTrue(viewModel.emailError.value)

        viewModel.setPassword("invalidpw")

        advanceUntilIdle()
        assertTrue(viewModel.passwordError.value)

        advanceUntilIdle()
        assertFalse(viewModel.submitButtonEnabled.value)
        assertTrue(viewModel.emailError.value)
    }

    @Test
    fun testFailingCase() = runTest {
        val appError = mockk<AppError>()
        val deferred = async {
            viewModel.snackbar.first()
        }
        viewModel.setName(successName)
        viewModel.setEmail(successEmail)
        viewModel.setPassword(successPassword)
        advanceUntilIdle()

        coEvery {
            signUpUser.invoke(SignUpUser.Params(successName, successEmail, successPassword))
        } returns Either.Left(appError)

        viewModel.submit()
        advanceUntilIdle()

        val result = deferred.await() as AppAlert.Error
        assertEquals(result.error, appError)
    }

    @Test(expected = IllegalStateException::class)
    fun testSubmitWithEmptyFields() = runTest {
        viewModel.submit()
    }

    private fun mockCommon() {
        every {
            validateUserName.invoke(eq(ValidateUserName.Params(successName)))
        } returns true
        every {
            validateUserName.invoke(neq(ValidateUserName.Params(successName)))
        } returns false
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
