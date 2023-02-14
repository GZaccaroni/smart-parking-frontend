package it.unibolss.smartparking.domain.repositories.user

import arrow.core.Either
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.user.AuthState
import it.unibolss.smartparking.domain.entities.user.NewUser
import it.unibolss.smartparking.domain.entities.user.User
import it.unibolss.smartparking.domain.entities.user.UserCredentials

interface UserRepository {
    /**
     * Returns the current [AuthState]: if the user is logged in or not (guest)
     */
    val authState: AuthState

    /**
     * Creates a new user with the given data [NewUser] and automatically logs in.
     */
    suspend fun signUp(user: NewUser): Either<AppError, Unit>

    /**
     * Logs in the user with the given credentials [UserCredentials].
     */
    suspend fun login(credentials: UserCredentials): Either<AppError, Unit>

    /**
     * Logs out the currently logged in user.
     */
    suspend fun logout(): Either<AppError, Unit>
    /**
     * Changes the current user password [currentPassword] with a new one [newPassword].
     */
    suspend fun changeUserPassword(
        currentPassword: String,
        newPassword: String
    ): Either<AppError, Unit>

    /**
     * Gets the informations [User] of the currently logged in user
     */
    suspend fun getUser(): Either<AppError, User>
    /**
     * Deletes the currently logged in user
     */
    suspend fun deleteUser(): Either<AppError, Unit>
}