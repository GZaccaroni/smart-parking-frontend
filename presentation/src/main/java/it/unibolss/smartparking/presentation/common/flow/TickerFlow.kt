package it.unibolss.smartparking.presentation.common.flow

import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.TickerMode
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Returns a [Flow] that emits a unit value at a fixed interval [delayMillis] after an initial delay
 * of [initialDelayMillis].
 */
@OptIn(ObsoleteCoroutinesApi::class)
internal fun tickerFlow(
    delayMillis: Long,
    initialDelayMillis: Long = delayMillis,
    context: CoroutineContext = EmptyCoroutineContext,
    mode: TickerMode = TickerMode.FIXED_PERIOD
): Flow<Unit> {
    require(delayMillis > 0)
    return ticker(delayMillis, initialDelayMillis, context, mode)
        .consumeAsFlow()
}
