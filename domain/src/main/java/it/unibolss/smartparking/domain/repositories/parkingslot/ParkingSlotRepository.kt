package it.unibolss.smartparking.domain.repositories.parkingslot

import arrow.core.Either
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.geo.GeoPosition
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot

interface ParkingSlotRepository {
    suspend fun getParkingSlots(center: GeoPosition, radius: Double): Either<AppError, List<ParkingSlot>>
    suspend fun getParkingSlot(id: String): Either<AppError, ParkingSlot?>

    suspend fun occupyParkingSlot(id: String): Either<AppError, List<ParkingSlot>>
    suspend fun incrementParkingSlotOccupation(id: String): Either<AppError, List<ParkingSlot>>
    suspend fun freeParkingSlot(id: String): Either<AppError, List<ParkingSlot>>
}