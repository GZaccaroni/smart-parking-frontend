package it.unibolss.smartparking.domain.entities.parkingslot

import it.unibolss.smartparking.domain.entities.geo.GeoPosition
import kotlinx.datetime.Clock

/**
 * A parking slot
 * @param id identifier of the parking slot
 * @param position position of the parking slot
 * @param state state of the parking slot (whether it is occupied or not)
 */
data class ParkingSlot(
    val id: String,
    val position: GeoPosition,
    val state: ParkingSlotState
) {
    /**
     * Returns true if the parking slot is free
     */
    fun isFree() = state is ParkingSlotState.Free

    /**
     * Returns true if the parking slot is stale: it is occupied but the expected free date is passed
     */
    fun isStale() = state is ParkingSlotState.Occupied && state.freesAt < Clock.System.now()
}
