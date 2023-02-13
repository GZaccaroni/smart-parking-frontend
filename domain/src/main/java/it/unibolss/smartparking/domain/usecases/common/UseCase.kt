package it.unibolss.smartparking.domain.usecases.common

abstract class UseCase<in Params, out Result> {

    protected abstract fun run(params: Params): Result

    operator fun invoke(
        params: Params
    ): Result = run(params)
}
