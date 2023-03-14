package it.unibolss.smartparking.data.repositories

import android.util.Log
import arrow.core.Either
import it.unibolss.smartparking.data.common.AppJson
import it.unibolss.smartparking.data.models.common.AppErrorDto
import it.unibolss.smartparking.data.models.common.ErrorResponseDto
import it.unibolss.smartparking.domain.entities.common.AppError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import retrofit2.HttpException

internal suspend inline fun <reified T> apiCall(
    crossinline block: suspend () -> T,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): Either<AppError, T> =
    withContext(dispatcher) {
        try {
            Either.Right(block())
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            if (errorBody != null) {
                try {
                    val errorResponseDto = AppJson.instance.decodeFromString<ErrorResponseDto>(errorBody)
                    Either.Left(errorResponseDto.errorCode.toDomainError())
                } catch (e: SerializationException) {
                    Log.e("API_CALL", "Error serialization threw an exception", e)
                    Either.Left(AppError.Generic)
                }
            } else {
                Either.Left(AppError.Generic)
            }
        } catch (e: Exception) {
            Log.e("API_CALL", "Api call threw an exception", e)
            Either.Left(AppError.Generic)
        }
    }

private fun AppErrorDto.toDomainError(): AppError =
    when (this) {
        AppErrorDto.Unauthorized -> AppError.Unauthorized
        AppErrorDto.AlreadyRegistered -> AppError.AlreadyRegistered
        AppErrorDto.ParkingSlotNotFound -> AppError.ParkingSlotNotFound
        AppErrorDto.ParkingSlotOccupied -> AppError.ParkingSlotOccupied
        AppErrorDto.InvalidUserName -> AppError.InvalidUserName
        AppErrorDto.InvalidUserEmail -> AppError.InvalidUserEmail
        AppErrorDto.InvalidUserPassword -> AppError.InvalidUserPassword
        AppErrorDto.WrongCredentials -> AppError.WrongCredentials
        AppErrorDto.InvalidParkingSlotStopEnd -> AppError.InvalidParkingSlotStopEnd
    }
