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
class LogoutUserTest {
    @MockK
    lateinit var userRepository: UserRepository

    lateinit var useCase: LogoutUser

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = LogoutUser(userRepository)
    }

    @Test
    fun testHappyCase() = runTest {
        val userId = "id"

        coEvery {
            userRepository.authState
        } returns AuthState.LoggedIn(userId)

        coEvery {
            userRepository.logout()
        } returns Either.Right(Unit)

        val result = useCase()
        assertEquals(Either.Right(Unit), result)

        coVerify(exactly = 1) {
            userRepository.logout()
        }
    }

    @Test
    fun testNotLoggedIn() = runTest {
        coEvery {
            userRepository.authState
        } returns AuthState.Guest

        val result = useCase()
        assertEquals(Either.Right(Unit), result)

        coVerify(exactly = 0) {
            userRepository.logout()
        }
    }
}