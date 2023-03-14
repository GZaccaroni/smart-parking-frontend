package it.unibolss.smartparking.data.repositories.user

import arrow.core.Either
import it.unibolss.smartparking.data.datasources.user.AuthenticationDataSource
import it.unibolss.smartparking.data.datasources.user.UserDataSource
import it.unibolss.smartparking.data.models.user.AuthenticationInfo
import it.unibolss.smartparking.data.models.user.ChangePasswordRequestBody
import it.unibolss.smartparking.data.models.user.LoginRequestBody
import it.unibolss.smartparking.data.models.user.SignUpRequestBody
import it.unibolss.smartparking.data.models.user.UserDto
import it.unibolss.smartparking.data.repositories.apiCall
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.user.AuthState
import it.unibolss.smartparking.domain.entities.user.NewUser
import it.unibolss.smartparking.domain.entities.user.User
import it.unibolss.smartparking.domain.entities.user.UserCredentials
import it.unibolss.smartparking.domain.repositories.user.UserRepository

internal class UserRepositoryImpl(
    private val authenticationDataSource: AuthenticationDataSource,
    private val userDataSource: UserDataSource
) : UserRepository {
    override val authState: AuthState
        get() {
            val authInfo = authenticationDataSource.getCurrentAuthInfo()
            return if (authInfo != null) {
                AuthState.LoggedIn
            } else {
                AuthState.Guest
            }
        }

    override suspend fun signUp(user: NewUser): Either<AppError, Unit> =
        apiCall {
            userDataSource.signUp(
                SignUpRequestBody(
                    name = user.name,
                    email = user.email,
                    password = user.password
                )
            )
        }.map {
            authenticationDataSource.setCurrentAuthInfo(
                AuthenticationInfo(
                    authToken = it.token,
                    userId = it.userId
                )
            )
        }

    override suspend fun login(credentials: UserCredentials): Either<AppError, Unit> =
        apiCall {
            userDataSource.login(
                LoginRequestBody(
                    email = credentials.email,
                    password = credentials.password
                )
            )
        }.map {
            authenticationDataSource.setCurrentAuthInfo(
                AuthenticationInfo(
                    authToken = it.token,
                    userId = it.userId
                )
            )
        }

    override suspend fun logout(): Either<AppError, Unit> {
        authenticationDataSource.setCurrentAuthInfo(null)
        return Either.Right(Unit)
    }

    override suspend fun changeUserPassword(
        currentPassword: String,
        newPassword: String
    ): Either<AppError, Unit> =
        apiCall {
            userDataSource.changeUserPassword(
                ChangePasswordRequestBody(
                    currentPassword = currentPassword,
                    newPassword = newPassword
                )
            )
        }

    override suspend fun getUser(): Either<AppError, User> =
        apiCall {
            userDataSource.getUser()
        }.map {
            it.toDomainUser()
        }

    override suspend fun deleteUser(): Either<AppError, Unit> =
        apiCall {
            userDataSource.deleteUser()
        }.map {
            authenticationDataSource.setCurrentAuthInfo(null)
        }

    private fun UserDto.toDomainUser(): User =
        User(
            name = name,
            email = email
        )
}
