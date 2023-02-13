package it.unibolss.smartparking.domain.repositories.parkingslot

import arrow.core.Either
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.geo.GeoPosition
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot

interface ParkingSlotRepository {
    /**
     * Finds all the parking slots in a radius of [radius] meters from [center]
     */
    suspend fun getParkingSlots(center: GeoPosition, radius: Double): Either<AppError, List<ParkingSlot>>

    /**
     * Finds a parking slot identified by [id]
     */
    suspend fun getParkingSlot(id: String): Either<AppError, ParkingSlot?>

    /**
     * Occupies a parking slot identified by [id]
     */
    suspend fun occupyParkingSlot(id: String): Either<AppError, Unit>

    /**
     * Increments the occupation of a parking slot identified by [id]
     */
    suspend fun incrementParkingSlotOccupation(id: String): Either<AppError, Unit>

    /**
     * Frees the parking slot identified by [id]
     */
    suspend fun freeParkingSlot(id: String): Either<AppError, Unit>
}