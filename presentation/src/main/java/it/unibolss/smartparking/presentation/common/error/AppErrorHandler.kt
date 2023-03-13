package it.unibolss.smartparking.presentation.common.error

import androidx.navigation.NavOptions
import it.unibolss.smartparking.domain.entities.common.AppError
import it.unibolss.smartparking.presentation.common.appalert.AppAlert
import it.unibolss.smartparking.presentation.common.appalert.AppAlertState
import it.unibolss.smartparking.presentation.common.appalert.show
import it.unibolss.smartparking.presentation.navigation.Router
import it.unibolss.smartparking.presentation.screens.user.login.LoginRoute
import kotlinx.coroutines.flow.MutableStateFlow

internal fun handleAppError(
    error: AppError,
    alertState: MutableStateFlow<AppAlertState>,
    router: Router
) {
    if (error == AppError.Unauthorized) {
        router.navigateTo(
            LoginRoute,
            NavOptions.Builder().setPopUpTo(0, true).build(),
        )
    }
    alertState.show(AppAlert.Error(error))
}
