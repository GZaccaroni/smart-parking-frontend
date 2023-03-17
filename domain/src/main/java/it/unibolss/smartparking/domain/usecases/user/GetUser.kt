package it.unibolss.smartparking.domain.usecases.user

import arrow.core.Either
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.user.AuthState
import it.unibolss.smartparking.domain.entities.user.User
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import it.unibolss.smartparking.domain.usecases.common.AsyncFailableUseCase

/**
 * Gets the the logged in user
 * If the user is not logged in it returns an instance of [Either.Left] containing [AppError.Unauthorized]
 */
class GetUser(
    private val userRepository: UserRepository
) : AsyncFailableUseCase<Unit, AppError, User>() {

    override suspend fun run(params: Unit): Either<AppError, User> {
        if (userRepository.authState is AuthState.Guest)
            return Either.Left(AppError.Unauthorized)
        return userRepository.getUser()
    }
}
