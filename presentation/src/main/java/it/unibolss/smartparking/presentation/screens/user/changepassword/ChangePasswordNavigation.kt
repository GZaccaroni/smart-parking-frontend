package it.unibolss.smartparking.presentation.screens.user.changepassword

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import it.unibolss.smartparking.presentation.navigation.Route

/**
 * The route to the change password screen
 */
object ChangePasswordRoute : Route {
    override val path = "change-password"
}

/**
 * Utility method to provide the route to change password screen in a [NavGraphBuilder]
 */
fun NavGraphBuilder.changePasswordScreen() {
    composable("change-password") {
        ChangePasswordScreen()
    }
}
