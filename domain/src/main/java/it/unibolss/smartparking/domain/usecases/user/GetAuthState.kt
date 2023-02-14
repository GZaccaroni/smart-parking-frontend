package it.unibolss.smartparking.domain.usecases.user

import it.unibolss.smartparking.domain.entities.user.AuthState
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import it.unibolss.smartparking.domain.usecases.common.UseCase

/**
 * Gets the current state of authentication.
 */
class GetAuthState(
    private val userRepository: UserRepository
) : UseCase<Unit, AuthState>() {

    override fun run(params: Unit): AuthState =
        userRepository.authState
}
