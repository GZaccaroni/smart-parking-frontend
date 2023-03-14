package it.unibolss.smartparking.data.common

import kotlinx.serialization.json.Json

internal object AppJson {
    val instance: Json =
        Json {
            ignoreUnknownKeys = true
        }
}
