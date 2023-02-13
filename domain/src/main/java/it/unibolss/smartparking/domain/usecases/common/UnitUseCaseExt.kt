package it.unibolss.smartparking.domain.usecases.common

operator fun <Res> UseCase<Unit, Res>.invoke(): Res = invoke(Unit)
suspend operator fun <Res> AsyncUseCase<Unit, Res>.invoke(): Res = invoke(Unit)