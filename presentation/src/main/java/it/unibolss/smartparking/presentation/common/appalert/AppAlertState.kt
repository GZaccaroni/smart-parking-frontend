package it.unibolss.smartparking.presentation.common.appalert

/**
 * Whether an app alert is currently shown or not.
 */
sealed interface AppAlertState {
    data class Some(val alert: AppAlert) : AppAlertState
    object None : AppAlertState
}
