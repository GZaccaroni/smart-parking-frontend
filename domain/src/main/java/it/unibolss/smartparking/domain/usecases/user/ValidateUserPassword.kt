package it.unibolss.smartparking.domain.usecases.user

import it.unibolss.smartparking.domain.usecases.common.UseCase

/**
 * Validates the password of a user
 */
class ValidateUserPassword : UseCase<ValidateUserPassword.Params, Boolean>() {

    override fun run(params: Params): Boolean {
        return params.password.length >= passwordMinLength
    }

    data class Params(val password: String)

    companion object {
        private const val passwordMinLength = 6
    }
}
