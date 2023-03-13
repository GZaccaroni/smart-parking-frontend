package it.unibolss.smartparking.domain.usecases.user

import arrow.core.Either
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.user.AuthState
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import it.unibolss.smartparking.domain.usecases.common.invoke
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DeleteUserTest {
    @MockK
    lateinit var userRepository: UserRepository

    lateinit var useCase: DeleteUser

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = DeleteUser(userRepository)
    }

    @Test
    fun testHappyCase() = runTest {
        coEvery {
            userRepository.authState
        } returns AuthState.LoggedIn

        coEvery {
            userRepository.deleteUser()
        } returns Either.Right(Unit)

        val result = useCase()
        assertEquals(Either.Right(Unit), result)

        coVerify(exactly = 1) {
            userRepository.deleteUser()
        }
    }

    @Test
    fun testNotLoggedIn() = runTest {
        coEvery {
            userRepository.authState
        } returns AuthState.Guest

        coEvery {
            userRepository.deleteUser()
        } returns Either.Right(Unit)

        val result = useCase()
        assertEquals(Either.Left(AppError.Unauthorized), result)

        coVerify(exactly = 0) {
            userRepository.deleteUser()
        }
    }
}
