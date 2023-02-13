package it.unibolss.smartparking.domain.usecases.parkingslot

import arrow.core.Either
import arrow.core.flatMap
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import it.unibolss.smartparking.domain.repositories.user.UserRepository
import it.unibolss.smartparking.domain.usecases.common.AsyncFailableUseCase

/**
 * Frees the parking slot currently occupied by the user. If the user hasn't parked it does
 * nothing.
 */
class FreeParkingSlot(
    private val userRepository: UserRepository,
    private val parkingSlotRepository: ParkingSlotRepository
) : AsyncFailableUseCase<Unit, AppError, Unit>() {

    override suspend fun run(params: Unit): Either<AppError, Unit> =
        userRepository.getUser()
            .flatMap {
                if (it.currentParkingSlot == null)
                    Either.Right(Unit)
                else
                    parkingSlotRepository.freeParkingSlot(it.currentParkingSlot.id)
            }
}