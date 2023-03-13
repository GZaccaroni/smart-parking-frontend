package it.unibolss.smartparking.data.repositories

import arrow.core.Either
import it.unibolss.smartparking.data.models.common.AppErrorDto
import it.unibolss.smartparking.data.models.common.ErrorResponseDto
import it.unibolss.smartparking.domain.entities.common.AppError
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.Response

internal inline fun <reified T> Response<T>.toEither(): Either<AppError, T> {
    return if (isSuccessful) {
        val body = body()
        if (body != null) {
            Either.Right(body)
        } else {
            Either.Left(AppError.SerializationFailed)
        }
    } else {
        val response = errorBody()?.string()
        if (response != null) {
            try {
                val errorResponseDto = Json.decodeFromString<ErrorResponseDto>(response)
                Either.Left(errorResponseDto.errorCode.toDomainError())
            } catch (e: Exception) {
                Either.Left(AppError.Generic)
            }
        } else {
            Either.Left(AppError.Generic)
        }
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
