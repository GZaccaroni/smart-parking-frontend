package it.unibolss.smartparking.domain.usecases.user

import arrow.core.Either
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.user.AuthState
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import it.unibolss.smartparking.domain.usecases.common.AsyncFailableUseCase
import it.unibolss.smartparking.domain.usecases.user.ChangeUserPassword.Params

/**
 * Changes the current password [Params.currentPassword] of the user with the new one
 * provided [Params.newPassword]. If the user is not logged in it returns an instance of
 * [Either.Left] containing [AppError.Unauthorized]
 */
class ChangeUserPassword(
    private val validateUserPassword: ValidateUserPassword,
    private val userRepository: UserRepository
) : AsyncFailableUseCase<ChangeUserPassword.Params, AppError, Unit>() {

    override suspend fun run(params: Params): Either<AppError, Unit> {
        if (params.newPassword == params.currentPassword)
            return Either.Left(AppError.NewPasswordEqualToCurrent)
        if (!validateUserPassword(ValidateUserPassword.Params(params.newPassword)))
            return Either.Left(AppError.InvalidUserPassword)
        if (userRepository.authState is AuthState.Guest)
            return Either.Left(AppError.Unauthorized)
        return userRepository.changeUserPassword(
            params.currentPassword,
            params.newPassword
        )
    }
    data class Params(
        val currentPassword: String,
        val newPassword: String
    )
}
