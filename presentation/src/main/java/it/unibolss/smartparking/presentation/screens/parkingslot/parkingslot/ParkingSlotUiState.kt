package it.unibolss.smartparking.presentation.screens.parkingslot.parkingslot

import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot

/**
 * The state of a screen displaying the details of a parking slot ([parkingSlot]).
 * @param parkingSlot the parking slot to be shown.
 * @param loading whether [parkingSlot] is still being fetched or a request is in progress.
 */
data class ParkingSlotUiState(
    val parkingSlot: ParkingSlot?,
    val loading: Boolean
) {
    companion object {
        fun initial() = ParkingSlotUiState(
            parkingSlot = null,
            loading = false
        )
    }
}
