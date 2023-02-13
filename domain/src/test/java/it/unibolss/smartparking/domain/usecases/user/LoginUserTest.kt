package it.unibolss.smartparking.domain.usecases.user

import arrow.core.Either
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.user.AuthState
import it.unibolss.smartparking.domain.entities.user.User
import it.unibolss.smartparking.domain.entities.user.UserCredentials
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import it.unibolss.smartparking.domain.usecases.common.invoke
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginUserTest {
    @MockK
    lateinit var validateUserEmail: ValidateUserEmail
    @MockK
    lateinit var userRepository: UserRepository

    lateinit var useCase: LoginUser

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = LoginUser(validateUserEmail, userRepository)
    }

    @Test
    fun testHappyCase() = runTest {
        val userCredentials = UserCredentials("test@test.it", "pw")

        every {
            validateUserEmail(ValidateUserEmail.Params(userCredentials.email))
        } returns true

        coEvery {
            userRepository.login(userCredentials)
        } returns Either.Right(Unit)

        val result = useCase(LoginUser.Params(userCredentials))
        assertEquals(Either.Right(Unit), result)

        coVerify(exactly = 1) {
            userRepository.login(userCredentials)
            validateUserEmail(ValidateUserEmail.Params(userCredentials.email))
        }
    }

    @Test
    fun testInvalidEmail() = runTest {
        val userCredentials = UserCredentials("test@test.it", "pw")

        every {
            validateUserEmail(ValidateUserEmail.Params(userCredentials.email))
        } returns false

        val result = useCase(LoginUser.Params(userCredentials))
        assertEquals(Either.Left(AppError.InvalidUserEmail), result)

        coVerify(exactly = 1) {
            validateUserEmail(ValidateUserEmail.Params(userCredentials.email))
        }
    }
}