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
        val userId = "id"
        val currentPassword = "current"
        val newPassword = "new"

        coEvery {
            userRepository.authState
        } returns AuthState.LoggedIn(userId)

        coEvery {
            userRepository.changeUserPassword(currentPassword, newPassword)
        } returns Either.Right(Unit)

        every {
            validateUserPassword(ValidateUserPassword.Params(newPassword))
        } returns true

        val result = useCase(ChangeUserPassword.Params(currentPassword, newPassword))
        assertEquals(result, Either.Right(Unit))

        coVerify(exactly = 1) {
            userRepository.changeUserPassword(currentPassword, newPassword)
        }
    }

    @Test
    fun testInvalidPassword() = runTest {
        val userId = "id"
        val currentPassword = "current"
        val newPassword = "new"

        coEvery {
            userRepository.authState
        } returns AuthState.LoggedIn(userId)

        every {
            validateUserPassword(ValidateUserPassword.Params(newPassword))
        } returns false

        val result = useCase(ChangeUserPassword.Params(currentPassword, newPassword))
        assertEquals(result, Either.Left(AppError.InvalidUserPassword))

        coVerify(exactly = 0) {
            userRepository.changeUserPassword(currentPassword, newPassword)
        }
    }
}