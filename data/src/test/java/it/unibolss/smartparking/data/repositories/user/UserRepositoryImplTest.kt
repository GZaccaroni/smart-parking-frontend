package it.unibolss.smartparking.data.repositories.user

import arrow.core.Either
import io.mockk.MockKAnnotations
import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import it.unibolss.smartparking.data.datasources.user.AuthenticationDataSource
import it.unibolss.smartparking.data.datasources.user.UserDataSource
import it.unibolss.smartparking.data.models.user.AuthenticationInfo
import it.unibolss.smartparking.data.models.user.AuthenticationResult
import it.unibolss.smartparking.data.models.user.ChangePasswordRequestBody
import it.unibolss.smartparking.data.models.user.LoginRequestBody
import it.unibolss.smartparking.data.models.user.SignUpRequestBody
import it.unibolss.smartparking.data.models.user.UserDto
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.user.AuthState
import it.unibolss.smartparking.domain.entities.user.NewUser
import it.unibolss.smartparking.domain.entities.user.User
import it.unibolss.smartparking.domain.entities.user.UserCredentials
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
internal class UserRepositoryImplTest {
    @MockK(relaxUnitFun = true)
    private lateinit var userDataSource: UserDataSource

    @MockK(relaxUnitFun = true)
    private lateinit var authenticationDataSource: AuthenticationDataSource

    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userRepository = UserRepositoryImpl(authenticationDataSource, userDataSource)
    }

    @Test
    fun getAuthStateWhenLoggedIn() {
        every {
            authenticationDataSource.getCurrentAuthInfo()
        } returns AuthenticationInfo("userId", "authToken")

        val authState = userRepository.authState

        assertEquals(AuthState.LoggedIn, authState)
    }

    @Test
    fun getAuthStateWhenGuest() {
        every {
            authenticationDataSource.getCurrentAuthInfo()
        } returns null

        val authState = userRepository.authState

        assertEquals(AuthState.Guest, authState)
    }

    @Test
    fun signUpSuccess() = runTest {
        val userID = "uID"
        val authToken = "token"
        val newUser = NewUser(
            name = "Giulio",
            email = "giulio@example.com",
            password = "giulioexample"
        )
        coEvery {
            userDataSource.signUp(any())
        } returns AuthenticationResult(authToken, userID)

        val result = userRepository.signUp(newUser)

        assertEquals(
            Either.Right(Unit),
            result
        )
        coVerify(ordering = Ordering.SEQUENCE) {
            userDataSource.signUp(
                SignUpRequestBody(
                    name = newUser.name,
                    email = newUser.email,
                    password = newUser.password,
                )
            )
            authenticationDataSource.setCurrentAuthInfo(AuthenticationInfo(userID, authToken))
        }
    }

    @Test
    fun signUpFailure() = runTest {
        val newUser = NewUser(
            name = "Giulio",
            email = "giulio@example.com",
            password = "giulioexample"
        )
        coEvery {
            userDataSource.signUp(any())
        } throws sampleHTTPException()

        val result = userRepository.signUp(newUser)

        assertEquals(
            Either.Left(AppError.Unauthorized),
            result
        )
        coVerify(exactly = 0) {
            authenticationDataSource.setCurrentAuthInfo(any())
        }
    }

    @Test
    fun loginSuccess() = runTest {
        val userID = "uID"
        val authToken = "token"
        val newUser = UserCredentials(
            email = "giulio@example.com",
            password = "giulioexample"
        )
        coEvery {
            userDataSource.login(any())
        } returns AuthenticationResult(authToken, userID)

        val result = userRepository.login(newUser)

        assertEquals(
            Either.Right(Unit),
            result
        )
        coVerify(ordering = Ordering.SEQUENCE) {
            userDataSource.login(
                LoginRequestBody(
                    email = newUser.email,
                    password = newUser.password,
                )
            )
            authenticationDataSource.setCurrentAuthInfo(AuthenticationInfo(userID, authToken))
        }
    }

    @Test
    fun loginFailure() = runTest {
        val newUser = UserCredentials(
            email = "giulio@example.com",
            password = "giulioexample"
        )
        coEvery {
            userDataSource.login(any())
        } throws sampleHTTPException()

        val result = userRepository.login(newUser)

        assertEquals(
            Either.Left(AppError.Unauthorized),
            result
        )
        coVerify(exactly = 0) {
            authenticationDataSource.setCurrentAuthInfo(any())
        }
    }

    @Test
    fun logout() = runTest {
        val result = userRepository.logout()

        assertEquals(
            Either.Right(Unit),
            result
        )
        coVerify(exactly = 1) {
            authenticationDataSource.setCurrentAuthInfo(null)
        }
    }

    @Test
    fun changeUserPasswordSuccess() = runTest {
        val currentPassword = "currentpw"
        val newPassword = "newpw"

        val result = userRepository.changeUserPassword(currentPassword, newPassword)

        assertEquals(
            Either.Right(Unit),
            result
        )
        coVerify {
            userDataSource.changeUserPassword(
                ChangePasswordRequestBody(
                    currentPassword = currentPassword,
                    newPassword = newPassword,
                )
            )
        }
    }

    @Test
    fun changeUserPasswordFailure() = runTest {
        val currentPassword = "currentpw"
        val newPassword = "newpw"

        coEvery {
            userDataSource.changeUserPassword(any())
        } throws sampleHTTPException()

        val result = userRepository.changeUserPassword(currentPassword, newPassword)

        assertEquals(
            Either.Left(AppError.Unauthorized),
            result
        )
    }

    @Test
    fun getUserSuccess() = runTest {
        val userName = "Giulio"
        val userEmail = "giulio@example.com"
        coEvery {
            userDataSource.getUser()
        } returns UserDto(userName, userEmail)

        val result = userRepository.getUser()

        assertEquals(
            Either.Right(
                User(
                    name = userName,
                    email = userEmail,
                )
            ),
            result
        )
        coVerify {
            userDataSource.getUser()
        }
    }

    @Test
    fun getUserFailure() = runTest {
        coEvery {
            userDataSource.getUser()
        } throws sampleHTTPException()

        val result = userRepository.getUser()

        assertEquals(
            Either.Left(
                AppError.Unauthorized
            ),
            result
        )
        coVerify {
            userDataSource.getUser()
        }
    }

    @Test
    fun deleteUserSuccess() = runTest {
        val result = userRepository.deleteUser()

        assertEquals(
            Either.Right(Unit),
            result
        )
        coVerify(ordering = Ordering.SEQUENCE) {
            userDataSource.deleteUser()
            authenticationDataSource.setCurrentAuthInfo(null)
        }
    }

    @Test
    fun deleteUserFailure() = runTest {
        coEvery {
            userDataSource.deleteUser()
        } throws sampleHTTPException()

        val result = userRepository.deleteUser()

        assertEquals(
            Either.Left(AppError.Unauthorized),
            result
        )
        coVerify(exactly = 0) {
            authenticationDataSource.setCurrentAuthInfo(any())
        }
    }

    private fun sampleHTTPException(): HttpException {
        val sampleResponse =
            """
                {
                    "errorCode": "Unauthorized"
                }
            """
        val responseMediaType = MediaType.get("application/json")
        val responseBody = ResponseBody.create(responseMediaType, sampleResponse)

        return HttpException(
            Response.error<String>(500, responseBody)
        )
    }
}
