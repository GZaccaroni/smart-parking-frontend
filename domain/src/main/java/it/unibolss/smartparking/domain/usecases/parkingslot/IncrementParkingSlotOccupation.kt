package it.unibolss.smartparking.domain.usecases.parkingslot

import arrow.core.Either
import arrow.core.flatMap
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import it.unibolss.smartparking.domain.usecases.common.AsyncFailableUseCase

/**
 * Increments the parking slot currently occupied by the user. If the user isn't parking it returns
 * an instance of [Either.Left] containing [AppError.NoParkingSlotOccupied]
 */
class IncrementParkingSlotOccupation(
    private val userRepository: UserRepository,
    private val parkingSlotRepository: ParkingSlotRepository
) : AsyncFailableUseCase<Unit, AppError, Unit>() {

    override suspend fun run(params: Unit): Either<AppError, Unit> =
        userRepository.getUser()
            .flatMap {
                if (it.currentParkingSlot == null)
                    Either.Left(AppError.NoParkingSlotOccupied)
                else
                    parkingSlotRepository.incrementParkingSlotOccupation(it.currentParkingSlot.id)
            }
}