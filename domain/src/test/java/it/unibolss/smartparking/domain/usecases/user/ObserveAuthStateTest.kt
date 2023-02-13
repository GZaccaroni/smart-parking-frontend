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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ObserveAuthStateTest {
    @MockK
    lateinit var userRepository: UserRepository

    lateinit var useCase: ObserveAuthState

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = ObserveAuthState(userRepository)
    }

    @Test
    fun testHappyCase() = runTest {
        val userId = "id"
        val flow = flowOf(AuthState.LoggedIn(userId))

        coEvery {
            userRepository.authStateFlow
        } returns flow

        val result = useCase()
        assertEquals(flow, result)

        coVerify(exactly = 1) {
            userRepository.authStateFlow
        }
    }
}