package it.unibolss.smartparking.domain.entities.common

enum class AppError {
    Generic,
    SerializationError,
    SessionExpired,
    Unauthorized,
    AlreadyLoggedIn,
    AlreadyRegistered,
    InvalidUserName,
    InvalidUserEmail,
    InvalidUserPassword,
    WrongCredentials,
    AlreadyParking,
    ParkingSlotOccupied,
    NoParkingSlotOccupied
}