package it.unibolss.smartparking.data.datasources.parkingslot

import it.unibolss.smartparking.data.models.parkingslot.ParkingSlotDto
import it.unibolss.smartparking.data.models.user.IncrementParkingSlotOccupationBody
import it.unibolss.smartparking.data.models.user.OccupyParkingSlotBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ParkingSlotDataSource {
    @GET("parking-slot/")
    suspend fun getParkingSlots(
        @Query("latitude")
        latitude: Double,
        @Query("longitude")
        longitude: Double,
        @Query("radius")
        radius: Double,
    ): List<ParkingSlotDto>

    @GET("parking-slot/current")
    suspend fun getCurrentParkingSlot(): ParkingSlotDto

    @GET("parking-slot/{id}")
    suspend fun getParkingSlot(
        @Path("id")
        id: String
    ): ParkingSlotDto

    @PUT("parking-slot/{id}/occupy")
    suspend fun occupyParkingSlot(
        @Path("id")
        id: String,
        @Body
        body: OccupyParkingSlotBody
    )

    @PUT("parking-slot/{id}/increment-occupation")
    suspend fun incrementParkingSlotOccupation(
        @Path("id")
        id: String,
        @Body
        body: IncrementParkingSlotOccupationBody
    )

    @PUT("parking-slot/{id}/free")
    suspend fun freeParkingSlot(
        @Path("id")
        id: String
    )
}
