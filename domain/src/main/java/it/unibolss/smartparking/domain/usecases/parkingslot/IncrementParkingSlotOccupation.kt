package it.unibolss.smartparking.domain.usecases.parkingslot

import arrow.core.Either
import arrow.core.flatMap
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlotState
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import it.unibolss.smartparking.domain.usecases.common.AsyncFailableUseCase
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

/**
 * Increments the parking slot currently occupied by the user. If the user isn't parking it returns
 * an instance of [Either.Left] containing [AppError.NoParkingSlotOccupied]
 */
class IncrementParkingSlotOccupation(
    private val parkingSlotRepository: ParkingSlotRepository
) : AsyncFailableUseCase<IncrementParkingSlotOccupation.Params, AppError, Unit>() {

    override suspend fun run(params: Params): Either<AppError, Unit> =
        parkingSlotRepository.getCurrentParkingSlot()
            .flatMap {
                if (it == null || it.state !is ParkingSlotState.Occupied) {
                    return@flatMap Either.Left(AppError.NoParkingSlotOccupied)
                }
                if (params.stopEnd <= it.state.freesAt || params.stopEnd <= Clock.System.now()) {
                    return@flatMap Either.Left(AppError.InvalidStopEnd)
                }
                parkingSlotRepository.incrementParkingSlotOccupation(it.id, params.stopEnd)
            }

    data class Params(
        val stopEnd: Instant
    )
}
