package it.unibolss.smartparking.presentation.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import it.unibolss.smartparking.presentation.common.theme.SmartParkingTheme
import it.unibolss.smartparking.presentation.navigation.Router
import it.unibolss.smartparking.presentation.navigation.RouterCommand
import it.unibolss.smartparking.presentation.screens.login.LoginRoute
import it.unibolss.smartparking.presentation.screens.login.loginScreen
import it.unibolss.smartparking.presentation.screens.parkingslot.parkingSlotScreen
import it.unibolss.smartparking.presentation.screens.parkingslots.ParkingSlotsRoute
import it.unibolss.smartparking.presentation.screens.parkingslots.parkingSlotsScreen
import it.unibolss.smartparking.presentation.screens.signup.signUpScreen
import org.koin.androidx.compose.get

@Composable
fun SmartParkingApp(
    isLoggedIn: Boolean,
    router: Router = get()
) {
    val navController = createNavController(router)
    SmartParkingTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val initialRoute = if (isLoggedIn) ParkingSlotsRoute else LoginRoute
            NavHost(navController, initialRoute.path) {
                // User
                loginScreen()
                signUpScreen()

                // Parking slots
                parkingSlotsScreen()
                parkingSlotScreen()
            }
        }
    }
}

@Composable
private fun createNavController(router: Router): NavHostController {
    val navController = rememberNavController()
    router.commands.collectAsState().value.also { command ->
        when (command) {
            is RouterCommand.NavigateTo ->
                navController.navigate(command.route.path, command.navOptions)
            RouterCommand.PopBackStack ->
                navController.popBackStack()
            null -> return@also
        }
    }
    return navController
}