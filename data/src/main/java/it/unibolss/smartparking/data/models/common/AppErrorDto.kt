package it.unibolss.smartparking.data.models.common

import kotlinx.serialization.Serializable

@Serializable
internal enum class AppErrorDto {
    Unauthorized,
    AlreadyRegistered,
    ParkingSlotNotFound,
    ParkingSlotOccupied,
    InvalidParkingSlotStopEnd,
    InvalidUserName,
    InvalidUserEmail,
    InvalidUserPassword,
    WrongCredentials,
}
