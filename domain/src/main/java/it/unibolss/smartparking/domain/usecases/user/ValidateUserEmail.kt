package it.unibolss.smartparking.domain.usecases.user

import it.unibolss.smartparking.domain.usecases.common.UseCase

/**
 * Validates the email of a user
 */
class ValidateUserEmail : UseCase<ValidateUserEmail.Params, Boolean>() {

    override fun run(params: Params): Boolean =
        params.email.matches(emailRegex)

    data class Params(val email: String)

    companion object {
        private val emailRegex = Regex(
            "[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
        )
    }
}
