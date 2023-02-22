package it.unibolss.smartparking.presentation.screens.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import it.unibolss.smartparking.presentation.navigation.Route

internal object LoginRoute : Route {
    override val path = "login"
}

internal fun NavGraphBuilder.loginScreen() {
    composable("login") {
        LoginScreen()
    }
}
