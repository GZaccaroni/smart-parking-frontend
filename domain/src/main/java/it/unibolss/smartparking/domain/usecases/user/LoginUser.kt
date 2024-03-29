package it.unibolss.smartparking.domain.usecases.user

import arrow.core.Either
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.user.UserCredentials
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import it.unibolss.smartparking.domain.usecases.common.AsyncFailableUseCase

/**
 * Logs in the user with the provided [Params.credentials]
 */
class LoginUser(
    private val validateUserEmail: ValidateUserEmail,
    private val userRepository: UserRepository
) : AsyncFailableUseCase<LoginUser.Params, AppError, Unit>() {

    override suspend fun run(params: Params): Either<AppError, Unit> {
        val trimmedEmail = params.email.trim()
        if (!validateUserEmail(ValidateUserEmail.Params(trimmedEmail)))
            return Either.Left(AppError.InvalidUserEmail)
        return userRepository.login(UserCredentials(trimmedEmail, params.password))
    }

    data class Params(
        val email: String,
        val password: String
    )
}
