package it.unibolss.smartparking.presentation.screens.user.signup

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import it.unibolss.smartparking.presentation.navigation.Route

/**
 * The route to the sign up screen
 */
object SignUpRoute : Route {
    override val path = "sign-up"
}

/**
 * Utility method to provide the route to sign up screen in a [NavGraphBuilder]
 */
fun NavGraphBuilder.signUpScreen() {
    composable("sign-up") {
        SignUpScreen()
    }
}
