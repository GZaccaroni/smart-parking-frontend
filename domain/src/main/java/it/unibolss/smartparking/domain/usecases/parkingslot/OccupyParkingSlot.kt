package it.unibolss.smartparking.domain.usecases.parkingslot

import arrow.core.Either
import arrow.core.flatMap
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import it.unibolss.smartparking.domain.usecases.common.AsyncFailableUseCase
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

/**
 * Occupies a parking slot if the user is currently occupying no parking slot. If the user is
 * already parking in another place it returns an instance of [Either.Left] containing
 * [AppError.AlreadyParking]
 */
class OccupyParkingSlot(
    private val parkingSlotRepository: ParkingSlotRepository
) : AsyncFailableUseCase<OccupyParkingSlot.Params, AppError, Unit>() {

    override suspend fun run(params: Params): Either<AppError, Unit> =
        parkingSlotRepository.getCurrentParkingSlot()
            .flatMap {
                if (it != null) {
                    return@flatMap Either.Left(AppError.AlreadyParking)
                }
                if (params.stopEnd <= Clock.System.now()) {
                    return@flatMap Either.Left(AppError.InvalidParkingSlotStopEnd)
                }
                parkingSlotRepository.occupyParkingSlot(params.id, params.stopEnd)
            }

    data class Params(
        val id: String,
        val stopEnd: Instant
    )
}
