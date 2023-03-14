package it.unibolss.smartparking.domain.entities.parkingslot

import kotlinx.datetime.Instant

/**
 * The state of a parking slot
 */
sealed interface ParkingSlotState {
    /**
     * The state of a parking slot when it is free
     */
    object Free : ParkingSlotState

    /**
     * The state of a parking slot when it is occupied
     * @param freesAt the date at which the parking slot will be free
     * @param currentUser whether the current user is occupying this parking slot
     */
    data class Occupied(
        val freesAt: Instant,
        val currentUser: Boolean,
    ) : ParkingSlotState
}
