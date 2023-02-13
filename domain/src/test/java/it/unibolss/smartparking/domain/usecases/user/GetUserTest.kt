package it.unibolss.smartparking.domain.usecases.user

import arrow.core.Either
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.user.AuthState
import it.unibolss.smartparking.domain.entities.user.User
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import it.unibolss.smartparking.domain.usecases.common.invoke
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetUserTest {
    @MockK
    lateinit var userRepository: UserRepository

    lateinit var useCase: GetUser

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetUser(userRepository)
    }

    @Test
    fun testHappyCase() = runTest {
        val user = mockk<User>()
        val userId = "id"

        coEvery {
            userRepository.authState
        } returns AuthState.LoggedIn(userId)

        coEvery {
            userRepository.getUser()
        } returns Either.Right(user)

        val result = useCase()
        assertEquals(result, Either.Right(user))

        coVerify(exactly = 1) {
            userRepository.getUser()
        }
    }

    @Test
    fun testNotLoggedIn() = runTest {
        coEvery {
            userRepository.authState
        } returns AuthState.Guest

        val result = useCase()
        assertEquals(result, Either.Left(AppError.Unauthorized))

        coVerify(exactly = 0) {
            userRepository.getUser()
        }
    }
}