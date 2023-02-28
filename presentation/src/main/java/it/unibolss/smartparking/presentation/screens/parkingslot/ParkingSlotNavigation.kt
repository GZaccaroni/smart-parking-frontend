package it.unibolss.smartparking.presentation.screens.parkingslot

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import it.unibolss.smartparking.presentation.navigation.Route

data class ParkingSlotRoute(val id: String) : Route {
    override val path = "parking-slots/$id"
}

fun NavGraphBuilder.parkingSlotScreen() {
    composable(
        "parking-slots/{id}",
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
        val parkingSlotId = backStackEntry.arguments?.getString("id")!!
        ParkingSlotScreen(parkingSlotId = parkingSlotId)
    }
}
