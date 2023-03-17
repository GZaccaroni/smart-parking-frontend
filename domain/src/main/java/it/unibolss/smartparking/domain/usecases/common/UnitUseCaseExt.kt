package it.unibolss.smartparking.domain.usecases.common

/**
 * Utility method to avoid passing [Unit] argument to invoke
 */
operator fun <Res> UseCase<Unit, Res>.invoke(): Res = invoke(Unit)

/**
 * Utility method to avoid passing [Unit] argument to invoke
 */
suspend operator fun <Res> AsyncUseCase<Unit, Res>.invoke(): Res = invoke(Unit)
