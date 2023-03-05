package it.unibolss.smartparking.data.repositories.parkingslot

import arrow.core.Either
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.geo.GeoPosition
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlotState
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus

class ParkingSlotRepositoryImpl : ParkingSlotRepository {

    /*
    val parkingSlots = listOf(
        ParkingSlot("1", GeoPosition(44.216036, 12.049972), ParkingSlotState.Free),
        ParkingSlot(
            "2",
            GeoPosition(44.216010, 12.049966),
            ParkingSlotState.Occupied(
                freesAt = Clock.System.now().plus(
                    DateTimePeriod(minutes = 5),
                    TimeZone.UTC
                ),
                currentUser = true
            )
        ),
        ParkingSlot(
            "3",
            GeoPosition(44.216046, 12.049618),
            ParkingSlotState.Occupied(
                freesAt = Clock.System.now().plus(
                    DateTimePeriod(minutes = 15),
                    TimeZone.UTC
                ),
                currentUser = true
            )
        ),
        ParkingSlot("4", GeoPosition(44.216224, 12.049792), ParkingSlotState.Free),
    )*/

    override suspend fun getParkingSlots(
        center: GetParkingSlotsBody(
            position: GeoPosition,
            radius: Double
        )
    ): Either<AppError, List<ParkingSlot>> {
        delay(4000)
        return Either.Right(
            parkingSlots
        )
    }

    override suspend fun getParkingSlot(id: String): Either<AppError, ParkingSlot> {
        val parkingSlot = parkingSlots.firstOrNull { id == it.id }
        delay(4000)
        return if (parkingSlot == null) {
            Either.Left(AppError.ParkingSlotNotFound)
        } else {
            Either.Right(parkingSlot)
        }
    }

    override suspend fun getCurrentParkingSlot(): Either<AppError, ParkingSlot?> {
        return Either.Right(parkingSlots.first())
    }

    override suspend fun occupyParkingSlot(
        id: String,
        //stopEnd: Instant
        stopEnd: String
    ): Either<AppError, Unit> {
        delay(3000)
        return Either.Right(Unit)
    }

    override suspend fun incrementParkingSlotOccupation(
        id: String,
        //stopEnd: Instant
        stopEnd: String
    ): Either<AppError, Unit> {
        delay(3000)
        return Either.Right(Unit)
    }

    override suspend fun freeParkingSlot(id: String): Either<AppError, Unit> {
        delay(3000)
        return Either.Right(Unit)
    }
}

