package it.unibolss.smartparking.presentation.screens.login

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import it.unibolss.smartparking.presentation.navigation.Route

object LoginRoute : Route {
    override val path = "login"
}

fun NavGraphBuilder.loginScreen() {
    composable("login") {
        Box(
            modifier = Modifier
        )
    }
}
