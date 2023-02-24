package it.unibolss.smartparking.presentation.screens.signup

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import it.unibolss.smartparking.presentation.navigation.Route

internal object SignUpRoute : Route {
    override val path = "sign-up"
}

internal fun NavGraphBuilder.signUpScreen() {
    composable("sign-up") {
        SignUpScreen()
    }
}
