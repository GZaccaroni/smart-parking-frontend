package it.unibolss.smartparking.domain.entities.common

enum class AppError {
    Generic,
    SerializationError,
    Unauthorized,
    AlreadyRegistered,
    InvalidUserName,
    InvalidUserEmail,
    InvalidUserPassword,
    WrongCredentials,
    AlreadyParking,
    ParkingSlotOccupied,
    ParkingSlotNotFound,
    NoParkingSlotOccupied
}
