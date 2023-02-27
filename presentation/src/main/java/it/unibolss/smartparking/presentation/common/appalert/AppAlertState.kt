package it.unibolss.smartparking.presentation.common.appalert

sealed interface AppAlertState {
    data class Some(val alert: AppAlert) : AppAlertState
    object None : AppAlertState
}
