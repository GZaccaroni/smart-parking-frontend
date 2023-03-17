package it.unibolss.smartparking.domain.usecases.user

import arrow.core.Either
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.user.AuthState
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import it.unibolss.smartparking.domain.usecases.common.AsyncFailableUseCase

/**
 * Logs out the currently logged in user. If no user is currently logged in it succeeds.
 */
class LogoutUser(
    private val userRepository: UserRepository
) : AsyncFailableUseCase<Unit, AppError, Unit>() {

    override suspend fun run(params: Unit): Either<AppError, Unit> {
        if (userRepository.authState is AuthState.Guest)
            return Either.Right(Unit) // Already logged out
        return userRepository.logout()
    }
}
