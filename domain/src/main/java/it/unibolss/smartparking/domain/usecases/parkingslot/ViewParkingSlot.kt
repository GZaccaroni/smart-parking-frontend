package it.unibolss.smartparking.domain.usecases.parkingslot

import arrow.core.Either
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import it.unibolss.smartparking.domain.usecases.common.AsyncFailableUseCase
import it.unibolss.smartparking.domain.usecases.parkingslot.ViewParkingSlot.Params

/**
 * Finds the information of a parking slot identified by [Params.id]
 */
class ViewParkingSlot(
    private val parkingSlotRepository: ParkingSlotRepository
) : AsyncFailableUseCase<ViewParkingSlot.Params, AppError, ParkingSlot>() {

    override suspend fun run(params: Params): Either<AppError, ParkingSlot> =
        parkingSlotRepository.getParkingSlot(params.id)

    data class Params(
        val id: String,
    )
}
