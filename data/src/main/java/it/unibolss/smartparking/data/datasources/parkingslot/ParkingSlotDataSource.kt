package it.unibolss.smartparking.data.datasources.parkingslot

import it.unibolss.smartparking.data.models.parkingslot.ParkingSlotDto
import it.unibolss.smartparking.domain.entities.geo.GeoPosition
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import retrofit2.Response

internal interface ParkingSlotDataSource {
    suspend fun getParkingSlots(center: GeoPosition, radius: Double): Response<List<ParkingSlotDto>>

    suspend fun getParkingSlot(id: String): Response<ParkingSlot>

    suspend fun occupyParkingSlot(id: String): Response<Unit>

}
