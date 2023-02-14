package it.unibolss.smartparking.presentation.screens.parkingslots

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import it.unibolss.smartparking.presentation.navigation.Route

object ParkingSlotsRoute : Route {
    override val path = "parking-slots"
}

fun NavGraphBuilder.parkingSlotsScreen() {
    composable("parking-slots") {
        Box(
            modifier = Modifier
        )
    }
}
