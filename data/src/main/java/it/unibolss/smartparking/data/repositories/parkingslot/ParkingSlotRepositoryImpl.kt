package it.unibolss.smartparking.data.repositories.parkingslot

import arrow.core.Either
import it.unibolss.smartparking.data.datasources.parkingslot.ParkingSlotDataSource
import it.unibolss.smartparking.data.datasources.user.AuthenticationDataSource
import it.unibolss.smartparking.data.models.parkingslot.GeoPositionDto
import it.unibolss.smartparking.data.models.parkingslot.ParkingSlotDto
import it.unibolss.smartparking.data.models.user.IncrementParkingSlotOccupationBody
import it.unibolss.smartparking.data.models.user.OccupyParkingSlotBody
import it.unibolss.smartparking.data.repositories.apiCall
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.domain.entities.geo.GeoPosition
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlot
import it.unibolss.smartparking.domain.entities.parkingslot.ParkingSlotState
import it.unibolss.smartparking.domain.repositories.parkingslot.ParkingSlotRepository
import kotlinx.datetime.Instant

internal class ParkingSlotRepositoryImpl(
    private val authenticationDataSource: AuthenticationDataSource,
    private val parkingSlotDataSource: ParkingSlotDataSource
) : ParkingSlotRepository {

    override suspend fun getParkingSlots(
        center: GeoPosition,
        radius: Double
    ): Either<AppError, List<ParkingSlot>> {
        val authInfo = authenticationDataSource.getCurrentAuthInfo()
            ?: return Either.Left(AppError.Unauthorized)
        return apiCall {
            parkingSlotDataSource
                .getParkingSlots(
                    latitude = center.latitude,
                    longitude = center.longitude,
                    radius = radius
                )
        }.map { list ->
            list.map { it.toDomain(authInfo.userId == it.occupierId) }
        }
    }
    override suspend fun getParkingSlot(id: String): Either<AppError, ParkingSlot> {
        val authInfo = authenticationDataSource.getCurrentAuthInfo()
            ?: return Either.Left(AppError.Unauthorized)
        return apiCall {
            parkingSlotDataSource
                .getParkingSlot(id)
        }.map {
            it.toDomain(authInfo.userId == it.occupierId)
        }
    }
    override suspend fun getCurrentParkingSlot(): Either<AppError, ParkingSlot?> =
        apiCall {
            parkingSlotDataSource
                .getCurrentParkingSlot()
        }.map {
            it.toDomain(true)
        }.apply {
            return when (this) {
                Either.Left(AppError.ParkingSlotNotFound) -> Either.Right(null)
                else -> this
            }
        }
