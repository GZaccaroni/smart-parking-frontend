package it.unibolss.smartparking.presentation.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import it.unibolss.smartparking.presentation.common.theme.SmartParkingTheme
import it.unibolss.smartparking.presentation.navigation.Router
import it.unibolss.smartparking.presentation.navigation.RouterCommand
import it.unibolss.smartparking.presentation.screens.parkingslot.parkingslot.parkingSlotScreen
import it.unibolss.smartparking.presentation.screens.parkingslot.parkingslots.ParkingSlotsRoute
import it.unibolss.smartparking.presentation.screens.parkingslot.parkingslots.parkingSlotsScreen
import it.unibolss.smartparking.presentation.screens.user.changepassword.changePasswordScreen
import it.unibolss.smartparking.presentation.screens.user.login.LoginRoute
import it.unibolss.smartparking.presentation.screens.user.login.loginScreen
import it.unibolss.smartparking.presentation.screens.user.signup.signUpScreen
import org.koin.androidx.compose.get

/**
 * A composable function that defines the main UI for the Smart Parking application.
 *
 * This function takes two parameters: [isLoggedIn], which specifies whether the user is currently
 * logged in, and [router]: a [Router] implementation used for navigating
 * between screens.
*/
@Composable
internal fun SmartParkingApp(
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
                changePasswordScreen()

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
    LaunchedEffect(true) {
        router.commands.collect { command ->
            when (command) {
                is RouterCommand.NavigateTo ->
                    navController.navigate(command.route.path, command.navOptions)
                RouterCommand.PopBackStack ->
                    navController.popBackStack()
            }
        }
    }
    return navController
}
