package it.unibolss.smartparking.domain.usecases.common

import arrow.core.Either

/**
 * An abstract base class for defining asynchronous use cases that may fail.
 *
*/
abstract class AsyncFailableUseCase<in Params, out Failure, out Result> :
    AsyncUseCase<Params, Either<Failure, Result>>()
