package it.unibolss.smartparking.presentation.screens.parkingslot

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import it.unibolss.smartparking.presentation.navigation.Route

data class ParkingSlotRoute(val id: String) : Route {
    override val path = "parking-slots/$id"
}

fun NavGraphBuilder.parkingSlotScreen() {
    composable("parking-slots/{id}") {
        Box(
            modifier = Modifier
        )
    }
}
