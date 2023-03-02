package it.unibolss.smartparking.presentation.common.position

import android.location.Location
import it.unibolss.smartparking.domain.entities.geo.GeoPosition

/**
 * Calculates the distance from [other] in meters
 */
fun GeoPosition.distanceFrom(other: GeoPosition): Double {
    val locationA = Location("this_point")
    locationA.latitude = latitude
    locationA.longitude = longitude
    val locationB = Location("other_point")
    locationB.latitude = other.latitude
    locationB.longitude = other.longitude

    return locationA.distanceTo(locationB).toDouble()
}
