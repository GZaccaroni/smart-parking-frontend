package it.unibolss.smartparking.domain.usecases.user

import arrow.core.Either
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.user.AuthState
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import it.unibolss.smartparking.domain.usecases.common.AsyncFailableUseCase
import it.unibolss.smartparking.domain.usecases.user.ChangeUserPassword.Params

/**
 * Deletes the logged in user.
 * If the user is not logged in it returns an instance of [Either.Left] containing [AppError.Unauthorized]
 */
class DeleteUser(
    private val userRepository: UserRepository
) : AsyncFailableUseCase<Unit, AppError, Unit>() {

    override suspend fun run(params: Unit): Either<AppError, Unit> {
        if (userRepository.authState is AuthState.Guest)
            return Either.Left(AppError.Unauthorized)
        return userRepository.deleteUser()
    }

}