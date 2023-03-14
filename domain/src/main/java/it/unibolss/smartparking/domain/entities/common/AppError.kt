package it.unibolss.smartparking.domain.entities.common

/**
 * An error happened during the use of the app
 */
enum class AppError {
    /**
     * An unknown error (typically due to a network issue).
     */
    Generic,

    /**
     * Serialization or deserialization of some objects failed.
     */
    SerializationFailed,

    /**
     * The user is not authorized to make this request, probably because their session is expired
     * or they are not allowed to do it.
     */
    Unauthorized,

    /**
     * A user already exists with this credentials in the system.
     */
    AlreadyRegistered,

    /**
     * The name selected by the user is not valid.
     */
    InvalidUserName,

    /**
     * The email selected by the user is not valid.
     */
    InvalidUserEmail,

    /**
     * The password selected by the user is not valid.
     */
    InvalidUserPassword,

    /**
     * The new password chosen is equal to the current one and thus it is not valid.
     */
    NewPasswordEqualToCurrent,

    /**
     *
     */
    WrongCredentials,

    /**
     * The user is already parking in a different parking slot and thus they can't occupy a different
     * one.
     */
    AlreadyParking,

    /**
     * The parking slot the user is trying to occupy is already occupied by a different user and thus
     * it can't be occupied by the current one.
     */
    ParkingSlotOccupied,

    /**
     * This parking slot no more exists.
     */
    ParkingSlotNotFound,

    /**
     * The user is currently occupying no parking slot.
     */
    NoParkingSlotOccupied,

    /**
     * The end of the parking slot occupation chosen is not valid (probably it is in the past).
     */
    InvalidParkingSlotStopEnd
}
