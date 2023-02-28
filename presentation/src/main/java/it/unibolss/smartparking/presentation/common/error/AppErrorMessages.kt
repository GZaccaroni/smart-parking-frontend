package it.unibolss.smartparking.presentation.common.error

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.presentation.R

internal val AppError.message: String
    @Composable
    get() = stringResource(
        when (this) {
            AppError.Generic -> R.string.app_error_generic
            AppError.SerializationFailed -> R.string.app_error_serialization_failed
            AppError.Unauthorized -> R.string.app_error_unauthorized
            AppError.AlreadyRegistered -> R.string.app_error_already_registered
            AppError.InvalidUserName -> R.string.app_error_invalid_user_name
            AppError.InvalidUserEmail -> R.string.app_error_invalid_user_email
            AppError.InvalidUserPassword -> R.string.app_error_invalid_user_password
            AppError.NewPasswordEqualToCurrent -> R.string.app_error_new_password_equal_to_current
            AppError.WrongCredentials -> R.string.app_error_wrong_credentials
            AppError.AlreadyParking -> R.string.app_error_already_parking
            AppError.ParkingSlotOccupied -> R.string.app_error_parking_slot_occupied
            AppError.ParkingSlotNotFound -> R.string.app_error_parking_slot_not_found
            AppError.NoParkingSlotOccupied -> R.string.app_error_no_parking_slot_occupied
            AppError.InvalidStopEnd -> R.string.app_error_invalid_stop_end
        }
    )
