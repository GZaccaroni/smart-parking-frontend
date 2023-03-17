package it.unibolss.smartparking.presentation.common.date

import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Formats an [Instant] according to a given [FormatStyle]
 */
fun Instant.formatted(
    formatStyle: FormatStyle
): String {
    val javaInstant = this.toJavaInstant()
    val zonedDateTime = ZonedDateTime.ofInstant(javaInstant, ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofLocalizedDateTime(formatStyle)
    return formatter.format(zonedDateTime)
}
