package it.unibolss.smartparking.presentation.screens.parkingslot.parkingslots

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import it.unibolss.smartparking.presentation.navigation.Route

object ParkingSlotsRoute : Route {
    override val path = "parking-slots"
}

fun NavGraphBuilder.parkingSlotsScreen() {
    composable("parking-slots") {
        ParkingSlotsScreen()
    }
}
