package it.unibolss.smartparking.domain.usecases.user

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import it.unibolss.smartparking.domain.entities.user.AuthState
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import it.unibolss.smartparking.domain.usecases.common.invoke
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetAuthStateTest {
    @MockK
    lateinit var userRepository: UserRepository

    lateinit var useCase: GetAuthState

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetAuthState(userRepository)
    }

    @Test
    fun testHappyCase() = runTest {
        val loggedIn = AuthState.LoggedIn

        coEvery {
            userRepository.authState
        } returns loggedIn

        val result = useCase()
        assertEquals(loggedIn, result)

        coVerify(exactly = 1) {
            userRepository.authState
        }
    }
}
