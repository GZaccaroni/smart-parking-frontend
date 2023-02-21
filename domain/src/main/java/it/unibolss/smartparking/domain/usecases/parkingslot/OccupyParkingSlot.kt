package it.unibolss.smartparking.domain.usecases.parkingslot

import arrow.core.Either
import arrow.core.flatMap
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import it.unibolss.smartparking.domain.usecases.common.AsyncFailableUseCase

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
                if (it != null)
                    Either.Left(AppError.AlreadyParking)
                else
                    parkingSlotRepository.occupyParkingSlot(params.id)
            }

    data class Params(
        val id: String,
    )
}