package it.unibolss.smartparking.domain.entities.parkingslot

import it.unibolss.smartparking.domain.entities.geo.GeoPosition
import kotlinx.datetime.Clock

data class ParkingSlot(
    val id: String,
    val position: GeoPosition,
    val state: ParkingSlotState
) {
    fun isFree() = state is ParkingSlotState.Free
    fun isStale() = state is ParkingSlotState.Occupied && state.freesAt < Clock.System.now()
}
