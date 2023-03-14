package it.unibolss.smartparking.presentation.screens.parkingslot.parkingslots

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import it.unibolss.smartparking.presentation.navigation.Route

/**
 * The route to the parking slots screen
 */
object ParkingSlotsRoute : Route {
    override val path = "parking-slots"
}

/**
 * Utility method to provide the route to parking slots screen in a [NavGraphBuilder]
 */
fun NavGraphBuilder.parkingSlotsScreen() {
    composable("parking-slots") {
        ParkingSlotsScreen()
    }
}
