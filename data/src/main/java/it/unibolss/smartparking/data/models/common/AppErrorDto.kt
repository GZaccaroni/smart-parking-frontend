package it.unibolss.smartparking.data.models.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AppErrorDto {
    @SerialName("unauthorizedUser")
    Unauthorized,

    @SerialName("alreadyRegistered")
    AlreadyRegistered,
}
