package it.unibolss.smartparking.domain.repositories.user

import arrow.core.Either
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.user.AuthState
import it.unibolss.smartparking.domain.entities.user.User
import it.unibolss.smartparking.domain.entities.user.UserCredentials
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val authState: AuthState
    val authStateFlow: Flow<AuthState>

    suspend fun signUp(user: User): Either<AppError, Unit>
    suspend fun login(credentials: UserCredentials): Either<AppError, Unit>
    suspend fun logout(): Either<AppError, Unit>
    suspend fun changeUserPassword(currentPassword: String): Either<AppError, Unit>

    suspend fun getUser(): Either<AppError, User>
    suspend fun deleteUser(): Either<AppError, Unit>
}