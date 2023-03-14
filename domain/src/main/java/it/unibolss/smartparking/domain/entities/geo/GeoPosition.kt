package it.unibolss.smartparking.domain.entities.geo

/**
 * A geographical position
 * @param latitude the latitude of the point
 * @param longitude the longitude of the point
 */
data class GeoPosition(
    val latitude: Double,
    val longitude: Double
)
