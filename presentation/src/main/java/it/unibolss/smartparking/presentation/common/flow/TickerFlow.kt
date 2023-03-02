package it.unibolss.smartparking.presentation.common.flow

import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.TickerMode
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(ObsoleteCoroutinesApi::class)
fun tickerFlow(
    delayMillis: Long,
    initialDelayMillis: Long = delayMillis,
    context: CoroutineContext = EmptyCoroutineContext,
    mode: TickerMode = TickerMode.FIXED_PERIOD
): Flow<Unit> {
    require(delayMillis > 0)
    return ticker(delayMillis, initialDelayMillis, context, mode)
        .consumeAsFlow()
}
