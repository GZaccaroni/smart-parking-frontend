package it.unibolss.smartparking.domain.usecases.common

/**
 * An abstract base class for defining non-asynchronous use cases.
*/
abstract class UseCase<in Params, out Result> {

    protected abstract fun run(params: Params): Result

    operator fun invoke(
        params: Params
    ): Result = run(params)
}
