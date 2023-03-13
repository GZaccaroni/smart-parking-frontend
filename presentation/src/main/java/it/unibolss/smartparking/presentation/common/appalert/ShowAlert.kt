package it.unibolss.smartparking.presentation.common.appalert

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

/**
 * This method shows an [alert] for a given [duration]
 */
@OptIn(DelicateCoroutinesApi::class)
internal fun MutableStateFlow<AppAlertState>.show(
    alert: AppAlert,
    duration: Duration = 4.toDuration(DurationUnit.SECONDS)
) {
    val state = AppAlertState.Some(alert)
    value = state

    // It is ok to use GlobalScope here because the alert is linked to the whole
    // app and not to a single screen
    GlobalScope.launch {
        delay(duration)

        // If another alert has been shown in the meanwhile we don't hide it
        if (state != value) return@launch

        // Hide current alert
        value = AppAlertState.None
    }
}
