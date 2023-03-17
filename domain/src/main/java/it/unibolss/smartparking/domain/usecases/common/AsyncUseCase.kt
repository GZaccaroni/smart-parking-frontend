package it.unibolss.smartparking.domain.usecases.common

/**
 * An abstract base class for defining asynchronous use cases.
*/
abstract class AsyncUseCase<in Params, out Result> {

    protected abstract suspend fun run(params: Params): Result

    suspend operator fun invoke(
        params: Params
    ): Result = run(params)
}
