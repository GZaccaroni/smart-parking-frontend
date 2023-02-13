package it.unibolss.smartparking.domain.usecases.parkingslot

import arrow.core.Either
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.geo.GeoPosition
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import it.unibolss.smartparking.domain.usecases.common.AsyncFailableUseCase

/**
 * Finds the parking slots in a radius of [Params.radius] meters from [Params.center]
 */
class FindParkingSlots(
    private val parkingSlotRepository: ParkingSlotRepository
) : AsyncFailableUseCase<FindParkingSlots.Params, AppError, List<ParkingSlot>>() {

    override suspend fun run(params: Params): Either<AppError, List<ParkingSlot>> {
        if (params.radius <= 0) return Either.Right(emptyList())
        return parkingSlotRepository.getParkingSlots(params.center, params.radius)
    }

    data class Params(
        val center: GeoPosition,
        val radius: Double,
    )
}