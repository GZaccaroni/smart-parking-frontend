package it.unibolss.smartparking.domain.usecases.user

import arrow.core.Either
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.user.NewUser
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SignUpUserTest {
    @MockK
    lateinit var validateUserName: ValidateUserName
    @MockK
    lateinit var validateUserEmail: ValidateUserEmail
    @MockK
    lateinit var validateUserPassword: ValidateUserPassword
    @MockK
    lateinit var userRepository: UserRepository

    lateinit var useCase: SignUpUser

    private val newUserName = "Test"
    private val newUserEmail = "test@test.it"
    private val newUserPassword = "pw"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = SignUpUser(validateUserName, validateUserEmail, validateUserPassword, userRepository)
    }

    @Test
    fun testHappyCase() = runTest {
        every {
            validateUserName(ValidateUserName.Params(newUserName))
        } returns true
        every {
            validateUserEmail(ValidateUserEmail.Params(newUserEmail))
        } returns true
        every {
            validateUserPassword(ValidateUserPassword.Params(newUserPassword))
        } returns true

        coEvery {
            userRepository.signUp(NewUser(newUserName, newUserEmail, newUserPassword))
        } returns Either.Right(Unit)

        val result = useCase(
            SignUpUser.Params(
                name = newUserName,
                email = newUserEmail,
                password = newUserPassword
            )
        )
        assertEquals(Either.Right(Unit), result)

        coVerify(exactly = 1) {
            userRepository.signUp(
                NewUser(newUserName, newUserEmail, newUserPassword)
            )
            validateUserName(ValidateUserName.Params(newUserName))
            validateUserEmail(ValidateUserEmail.Params(newUserEmail))
            validateUserPassword(ValidateUserPassword.Params(newUserPassword))
        }
    }

    @Test
    fun testInvalidName() = runTest {
        every {
            validateUserName(ValidateUserName.Params(newUserName))
        } returns false
        every {
            validateUserEmail(ValidateUserEmail.Params(newUserEmail))
        } returns true
        every {
            validateUserPassword(ValidateUserPassword.Params(newUserPassword))
        } returns true

        val result = useCase(
            SignUpUser.Params(
                name = newUserName,
                email = newUserEmail,
                password = newUserPassword
            )
        )
        assertEquals(Either.Left(AppError.InvalidUserName), result)
    }

    @Test
    fun testInvalidEmail() = runTest {
        every {
            validateUserName(ValidateUserName.Params(newUserName))
        } returns true
        every {
            validateUserEmail(ValidateUserEmail.Params(newUserEmail))
        } returns false
        every {
            validateUserPassword(ValidateUserPassword.Params(newUserPassword))
        } returns true

        val result = useCase(
            SignUpUser.Params(
                name = newUserName,
                password = newUserPassword,
                email = newUserEmail
            )
        )
        assertEquals(Either.Left(AppError.InvalidUserEmail), result)
    }

    @Test
    fun testInvalidPassword() = runTest {
        every {
            validateUserName(ValidateUserName.Params(newUserName))
        } returns true
        every {
            validateUserEmail(ValidateUserEmail.Params(newUserEmail))
        } returns true
        every {
            validateUserPassword(ValidateUserPassword.Params(newUserPassword))
        } returns false

        val result = useCase(
            SignUpUser.Params(
                name = newUserName,
                email = newUserEmail,
                password = newUserPassword
            )
        )
        assertEquals(Either.Left(AppError.InvalidUserPassword), result)
    }
}
