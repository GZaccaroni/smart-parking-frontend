package it.unibolss.smartparking.domain.usecases.user

import arrow.core.Either
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.user.AuthState
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ChangeUserPasswordTest {
    @MockK
    lateinit var validateUserPassword: ValidateUserPassword
    @MockK
    lateinit var userRepository: UserRepository

    lateinit var useCase: ChangeUserPassword

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = ChangeUserPassword(validateUserPassword, userRepository)
    }

    @Test
    fun testHappyCase() = runTest {
        val currentPassword = "current"
        val newPassword = "new"

        coEvery {
            userRepository.authState
        } returns AuthState.LoggedIn

        coEvery {
            userRepository.changeUserPassword(currentPassword, newPassword)
        } returns Either.Right(Unit)

        every {
            validateUserPassword(ValidateUserPassword.Params(newPassword))
        } returns true

        val result = useCase(ChangeUserPassword.Params(currentPassword, newPassword))
        assertEquals(Either.Right(Unit), result)

        coVerify(exactly = 1) {
            userRepository.changeUserPassword(currentPassword, newPassword)
        }
    }

    @Test
    fun testInvalidPassword() = runTest {
        val currentPassword = "current"
        val newPassword = "new"

        coEvery {
            userRepository.authState
        } returns AuthState.LoggedIn

        every {
            validateUserPassword(ValidateUserPassword.Params(newPassword))
        } returns false

        val result = useCase(ChangeUserPassword.Params(currentPassword, newPassword))
        assertEquals(Either.Left(AppError.InvalidUserPassword), result)

        coVerify(exactly = 0) {
            userRepository.changeUserPassword(currentPassword, newPassword)
        }
    }

    @Test
    fun testInvalidNewPasswordEqualToOld() = runTest {
        val currentPassword = "current"
        val newPassword = "current"

        coEvery {
            userRepository.authState
        } returns AuthState.LoggedIn

        every {
            validateUserPassword(ValidateUserPassword.Params(newPassword))
        } returns false

        val result = useCase(ChangeUserPassword.Params(currentPassword, newPassword))
        assertEquals(Either.Left(AppError.NewPasswordEqualToCurrent), result)

        coVerify(exactly = 0) {
            userRepository.changeUserPassword(currentPassword, newPassword)
        }
    }
}