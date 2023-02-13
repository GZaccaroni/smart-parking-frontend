package it.unibolss.smartparking.domain.usecases.user

import it.unibolss.smartparking.domain.usecases.common.UseCase

/**
 * Validates the name of a user
 */
class ValidateUserName : UseCase<ValidateUserName.Params, Boolean>() {

    override fun run(params: Params): Boolean {
        return nameLengthRange.contains(params.name.trim().length)
    }

    data class Params(val name: String)

    companion object {
        private val nameLengthRange = 4..10
    }
}