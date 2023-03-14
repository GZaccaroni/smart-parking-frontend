package it.unibolss.smartparking.domain.repositories.parkingslot

import arrow.core.Either
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.geo.GeoPosition
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import kotlinx.datetime.Instant

/**
 * An interface representing a parking slot repository for accessing and manipulating parking slot
 *  data.
 */
interface ParkingSlotRepository {
    /**
     * Finds all the parking slots in a radius of [radius] meters from [center]
     */
    suspend fun getParkingSlots(center: GeoPosition, radius: Double): Either<AppError, List<ParkingSlot>>

    /**
     * Finds a parking slot identified by [id]
     */
    suspend fun getParkingSlot(id: String): Either<AppError, ParkingSlot>

    /**
     * Finds the parking slot currently occupied by the user
     */
    suspend fun getCurrentParkingSlot(): Either<AppError, ParkingSlot?>

    /**
     * Occupies a parking slot identified by [id]
     */
    suspend fun occupyParkingSlot(
        id: String,
        stopEnd: Instant,
    ): Either<AppError, Unit>

    /**
     * Increments the occupation of a parking slot identified by [id]
     */
    suspend fun incrementParkingSlotOccupation(
        id: String,
        stopEnd: Instant,
    ): Either<AppError, Unit>

    /**
     * Frees the parking slot identified by [id]
     */
    suspend fun freeParkingSlot(id: String): Either<AppError, Unit>
}
