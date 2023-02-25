package it.unibolss.smartparking.data.models.common

import kotlinx.serialization.Serializable

@Serializable
internal data class ErrorResponseDto(
    val errorCode: AppErrorDto
)
