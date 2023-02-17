package it.unibolss.smartparking.data.datasources.parkingslot

import it.unibolss.smartparking.data.models.parkingslot.GetParkingSlotsBody
import it.unibolss.smartparking.data.models.parkingslot.ParkingSlotDto
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface ParkingSlotDataSource {
    @GET("parking-slot/")
    suspend fun getParkingSlots(
        @Body
        body: GetParkingSlotsBody
    ): Response<List<ParkingSlotDto>>

    @GET("parking-slot/{id}")
    suspend fun getParkingSlot(
        @Path("id")
        id: String
    ): Response<ParkingSlot>

    @POST("parking-slot/{id}/occupy")
    suspend fun occupyParkingSlot(
        @Path("id")
        id: String
    ): Response<Unit>

    @POST("parking-slot/{id}/increment-occupation")
    suspend fun incrementParkingSlotOccupation(
        @Path("id")
        id: String
    ): Response<Unit>

    @GET("user/{userId}/parking-slot")
    suspend fun getCurrentParkingSlot(
        @Path("userId")
        userId: String,
    ): Response<ParkingSlot>

    @POST("parking-slot/{id}/free")
    suspend fun freeParkingSlot(
        @Path("id")
        id: String
    ): Response<Unit>
}
