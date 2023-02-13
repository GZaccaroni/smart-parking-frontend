package it.unibolss.smartparking.domain.usecases.user

import arrow.core.Either
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.user.NewUser
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import it.unibolss.smartparking.domain.usecases.common.AsyncFailableUseCase

/**
 * Signs up a new user [Params.user]
 */
class SignUpUser(
    private val validateUserName: ValidateUserName,
    private val validateUserEmail: ValidateUserEmail,
    private val validateUserPassword: ValidateUserPassword,
    private val userRepository: UserRepository
) : AsyncFailableUseCase<SignUpUser.Params, AppError, Unit>() {

    override suspend fun run(params: Params): Either<AppError, Unit> {
        val cleanedUpUser = params.user.copy(
            name = params.user.name.trim(),
            email = params.user.email.trim(),
            password = params.user.password.trim(),
        )
        val validationError = validationError(cleanedUpUser)
        if (validationError != null) {
            return Either.Left(validationError)
        }
        return userRepository.signUp(cleanedUpUser)
    }
    private fun validationError(user: NewUser): AppError? {
        if (!validateUserName(ValidateUserName.Params(user.name)))
            return AppError.InvalidUserName
        if (!validateUserEmail(ValidateUserEmail.Params(user.email)))
            return AppError.InvalidUserEmail
        if (!validateUserPassword(ValidateUserPassword.Params(user.password)))
            return AppError.InvalidUserPassword

        return null
    }

    data class Params(val user: NewUser)
}