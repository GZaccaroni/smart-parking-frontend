package it.unibolss.smartparking.presentation.common.appalert

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal suspend fun MutableStateFlow<AppAlertState>.show(
    alert: AppAlert,
    duration: Duration = 5.toDuration(DurationUnit.SECONDS)
) {
    val state = AppAlertState.Some(alert)
    value = state

    // Dismiss alert after duration
    coroutineScope {
        launch {
            delay(duration)
            if (state != value) return@launch
            value = AppAlertState.None
        }
    }
}
