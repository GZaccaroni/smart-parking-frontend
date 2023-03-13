package it.unibolss.smartparking.presentation.screens.user.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import it.unibolss.smartparking.presentation.navigation.Route

/**
 * The route to the login screen
 */
object LoginRoute : Route {
    override val path = "login"
}

/**
 * Utility method to provide the route to login screen in a [NavGraphBuilder]
 */
fun NavGraphBuilder.loginScreen() {
    composable("login") {
        LoginScreen()
    }
}
