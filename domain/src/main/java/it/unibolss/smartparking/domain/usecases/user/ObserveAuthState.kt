package it.unibolss.smartparking.domain.usecases.user

import it.unibolss.smartparking.domain.entities.user.AuthState
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import it.unibolss.smartparking.domain.usecases.common.UseCase
import kotlinx.coroutines.flow.Flow

/**
 * Observes the state of authentication by the use of a [Flow].
 */
class ObserveAuthState(
    private val userRepository: UserRepository
) : UseCase<Unit, Flow<AuthState>>() {

    override fun run(params: Unit): Flow<AuthState> =
        userRepository.authStateFlow
}