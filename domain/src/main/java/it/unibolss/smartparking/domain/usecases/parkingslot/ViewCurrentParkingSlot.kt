package it.unibolss.smartparking.domain.usecases.parkingslot

import arrow.core.Either
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import it.unibolss.smartparking.domain.usecases.common.AsyncFailableUseCase

/**
 * Finds the information of the current parking slot
 */
class ViewCurrentParkingSlot(
    private val parkingSlotRepository: ParkingSlotRepository
) : AsyncFailableUseCase<Unit, AppError, ParkingSlot?>() {

    override suspend fun run(params: Unit): Either<AppError, ParkingSlot?> =
        parkingSlotRepository.getCurrentParkingSlot()
}
