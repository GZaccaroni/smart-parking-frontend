package it.unibolss.smartparking.domain.entities.common

enum class AppError {
    Generic,
    SessionExpired,
    Unauthorized,
    AlreadyLoggedIn,
    InvalidUserName,
    InvalidUserEmail,
    InvalidUserPassword,
    WrongCredentials,
    AlreadyParking,
    ParkingSlotOccupied,
    NoParkingSlotOccupied
}