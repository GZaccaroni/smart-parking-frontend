package it.unibolss.smartparking.domain.usecases.common

import arrow.core.Either

abstract class AsyncFailableUseCase<in Params, out Failure, out Result> :
    AsyncUseCase<Params, Either<Failure, Result>>()